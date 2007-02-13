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

    private Module scope_;

    /**
     * defines the scope of this qualified name
     * 
     * @param scope scope of this qualified name (or null for global scope)
     */
    void postProcessing( Module scope )
    {
        scope_ = scope;
    }
}
