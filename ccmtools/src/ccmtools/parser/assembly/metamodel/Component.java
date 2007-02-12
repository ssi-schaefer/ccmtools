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
 * defines an inner component
 */
public class Component extends AssemblyElement
{
    private QualifiedName idl_name_;

    private String name_;

    public Component( QualifiedName idl_name, String name )
    {
        idl_name_ = idl_name;
        name_ = name;
    }

    public void prettyPrint( PrintStream out, String offset )
    {
        out.println(offset + "component " + idl_name_ + " " + name_ + " ;");
    }
}
