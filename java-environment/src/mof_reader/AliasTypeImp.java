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
 * AliasType implementation
 *
 * @author Robert Lechner (robert.lechner@salomon.at)
 * @version $Date$
 */
class AliasTypeImp extends GeneralizableElementImp implements MofAliasType
{
    AliasTypeImp( AliasTypeXmi xmi, MofModelElement parent )
    {
        super(xmi, parent);
    }

    String getXmiAnnotation()
    { return ((AliasTypeXmi)xmi_).annotation_; }

    String getXmiName()
    { return ((AliasTypeXmi)xmi_).name_; }

    String getXmiAbstract()
    { return ((AliasTypeXmi)xmi_).isAbstract_; }

    String getXmiLeaf()
    { return ((AliasTypeXmi)xmi_).isLeaf_; }

    String getXmiRoot()
    { return ((AliasTypeXmi)xmi_).isRoot_; }

    String getXmiVisibility()
    { return ((AliasTypeXmi)xmi_).visibility_; }


    private MofClassifier type_;


    /// implements {@link MofTypedElement#getType}
    public MofClassifier getType()
    {
        if( type_==null )
        {
            type_ = TypedElementImp.makeType(this);
        }
        return type_;
    }


    /// implements {@link MofModelElement#process}
    public void process( NodeHandler handler ) throws NodeHandlerException
    {
        // TODO
    }
}
