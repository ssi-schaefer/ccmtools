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
 * TypedElement implementation
 *
 * @author Robert Lechner (robert.lechner@salomon.at)
 * @version $Date$
 */
abstract class TypedElementImp extends ModelElementImp implements MofTypedElement
{
    TypedElementImp( DTD_Container xmi, MofModelElement parent )
    {
        super(xmi, parent);
    }


    /// implements {@link MofTypedElement#getType}
    public MofClassifier getType()
    {
        // TODO
        return null;
    }

}
