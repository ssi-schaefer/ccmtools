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
import mof_xmi_parser.model.MTag_tagId;
import mof_xmi_parser.model.MTag_values;


/**
 * Tag implementation
 *
 * @author Robert Lechner (robert.lechner@salomon.at)
 * @version $Date$
 */
class TagImp extends ModelElementImp implements MofTag
{
    TagImp( TagXmi xmi, MofModelElement parent )
    {
        super(xmi, parent);
    }

    String getXmiAnnotation()
    { return ((TagXmi)xmi_).annotation_; }

    String getXmiName()
    { return ((TagXmi)xmi_).name_; }


    private String tagId_;
    private ArrayList values_;


    /// implements {@link MofTag#getTagId}
    public String getTagId()
    {
        if( tagId_==null )
        {
            tagId_ = ((TagXmi)xmi_).tagId_;
            if( tagId_==null )
            {
                Vector ch = xmi_.findChildren(MTag_tagId.xmlName__);
                if( ch.size()>=1 )
                {
                    MTag_tagId id = (MTag_tagId)ch.get(0);
                    if( id.size()>=1 )
                    {
                        tagId_ = id.get(0).toString();
                    }
                }
            }
        }
        return tagId_;
    }


    /// implements {@link MofTag#getValues}
    public List getValues()
    {
        if( values_==null )
        {
            values_ = new ArrayList();
            Vector ch = xmi_.findChildren(MTag_values.xmlName__);
            for( int i=0; i<ch.size(); ++i )
            {
                MTag_values v = (MTag_values)ch.get(i);
                if( v.size()>=1 )
                {
                    values_.add(v.get(0).toString());
                }
            }
        }
        return values_;
    }


    /// implements {@link MofTag#getModelElement}
    public MofModelElement getModelElement()
    {
        return parent_;
    }


    /// implements {@link MofModelElement#process}
    public void process( NodeHandler handler ) throws NodeHandlerException
    {
        // TODO
    }
}
