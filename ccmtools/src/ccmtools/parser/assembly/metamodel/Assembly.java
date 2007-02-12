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
import java.util.HashMap;
import java.util.Vector;

/**
 * defines one assembly
 */
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

    private HashMap<String, Component> components_;

    void postProcessing( Module parent )
    {
        parent_ = parent;
        idl_name_.postProcessing(parent);
        components_ = new HashMap<String, Component>();
        for (int i = 0; i < elements_.size(); ++i)
        {
            elements_.get(i).postProcessing(this, components_);
        }
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
