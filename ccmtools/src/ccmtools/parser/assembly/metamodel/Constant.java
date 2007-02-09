/*
 * Created on Feb 9, 2007
 * 
 * R&D Salomon Automation (http://www.salomon.at)
 * 
 * Robert Lechner (robert.lechner@salomon.at)
 * 
 * $Id$
 */
package ccmtools.parser.assembly.metamodel;

public class Constant extends AssemblyElement
{
    private Port target_;

    private Value value_;

    public Constant( Port target, Value value )
    {
        target_ = target;
        value_ = value;
    }
}
