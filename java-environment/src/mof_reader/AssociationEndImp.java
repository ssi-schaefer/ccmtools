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

import java.util.Collection;
import java.util.List;

import mof_xmi_parser.DTD_Container;


/**
 * AssociationEnd implementation
 *
 * @author Robert Lechner (robert.lechner@salomon.at)
 * @version $Date$
 */
class AssociationEndImp extends TypedElementImp implements MofAssociationEnd
{
    AssociationEndImp( DTD_Container xmi, MofModelElement parent )
    {
        super(xmi, parent);
    }


    /// implements {@link MofAssociationEnd#getAggregation}
    public MofAggregationKind getAggregation()
    {
        // TODO
        return null;
    }

    /// implements {@link MofAssociationEnd#isChangeable}
    public boolean isChangeable()
    {
        // TODO
        return false;
    }

    /// implements {@link MofAssociationEnd#isNavigable}
    public boolean isNavigable()
    {
        // TODO
        return false;
    }

    /// implements {@link MofAssociationEnd#getMultiplicity}
    public MofMultiplicityType getMultiplicity()
    {
        // TODO
        return null;
    }


    /// implements {@link MofModelElement#process}
    public void process( NodeHandler handler ) throws NodeHandlerException
    {
        // TODO
    }
}
