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

import java.util.Map;

/**
 * facet or receptacle
 */
public final class Port
{
    private String component_;

    private String connector_;

    /**
     * internal port (port of an inner component)
     * 
     * @param component name of the inner component
     * @param connector facet or receptacle name
     */
    public Port( String component, String connector )
    {
        component_ = component;
        connector_ = connector;
    }
    
    /**
     * returns the name of the inner component or null if this is a port of the outer component
     */
    public String getComponent()
    {
        return component_;
    }
    
    /**
     * returns the name of this facet or receptacle
     */
    public String getConnector()
    {
        return connector_;
    }

    /**
     * external port (port of the assembly)
     * 
     * @param connector facet or receptacle name
     */
    public Port( String connector )
    {
        component_ = null;
        connector_ = connector;
    }

    public String toString()
    {
        if (component_ == null)
            return "this." + connector_;
        else
            return component_ + "." + connector_;
    }

    void postProcessing( Map<String, Component> components )
    {
        if (component_ != null)
        {
            if (!components.containsKey(component_))
            {
                throw new RuntimeException("inner component \"" + component_ + "\" is undefined");
            }
        }
    }
}
