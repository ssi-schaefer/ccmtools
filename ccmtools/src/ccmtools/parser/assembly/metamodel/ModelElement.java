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

/**
 * Top level element of the model.
 */
public abstract class ModelElement
{
    protected String name_;

    protected ModelElement( String name )
    {
        name_ = name;
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

    /**
     * call this method after model creation
     * 
     * @param parent namespace or null
     */
    abstract void postProcessing( Module parent );
}
