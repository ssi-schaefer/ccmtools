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
import java.util.ArrayList;

import mof_xmi_parser.DTD_Container;
import mof_xmi_parser.model.MEnumerationType_labels;


/**
 * EnumerationType implementation
 *
 * @author Robert Lechner (robert.lechner@salomon.at)
 * @version $Date$
 */
class EnumerationTypeImp extends GeneralizableElementImp implements MofEnumerationType
{
    EnumerationTypeImp( EnumerationTypeXmi xmi, MofModelElement parent )
    {
        super(xmi, parent);
    }

    String getXmiAnnotation()
    { return ((EnumerationTypeXmi)xmi_).annotation_; }

    String getXmiName()
    { return ((EnumerationTypeXmi)xmi_).name_; }

    String getXmiAbstract()
    { return ((EnumerationTypeXmi)xmi_).isAbstract_; }

    String getXmiLeaf()
    { return ((EnumerationTypeXmi)xmi_).isLeaf_; }

    String getXmiRoot()
    { return ((EnumerationTypeXmi)xmi_).isRoot_; }

    String getXmiVisibility()
    { return ((EnumerationTypeXmi)xmi_).visibility_; }


    private ArrayList labels_;


    /// implements {@link MofEnumerationType#getLabels}
    public List getLabels()
    {
        if( labels_==null )
        {
            labels_ = new ArrayList();
            Vector ch = xmi_.findChildren(MEnumerationType_labels.xmlName__);
            for( int i=0; i<ch.size(); ++i )
            {
                MEnumerationType_labels v = (MEnumerationType_labels)ch.get(i);
                if( v.size()>=1 )
                {
                    labels_.add(v.get(0).toString());
                }
            }
        }
        return labels_;
    }


    /// implements {@link MofModelElement#process}
    public void process( NodeHandler handler ) throws NodeHandlerException
    {
        // TODO
    }
}
