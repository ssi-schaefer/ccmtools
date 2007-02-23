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
    /**
     * qualified IDL name of the component
     */
    private QualifiedName idl_name_;

    /**
     * name of the instance
     */
    private String name_;

    /**
     * the component will be deployed under that name (or null if we have to instantiate the home
     * directly)
     */
    private String alias_;

    public Component( QualifiedName idl_name, String name )
    {
        this(idl_name, name, null);
    }

    public Component( QualifiedName idl_name, String name, String alias )
    {
        idl_name_ = idl_name;
        name_ = name;
        alias_ = alias;
    }

    public QualifiedName getCcmName()
    {
        return idl_name_;
    }
    
    public String getAlias()
    {
        return alias_;
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
        StringBuilder text = new StringBuilder();
        text.append(offset);
        text.append("component ");
        text.append(idl_name_);
        if (alias_ != null)
        {
            text.append(" alias \"");
            text.append(alias_);
            text.append("\"");
        }
        text.append(" ");
        text.append(name_);
        text.append(" ;");
        out.println(text.toString());
    }
}
