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
import java.util.List;
import java.util.Vector;
import ccmtools.parser.idl.metamodel.BaseIDL.MContainer;
import ccmtools.parser.idl.metamodel.BaseIDL.MModuleDef;
import ccmtools.parser.idl.metamodel.ComponentIDL.MComponentDef;

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
     * the key is a qualified IDL name (starting with "::")
     */
    static HashMap<String, MComponentDef> ccm_component_repository = new HashMap<String, MComponentDef>();

    static void updateCcmModel( List ccm_elements, List<ModelElement> this_elements,
            String parent_name )
    {
        for (Object root : ccm_elements)
        {
            if (root instanceof MModuleDef)
            {
                MModuleDef module = (MModuleDef) root;
                String name = module.getIdentifier();
                boolean found = false;
                for (ModelElement e : this_elements)
                {
                    if (e instanceof Module && e.name().equals(name))
                    {
                        ((Module) e).updateCcmModel(module);
                        found = true;
                    }
                }
                if (!found)
                {
                    String pn = parent_name + IDL_SCOPE + name;
                    final List<ModelElement> dummy = new Vector<ModelElement>();
                    updateCcmModel(module.getContentss(), dummy, pn);
                }
            }
            else if (root instanceof MComponentDef)
            {
                MComponentDef component = (MComponentDef) root;
                String name = component.getIdentifier();
                ccm_component_repository.put(parent_name + IDL_SCOPE + name, component);
                for (ModelElement e : this_elements)
                {
                    if (e instanceof Assembly && e.name().equals(name))
                    {
                        ((Assembly) e).updateCcmModel(component);
                    }
                }
            }
        }
    }

    public void updateCcmModel( MContainer ccmModel )
    {
        updateCcmModel(ccmModel.getContentss(), elements_, "");
    }

    public void updateCcmModels( List<MContainer> models )
    {
        for (MContainer ccmModel : models)
            updateCcmModel(ccmModel);
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
