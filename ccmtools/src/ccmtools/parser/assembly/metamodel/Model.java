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
import java.util.Vector;

/**
 * The root element of the ccmtools assembly metamodel.
 */
public class Model
{
    private Vector<ModelElement> elements_ = new Vector<ModelElement>();

    /**
     * adds a new ModelElement
     */
    public void add( ModelElement e )
    {
        elements_.add(e);
    }

    /**
     * call this method after model creation
     */
    public void postProcessing()
    {
        for (int i = 0; i < elements_.size(); ++i)
        {
            elements_.get(i).postProcessing(null);
        }
    }

    /**
     * prints the model to an output stream
     * 
     * @param out the output stream
     */
    public void prettyPrint( PrintStream out )
    {
        for (int i = 0; i < elements_.size(); ++i)
        {
            elements_.get(i).prettyPrint(out, "");
        }
    }
}
