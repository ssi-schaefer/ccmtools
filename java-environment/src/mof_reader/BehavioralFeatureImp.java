/*  MOF reader
 *
 *  2004 by Research & Development, Salomon Automation <www.salomon.at>
 *
 *  Robert Lechner  <robert.lechner@salomon.at>
 *
 *
 *  $Id$
 *
 */

package mof_reader;


import java.util.Vector;

import mof_xmi_parser.DTD_Container;
import mof_xmi_parser.model.MFeature_visibility;
import mof_xmi_parser.model.MFeature_scope;


/**
 * BehavioralFeature implementation
 *
 * @author Robert Lechner (robert.lechner@salomon.at)
 * @version $Date$
 */
abstract class BehavioralFeatureImp extends NamespaceImp implements MofBehavioralFeature
{
    BehavioralFeatureImp( DTD_Container xmi, MofModelElement parent )
    {
        super(xmi, parent);
    }


    private MofScopeKind scope_;
    private MofVisibilityKind visibility_;


    /// implements {@link MofFeature#getScope}
    public MofScopeKind getScope() throws IllegalArgumentException
    {
        if( scope_==null )
        {
            String text = getXmiScope();
            if( text!=null )
            {
                scope_ = MofScopeKind.create(text);
            }
            else
            {
                Vector children = xmi_.findChildren(MFeature_scope.xmlName__);
                if( children.size()>=1 )
                {
                    MFeature_scope v = (MFeature_scope)children.get(0);
                    scope_ = MofScopeKind.create(v.xmi_value_);
                }
                else
                {
                    throw new IllegalArgumentException("no scope");
                }
            }
        }
        return scope_;
    }

    abstract String getXmiScope();


    /// implements {@link MofFeature#getVisibility}
    public MofVisibilityKind getVisibility() throws IllegalArgumentException
    {
        if( visibility_==null )
        {
            String text = getXmiVisibility();
            if( text!=null )
            {
                visibility_ = MofVisibilityKind.create(text);
            }
            else
            {
                Vector children = xmi_.findChildren(MFeature_visibility.xmlName__);
                if( children.size()>=1 )
                {
                    MFeature_visibility v = (MFeature_visibility)children.get(0);
                    visibility_ = MofVisibilityKind.create(v.xmi_value_);
                }
                else
                {
                    throw new IllegalArgumentException("no visibility");
                }
            }
        }
        return visibility_;
    }

    abstract String getXmiVisibility();

}
