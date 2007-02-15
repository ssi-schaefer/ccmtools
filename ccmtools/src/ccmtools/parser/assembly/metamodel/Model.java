/*
 * Created on Feb 5, 2007
 * 
 * R&D Salomon Automation (http://www.salomon.at)
 * 
 * Robert Lechner (robert.lechner@salomon.at)
 * 
 * $Id$
 */
package ccmtools.parser.assembly.metamodel;

import java.io.PrintStream;
import java.util.HashMap;
import java.util.Vector;

/**
 * The root element of the ccmtools assembly metamodel.
 */
public class Model
{
    public static final String IDL_SCOPE = "::";

    private Vector<ModelElement> elements_ = new Vector<ModelElement>();

    /**
     * adds a new ModelElement
     */
    public void add( ModelElement e )
    {
        elements_.add(e);
    }

    private HashMap<String, Assembly> assemblies_;

    /**
     * call this method after model creation
     */
    public void postProcessing()
    {
        assemblies_ = new HashMap<String, Assembly>();
        for (ModelElement e : elements_)
        {
            e.postProcessing(null, assemblies_);
        }
    }

    /**
     * prints the model to an output stream
     * 
     * @param out the output stream
     */
    public void prettyPrint( PrintStream out )
    {
        for (ModelElement e : elements_)
        {
            e.prettyPrint(out, "");
        }
    }

    /**
     * adds all elements of the give model to this model
     */
    public void merge( Model m )
    {
        elements_.addAll(m.elements_);
        if (assemblies_ == null)
            assemblies_ = new HashMap<String, Assembly>(m.assemblies_);
        else
            for (String key : m.assemblies_.keySet())
            {
                if (assemblies_.containsKey(key))
                {
                    throw new RuntimeException("an assembly of type \"" + key + "\" already exists");
                }
                Assembly a = m.assemblies_.get(key);
                assemblies_.put(key, a);
            }
    }

    /**
     * searches for an assembly description
     * 
     * @param key qualified IDL name of the component
     * @return the assembly description (or null)
     */
    public Assembly getAssembly( String key )
    {
        if (!key.startsWith(IDL_SCOPE))
            key = IDL_SCOPE + key;
        return assemblies_.get(key);
    }
}
