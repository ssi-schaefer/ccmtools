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
import mof_xmi_parser.model.MImport_isClustered;
import mof_xmi_parser.model.MImport_visibility;
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
            Vector children = xmi_.findChildren(MImport_isClustered.xmlName__);
            if( children.size()>=1 )
            {
                MImport_isClustered x = (MImport_isClustered)children.get(0);
                if( x.size()>=1 )
                {
                    isClustered_ = x.get(0).toString();
                }
            }
            if( isClustered_==null )
            {
                isClustered_ = "false";
            }
        }
        return isClustered_.equalsIgnoreCase("true");
    }


    /// implements {@link MofImport#getVisibility}
    public MofVisibilityKind getVisibility()
    {
        if( visibility_==null )
        {
            try
            {
                String text = ((ImportXmi)xmi_).visibility_;
                if( text!=null )
                {
                    visibility_ = MofVisibilityKind.create(text);
                }
                else
                {
                    Vector children = xmi_.findChildren(MImport_visibility.xmlName__);
                    if( children.size()>=1 )
                    {
                        MImport_visibility v = (MImport_visibility)children.get(0);
                        visibility_ = MofVisibilityKind.create(v.xmi_value_);
                    }
                }
            }
            catch( IllegalArgumentException e )
            {
                e.printStackTrace();
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
        // TODO
    }

}
