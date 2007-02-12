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

    private Module container_;

    void postProcessing( Module container )
    {
        container_ = container;
    }
}
