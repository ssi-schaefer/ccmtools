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

public class Assembly extends ModelElement
{
    private QualifiedName idl_name_;

    private Vector<AssemblyElement> elements_;

    public Assembly( String name, QualifiedName idl_name, Vector<AssemblyElement> elements )
    {
        super(name);
        idl_name_ = idl_name;
        elements_ = elements;
    }

    public void prettyPrint( PrintStream out, String offset )
    {
        out.println(offset + "assembly " + name_ + " implements " + idl_name_ + " {");
        for (int i = 0; i < elements_.size(); ++i)
        {
            elements_.get(i).prettyPrint(out, offset + "  ");
        }
        out.println(offset + "};");
    }
}
