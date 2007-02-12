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
}
