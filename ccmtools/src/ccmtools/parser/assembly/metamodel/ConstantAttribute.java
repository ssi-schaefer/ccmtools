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

public class ConstantAttribute extends Attribute
{
    private Port target_;

    private String value_;

    public ConstantAttribute( Port target, String value )
    {
        target_ = target;
        value_ = value;
    }
}
