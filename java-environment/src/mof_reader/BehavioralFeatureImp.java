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
                scope_ = MofScopeKind.INSTANCE;
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
                throw new IllegalArgumentException("no visibility");
            }
        }
        return visibility_;
    }

    abstract String getXmiVisibility();

}
