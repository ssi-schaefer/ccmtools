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

public class Component extends AssemblyElement
{
    private QualifiedName idl_name_;

    private String name_;

    public Component( QualifiedName idl_name, String name )
    {
        idl_name_ = idl_name;
        name_ = name;
    }
}
