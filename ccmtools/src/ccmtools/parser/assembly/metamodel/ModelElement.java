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
    protected String name_;

    protected ModelElement( String name )
    {
        name_ = name;
        if (name.indexOf(Model.IDL_SCOPE) >= 0)
            throw new RuntimeException("scoped name!");
    }

    public String name()
    {
        return name_;
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
            return parent_.getGlobalName() + Model.IDL_SCOPE + name_;
        }
        else
        {
            return Model.IDL_SCOPE + name_;
        }
    }
    
    public String toString()
    {
        return getGlobalName();
    }

    /**
     * call this method after model creation
     * 
     * @param parent namespace or null
     * @param assemblies add all assemblies to that map
     */
    abstract void postProcessing( Module parent, Map<String, Assembly> assemblies );
}
