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
import java.util.Vector;

import mof_xmi_parser.DTD_Container;
import mof_xmi_parser.model.MCollectionType_multiplicity;


/**
 * CollectionType implementation
 *
 * @author Robert Lechner (robert.lechner@salomon.at)
 * @version $Date$
 */
class CollectionTypeImp extends GeneralizableElementImp implements MofCollectionType
{
    CollectionTypeImp( CollectionTypeXmi xmi, MofModelElement parent )
    {
        super(xmi, parent);
    }

    String getXmiAnnotation()
    { return ((CollectionTypeXmi)xmi_).annotation_; }

    String getXmiName()
    { return ((CollectionTypeXmi)xmi_).name_; }

    String getXmiAbstract()
    { return ((CollectionTypeXmi)xmi_).isAbstract_; }

    String getXmiLeaf()
    { return ((CollectionTypeXmi)xmi_).isLeaf_; }

    String getXmiRoot()
    { return ((CollectionTypeXmi)xmi_).isRoot_; }

    String getXmiVisibility()
    { return ((CollectionTypeXmi)xmi_).visibility_; }


    private MofClassifier type_;
    private MofMultiplicityType multiplicity_;


    /// implements {@link MofTypedElement#getType}
    public MofClassifier getType()
    {
        if( type_==null )
        {
            type_ = TypedElementImp.makeType(this);
        }
        return type_;
    }


    /// implements {@link MofCollectionType#getMultiplicity}
    public MofMultiplicityType getMultiplicity() throws NumberFormatException
    {
        if( multiplicity_==null )
        {
            Vector children = xmi_.findChildren(MCollectionType_multiplicity.xmlName__);
            if( children.size()>=1 )
            {
                MCollectionType_multiplicity p = (MCollectionType_multiplicity)children.get(0);
                multiplicity_ = new MofMultiplicityType(p);
            }
            else
            {
                throw new NumberFormatException("no multiplicity");
            }
        }
        return multiplicity_;
    }


    /// implements {@link MofModelElement#process}
    public void process( NodeHandler handler ) throws NodeHandlerException
    {
        handler.beginCollectionType(this);
        handler.endModelElement(this);
    }
}
