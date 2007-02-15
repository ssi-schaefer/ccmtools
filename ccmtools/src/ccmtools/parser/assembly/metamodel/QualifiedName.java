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

import ccmtools.parser.idl.metamodel.ComponentIDL.MComponentDef;

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

    /**
     * returns the component (or null if we couldn't find it)
     */
    public MComponentDef getCcmComponent()
    {
        if (qn_.startsWith(Model.IDL_SCOPE))
        {
            return Model.ccm_component_repository.get(qn_);
        }
        ModelElement parent = scope_;
        while (parent != null)
        {
            String key = parent.getGlobalName() + Model.IDL_SCOPE + qn_;
            MComponentDef x = Model.ccm_component_repository.get(key);
            if (x != null)
                return x;
            parent = parent.getParent();
        }
        return Model.ccm_component_repository.get(Model.IDL_SCOPE + qn_);
    }
}
