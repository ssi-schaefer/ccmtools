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
}
