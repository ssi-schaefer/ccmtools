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
import java.util.Iterator;

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
                tagId_ = getTextFromChild(MTag_tagId.xmlName__);
                if( tagId_==null )
                {
                    StringBuffer buffer = new StringBuffer();
                    boolean makePoint=false;
                    Iterator it = getQualifiedName().iterator();
                    while( it.hasNext() )
                    {
                        if( makePoint )  buffer.append(".");
                        else  makePoint=true;
                        buffer.append(it.next().toString());
                    }
                    tagId_ = buffer.toString();
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
        handler.beginTag(this);
        handler.endModelElement(this);
    }
}
