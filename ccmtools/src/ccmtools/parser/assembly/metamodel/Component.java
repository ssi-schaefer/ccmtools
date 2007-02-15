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
import java.util.Map;

/**
 * defines an inner component
 */
public class Component extends AssemblyElement
{
    private QualifiedName idl_name_;

    private String name_;

    public Component( QualifiedName idl_name, String name )
    {
        idl_name_ = idl_name;
        name_ = name;
    }
    
    public QualifiedName getCcmName()
    {
        return idl_name_;
    }

    void postProcessing( Assembly parent, Map<String, Component> components )
    {
        parent_ = parent;
        idl_name_.postProcessing(parent.getParent());
        if (components.containsKey(name_))
        {
            throw new RuntimeException("inner component \"" + name_ + "\" already exists");
        }
        components.put(name_, this);
    }

    public void prettyPrint( PrintStream out, String offset )
    {
        out.println(offset + "component " + idl_name_ + " " + name_ + " ;");
    }
}
