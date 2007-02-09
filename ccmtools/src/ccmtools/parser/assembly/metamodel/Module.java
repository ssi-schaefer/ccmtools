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

import java.util.Vector;

public class Module extends ModelElement
{
    private Vector<ModelElement> children_;

    public Module( String name, Vector<ModelElement> children )
    {
        super(name);
        children_ = children;
    }
}
