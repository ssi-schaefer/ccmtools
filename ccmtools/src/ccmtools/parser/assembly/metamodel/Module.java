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
import java.util.Vector;

/**
 * a namespace
 */
public class Module extends ModelElement
{
    private Vector<ModelElement> children_;

    public Module( String name, Vector<ModelElement> children )
    {
        super(name);
        children_ = children;
    }

    public void prettyPrint( PrintStream out, String offset )
    {
        out.println(offset + "module " + name_ + " {");
        for (int i = 0; i < children_.size(); ++i)
        {
            children_.get(i).prettyPrint(out, offset + "  ");
        }
        out.println(offset + "};");
    }
}
