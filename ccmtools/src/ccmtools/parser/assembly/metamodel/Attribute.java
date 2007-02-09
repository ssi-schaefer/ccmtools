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

public class Attribute extends AssemblyElement
{
    private Port target_;

    private Port source_;

    public Attribute( Port target, Port source )
    {
        target_ = target;
        source_ = source;
    }
}
