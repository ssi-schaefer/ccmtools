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


/**
 * Import implementation
 *
 * @author Robert Lechner (robert.lechner@salomon.at)
 * @version $Date$
 */
class ImportImp extends ModelElementImp implements MofImport
{
    ImportImp( DTD_Container xmi, MofModelElement parent )
    {
        super(xmi, parent);
    }


    private String isClustered_;


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
        // TODO
        return null;
    }


    /// implements {@link MofImport#getImported}
    public MofNamespace getImported()
    {
        // TODO
        return null;
    }


    /// implements {@link MofModelElement#process}
    public void process( NodeHandler handler ) throws NodeHandlerException
    {
        // TODO
    }

}
