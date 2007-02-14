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
import java.util.Map;

/**
 * Top level element of the model.
 */
public abstract class ModelElement
{
    public static final String IDL_SCOPE = "::";

    protected String name_;

    protected ModelElement( String name )
    {
        name_ = name;
        if (name.indexOf(IDL_SCOPE) >= 0)
            throw new RuntimeException("scoped name!");
    }

    public abstract void prettyPrint( PrintStream out, String offset );

    protected Module parent_;

    /**
     * returns the parent Module of this element (or null)
     */
    public Module getParent()
    {
        return parent_;
    }

    public String getGlobalName()
    {
        if (parent_ != null)
        {
            return parent_.getGlobalName() + IDL_SCOPE + name_;
        }
        else
        {
            return IDL_SCOPE + name_;
        }
    }

    /**
     * call this method after model creation
     * 
     * @param parent namespace or null
     * @param assemblies add all assemblies to that map
     */
    abstract void postProcessing( Module parent, Map<String, Assembly> assemblies );
}
