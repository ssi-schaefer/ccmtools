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
 * StructuralFeature implementation
 *
 * @author Robert Lechner (robert.lechner@salomon.at)
 * @version $Date$
 */
abstract class StructuralFeatureImp extends TypedElementImp implements MofStructuralFeature
{
    StructuralFeatureImp( DTD_Container xmi, MofModelElement parent )
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


    /// implements {@link MofStructuralFeature#isChangeable}
    public boolean isChangeable()
    {
        // TODO
        return false;
    }

    
    /// implements {@link MofStructuralFeature#getMultiplicity}
    public MofMultiplicityType getMultiplicity()
    {
        // TODO
        return null;
    }

}
