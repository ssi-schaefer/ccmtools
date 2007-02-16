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
 * connects a facet with a receptacle
 */
public class Connection extends AssemblyElement
{
    private Port facet_;

    private Port receptacle_;

    public Connection( Port facet, Port receptacle )
    {
        facet_ = facet;
        receptacle_ = receptacle;
    }

    public Port getFacet()
    {
        return facet_;
    }

    public Port getReceptacle()
    {
        return receptacle_;
    }

    void postProcessing( Assembly parent, Map<String, Component> components )
    {
        parent_ = parent;
        facet_.postProcessing(components);
        receptacle_.postProcessing(components);
    }
    
    public String toString()
    {
        return "connect " + facet_ + " to " + receptacle_;
    }

    public void prettyPrint( PrintStream out, String offset )
    {
        out.println(offset + toString() + " ;");
    }
}
