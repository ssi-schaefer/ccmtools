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
import java.util.Map;
import java.util.Vector;

/**
 * defines one assembly
 */
public class Assembly extends ModelElement
{
    private String opt_name_;

    private Vector<AssemblyElement> elements_;

    public Assembly( String name, String opt_name, Vector<AssemblyElement> elements )
    {
        super(name);
        opt_name_ = opt_name;
        elements_ = elements;
    }

    private HashMap<String, Component> components_;

    void postProcessing( Module parent, Map<String, Assembly> assemblies )
    {
        parent_ = parent;
        String key = getGlobalName();
        if (assemblies.containsKey(key))
        {
            throw new RuntimeException("an assembly of type \"" + key + "\" already exists");
        }
        assemblies.put(key, this);
        components_ = new HashMap<String, Component>();
        for (int i = 0; i < elements_.size(); ++i)
        {
            elements_.get(i).postProcessing(this, components_);
        }
    }

    public void prettyPrint( PrintStream out, String offset )
    {
        out.print(offset + "assembly ");
        if (opt_name_ != null)
            out.print(opt_name_ + " ");
        out.println("implements " + name_ + " {");
        for (int i = 0; i < elements_.size(); ++i)
        {
            elements_.get(i).prettyPrint(out, offset + "  ");
        }
        out.println(offset + "};");
    }
}
