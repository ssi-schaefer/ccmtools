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

public class Assembly extends ModelElement
{
    private QualifiedName idl_name_;

    public Assembly( String name, QualifiedName idl_name )
    {
        super(name);
        idl_name_ = idl_name;
    }
}
