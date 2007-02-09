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

public class Model
{
    private Vector<ModelElement> elements_ = new Vector<ModelElement>();

    public void add( ModelElement e )
    {
        elements_.add(e);
    }
}
