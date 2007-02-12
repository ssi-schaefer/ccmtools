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
 * a qualified name
 */
public final class QualifiedName
{
    private String qn_;

    public QualifiedName( String qn )
    {
        qn_ = qn;
    }
    
    public String toString()
    {
        return qn_;
    }
}
