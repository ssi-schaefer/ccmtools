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
 * connects an attribute of the assembly with the attribute of an inner component
 */
public class Attribute extends AssemblyElement
{
    private Port target_;

    private Port source_;

    public Attribute( Port target, Port source )
    {
        target_ = target;
        source_ = source;
    }

    public void prettyPrint( PrintStream out, String offset )
    {
        out.println(offset + "attribute " + target_ + " = " + source_ + " ;");
    }
}
