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

    public void prettyPrint( PrintStream out, String offset )
    {
        out.println(offset + "connect " + facet_ + " to " + receptacle_ + " ;");
    }
}
