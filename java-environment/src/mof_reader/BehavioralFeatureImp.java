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


    /// implements {@link MofFeature#getScope}
    public MofScopeKind getScope()
    {
        // TODO
        return null;
    }


    /// implements {@link MofFeature#getVisibility}
    public MofVisibilityKind getVisibility()
    {
        // TODO
        return null;
    }

}
