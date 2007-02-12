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

import java.io.PrintStream;

/**
 * sets the attribute of an inner component to a constant value
 */
public class Constant extends AssemblyElement
{
    private Port target_;

    private Value value_;

    public Constant( Port target, Value value )
    {
        target_ = target;
        value_ = value;
    }

    public void prettyPrint( PrintStream out, String offset )
    {
        out.println(offset + "constant " + target_ + " = " + value_ + " ;");
    }
}
