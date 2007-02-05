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

public class VariableAttribute extends Attribute
{
    private String name_;

    private Port target_;

    public VariableAttribute( String name, Port target )
    {
        name_ = name;
        target_ = target;
    }
}
