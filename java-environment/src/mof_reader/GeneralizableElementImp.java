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


import java.util.List;
import java.util.Vector;
import java.util.ArrayList;

import mof_xmi_parser.DTD_Container;
import mof_xmi_parser.model.MGeneralizableElement_supertypes;


/**
 * GeneralizableElement implementation
 *
 * @author Robert Lechner (robert.lechner@salomon.at)
 * @version $Date$
 */
abstract class GeneralizableElementImp extends NamespaceImp implements MofGeneralizableElement
{
    GeneralizableElementImp( DTD_Container xmi, MofModelElement parent )
    {
        super(xmi, parent);
    }


    private String isAbstract_;
    private String isLeaf_;
    private String isRoot_;
    private MofVisibilityKind visibility_;
    private ArrayList supertypes_;


    /// implements {@link MofGeneralizableElement#isAbstract}
    public boolean isAbstract()
    {
        if( isAbstract_==null )
        {
            isAbstract_ = getXmiAbstract();
            if( isAbstract_==null )
            {
                isAbstract_ = "false";
            }
        }
        return isAbstract_.equalsIgnoreCase("true");
    }

    /// returns 'xmi_.isAbstract_'
    abstract String getXmiAbstract();


    /// implements {@link MofGeneralizableElement#isLeaf}
    public boolean isLeaf()
    {
        if( isLeaf_==null )
        {
            isLeaf_ = getXmiLeaf();
            if( isLeaf_==null )
            {
                isLeaf_ = "false";
            }
        }
        return isLeaf_.equalsIgnoreCase("true");
    }

    /// returns 'xmi_.isLeaf_'
    abstract String getXmiLeaf();


    /// implements {@link MofGeneralizableElement#isRoot}
    public boolean isRoot()
    {
        if( isRoot_==null )
        {
            isRoot_ = getXmiRoot();
            if( isRoot_==null )
            {
                isRoot_ = "false";
            }
        }
        return isRoot_.equalsIgnoreCase("true");
    }

    /// returns 'xmi_.isRoot_'
    abstract String getXmiRoot();


    /// implements {@link MofGeneralizableElement#getVisibility}
    public MofVisibilityKind getVisibility() throws IllegalArgumentException
    {
        if( visibility_==null )
        {
            String text = getXmiVisibility();
            if( text!=null )
            {
                visibility_ = MofVisibilityKind.create(text);
            }
            else
            {
                throw new IllegalArgumentException("no visibility");
            }
        }
        return visibility_;
    }

    /// returns 'xmi_.visibility_'
    abstract String getXmiVisibility();


    /// implements {@link MofGeneralizableElement#getSupertypes}
    public List getSupertypes()
    {
        if( supertypes_==null )
        {
            supertypes_ = new ArrayList();
            Vector children = xmi_.findChildren(MGeneralizableElement_supertypes.xmlName__);
            for( int i=0; i<children.size(); ++i )
            {
                MGeneralizableElement_supertypes st = (MGeneralizableElement_supertypes)children.get(i);
                for( int j=0; j<st.size(); ++j )
                {
                    Object obj = st.get(j);
                    if( obj instanceof Worker )
                    {
                        MofModelElement e = ((Worker)obj).mof();
                        if( e==null )
                        {
                            System.err.println(
                                "GeneralizableElementImp.getSupertypes(): "+
                                obj.getClass().getName()+".mof() returns null" );
                        }
                        else if( e instanceof MofGeneralizableElement )
                        {
                            supertypes_.add(e);
                        }
                        else
                        {
                            System.err.println("GeneralizableElement: wrong supertype: "+e.getClass().getName());
                        }
                    }
                    else
                    {
                        System.err.println("GeneralizableElement: unknown supertype: "+obj.getClass().getName());
                    }
                }
            }
        }
        return supertypes_;
    }

}
