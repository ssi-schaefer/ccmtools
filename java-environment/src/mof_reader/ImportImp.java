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
import mof_xmi_parser.model.MImport_importedNamespace;


/**
 * Import implementation
 *
 * @author Robert Lechner (robert.lechner@salomon.at)
 * @version $Date$
 */
class ImportImp extends ModelElementImp implements MofImport
{
    ImportImp( ImportXmi xmi, MofModelElement parent )
    {
        super(xmi, parent);
    }

    String getXmiAnnotation()
    { return ((ImportXmi)xmi_).annotation_; }

    String getXmiName()
    { return ((ImportXmi)xmi_).name_; }


    private String isClustered_;
    private MofVisibilityKind visibility_;


    /// implements {@link MofImport#isClustered}
    public boolean isClustered()
    {
        if( isClustered_==null )
        {
            isClustered_ = ((ImportXmi)xmi_).isClustered_;
            if( isClustered_==null )
            {
                isClustered_ = "false";
            }
        }
        return isClustered_.equalsIgnoreCase("true");
    }


    /// implements {@link MofImport#getVisibility}
    public MofVisibilityKind getVisibility() throws IllegalArgumentException
    {
        if( visibility_==null )
        {
            String text = ((ImportXmi)xmi_).visibility_;
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


    /// implements {@link MofImport#getImported}
    public MofNamespace getImported()
    {
        Vector children = xmi_.findChildren(MImport_importedNamespace.xmlName__);
        for( int i=0; i<children.size(); ++i )
        {
            MImport_importedNamespace ns = (MImport_importedNamespace)children.get(i);
            for( int j=0; j<ns.size(); ++j )
            {
                Object obj = ns.get(j);
                if( obj instanceof Worker )
                {
                    MofModelElement e = ((Worker)obj).mof();
                    if( e instanceof MofNamespace )
                    {
                        return (MofNamespace)e;
                    }
                }
            }
        }
        return null;
    }


    /// implements {@link MofModelElement#process}
    public void process( NodeHandler handler ) throws NodeHandlerException
    {
        handler.beginImport(this);
        handler.endModelElement(this);
    }

}
