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
            type_ = makeType(this);
        }
        return type_;
    }

    static MofClassifier makeType( ModelElementImp element )
    {
        Vector ch = element.xmi_.findChildren(MTypedElement_type.xmlName__);
        if( ch.size()<1 )
        {
            System.err.println("TypedElementImp.makeType : no type");
        }
        for( int i=0; i<ch.size(); ++i )
        {
            MTypedElement_type t = (MTypedElement_type)ch.get(i);
            if( t.size()<1 )
            {
                System.err.println("TypedElementImp.makeType : empty type");
            }
            for( int j=0; j<t.size(); ++j )
            {
                Object o = t.get(j);
                if( o instanceof Worker )
                {
                    MofModelElement e = ((Worker)o).mof();
                    if( e==null )
                    {
                        System.err.println("TypedElementImp.makeType : null type");
                    }
                    else if( e instanceof MofClassifier )
                    {
                        return (MofClassifier)e;
                    }
                    else
                    {
                        System.err.println("TypedElementImp.makeType : wrong type : "+
                                            e.getClass().getName());
                    }
                }
                else
                {
                    System.err.println("TypedElementImp.makeType : unknown type : "+o.getClass().getName());
                }
            }
        }
        return null;
    }

}
