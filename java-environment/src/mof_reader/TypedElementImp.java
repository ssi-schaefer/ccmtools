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
import mof_xmi_parser.model.MTypedElement_type;


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


    private MofClassifier type_;


    /// implements {@link MofTypedElement#getType}
    public MofClassifier getType()
    {
        if( type_==null )
        {
            Vector ch = xmi_.findChildren(MTypedElement_type.xmlName__);
            for( int i=0; i<ch.size(); ++i )
            {
                MTypedElement_type t = (MTypedElement_type)ch.get(i);
                // TODO
            }
        }
        return type_;
    }

}
