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
import mof_xmi_parser.model.MReference_referencedEnd;


/**
 * Reference implementation
 *
 * @author Robert Lechner (robert.lechner@salomon.at)
 * @version $Date$
 */
class ReferenceImp extends StructuralFeatureImp implements MofReference
{
    ReferenceImp( ReferenceXmi xmi, MofModelElement parent )
    {
        super(xmi, parent);
    }

    String getXmiAnnotation()
    { return ((ReferenceXmi)xmi_).annotation_; }

    String getXmiName()
    { return ((ReferenceXmi)xmi_).name_; }

    String getXmiScope()
    { return ((ReferenceXmi)xmi_).scope_; }

    String getXmiVisibility()
    { return ((ReferenceXmi)xmi_).visibility_; }

    String getXmiIsChangeable()
    { return ((ReferenceXmi)xmi_).isChangeable_; }


    private MofAssociationEnd referencedEnd_;
    //private MofAssociationEnd exposedEnd_;


    /// implements {@link MofReference#getReferencedEnd}
    public MofAssociationEnd getReferencedEnd()
    {
        if( referencedEnd_==null )
        {
            referencedEnd_ = createReferencedEnd();
        }
        return referencedEnd_;
    }

    private MofAssociationEnd createReferencedEnd()
    {
        Vector ch = xmi_.findChildren(MReference_referencedEnd.xmlName__);
        for( int i=0; i<ch.size(); ++i )
        {
            MReference_referencedEnd t = (MReference_referencedEnd)ch.get(i);
            for( int j=0; j<t.size(); ++j )
            {
                Object o = t.get(j);
                if( o instanceof Worker )
                {
                    MofModelElement e = ((Worker)o).mof();
                    if( e instanceof MofAssociationEnd )
                    {
                        return (MofAssociationEnd)e;
                    }
                }
            }
        }
        return null;
    }


    /// implements {@link MofReference#getExposedEnd}
    public MofAssociationEnd getExposedEnd()
    {
        // I don't know how to get this element
        throw new RuntimeException("not implemented");
    }


    /// implements {@link MofModelElement#process}
    public void process( NodeHandler handler ) throws NodeHandlerException
    {
        handler.beginReference(this);
        handler.endModelElement(this);
    }
}
