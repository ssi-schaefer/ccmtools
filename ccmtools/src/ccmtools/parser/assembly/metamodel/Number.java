/*
 * Created on Feb 9, 2007
 * 
 * R&D Salomon Automation (http://www.salomon.at)
 * 
 * Robert Lechner (robert.lechner@salomon.at)
 * 
 * $Id$
 */
package ccmtools.parser.assembly.metamodel;

/**
 * a number (integer or floating point)
 */
public class Number extends Value
{
    private String value_;

    public Number( String value )
    {
        value_ = value;
    }

    public String toString()
    {
        return value_;
    }
}
