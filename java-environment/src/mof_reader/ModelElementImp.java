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
import java.util.ArrayList;
import java.util.Vector;
import java.util.Iterator;

import mof_xmi_parser.DTD_Container;
import mof_xmi_parser.MXMI_reference;
import mof_xmi_parser.model.MModelElement_annotation;
import mof_xmi_parser.model.MModelElement_name;


/**
 * ModelElement implementation
 *
 * @author Robert Lechner (robert.lechner@salomon.at)
 * @version $Date$
 */
abstract class ModelElementImp implements MofModelElement
{
    ModelElementImp( DTD_Container xmi, MofModelElement parent )
    {
        xmi_ = xmi;
        parent_ = parent;
    }


    protected DTD_Container xmi_;
    protected MofModelElement parent_;
    private String annotation_;
    private String name_;
    private List qualifiedName_;
    private Vector constraints_;
    private Vector tags_;
    private Object userData_;


    /// implements {@link MofModelElement#setUserData}
    public void setUserData( Object data )
    {
        userData_ = data;
    }

    /// implements {@link MofModelElement#getUserData}
    public Object getUserData()
    {
        return userData_;
    }


    /// implements {@link MofModelElement#getAnnotation}
    public String getAnnotation()
    {
        if( annotation_==null )
        {
            annotation_ = getXmiAnnotation();
            if( annotation_==null )
            {
                annotation_ = createAnnotation(xmi_);
            }
        }
        return annotation_;
    }

    /// returns 'xmi_.annotation_'
    abstract String getXmiAnnotation();

    static String createAnnotation( DTD_Container element )
    {
        Vector children = element.findChildren(MModelElement_annotation.xmlName__);
        int s = children.size();
        StringBuffer buffer = new StringBuffer();
        for( int index=0; index<s; ++index )
        {
            MModelElement_annotation a = (MModelElement_annotation)children.get(index);
            Iterator it = a.content().iterator();
            while( it.hasNext() )
            {
                Object o = it.next();
                if( o instanceof MXMI_reference )
                {
                    throw new RuntimeException("MXMI_reference not implemented");
                }
                buffer.append(o.toString());
            }
        }
        return buffer.toString();
    }


    /// implements {@link MofModelElement#getName}
    public String getName()
    {
        if( name_==null )
        {
            name_ = getXmiName();
            if( name_==null )
            {
                name_ = getTextFromChild(MModelElement_name.xmlName__);
                if( name_==null )
                {
                    name_ = "(no name)";
                }
            }
        }
        return name_;
    }

    /// returns 'xmi_.name_'
    abstract String getXmiName();


    protected String getTextFromChild( String xmlName )
    {
        Vector children = xmi_.findChildren(xmlName);
        for( int i=0; i<children.size(); ++i )
        {
            DTD_Container child = (DTD_Container)children.get(i);
            if( child.size()>=1 )
            {
                return child.get(0).toString();
            }
        }
        return null;
    }


    protected String getBooleanFromChild( String xmlName )
    {
        String text = getTextFromChild(xmlName);
        if( text==null )
        {
            return "false";
        }
        return text;
    }


    /// implements {@link MofModelElement#getQualifiedName}
    public List getQualifiedName()
    {
        if( qualifiedName_==null )
        {
            qualifiedName_ = createQualifiedName(this);
        }
        return qualifiedName_;
    }


    static List createQualifiedName( ModelElementImp element )
    {
        if( element.parent_==null )
        {
            ArrayList dummy = new ArrayList();
            dummy.add(element.getName());
            return dummy;
        }
        ArrayList result = new ArrayList(element.parent_.getQualifiedName());
        result.add(element.getName());
        return result;
    }


    /// implements {@link MofModelElement#getProviders}
    public Collection getProviders()
    {
        // the DTD doesn't support the "Depends On" association
        return new ArrayList();
    }


    /// implements {@link MofModelElement#getContainer}
    public MofNamespace getContainer()
    {
        if( parent_!=null && (parent_ instanceof MofNamespace) )
        {
            return (MofNamespace)parent_;
        }
        return null;
    }


    /// implements {@link MofModelElement#getConstraints}
    public Collection getConstraints()
    {
        if( constraints_==null )
        {
            constraints_ = convertXmiToMof(xmi_.findChildren(ConstraintXmi.xmlName__));
        }
        return constraints_;
    }

    /// implements {@link MofModelElement#getTags}
    public List getTags()
    {
        if( tags_==null )
        {
            tags_ = convertXmiToMof(xmi_.findChildren(TagXmi.xmlName__));
        }
        return tags_;
    }

    static Vector convertXmiToMof( Vector xmi )
    {
        Vector result = new Vector();
        int s = xmi.size();
        for( int index=0; index<s; ++index )
        {
            Object obj = xmi.get(index);
            if( obj instanceof Worker )
            {
                result.add( ((Worker)obj).mof() );
            }
            else if( obj instanceof MofModelElement )
            {
                result.add(obj);
            }
            else
            {
                System.err.println("ModelElementImp.convertXmiToMof : unknown class: "+obj.getClass().getName());
            }
        }
        return result;
    }

}
