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
import java.util.ArrayList;
import java.util.Vector;
import java.util.Iterator;

import mof_xmi_parser.DTD_Container;
import mof_xmi_parser.model.MNamespace_contents;


/**
 * Namespace implementation
 *
 * @author Robert Lechner (robert.lechner@salomon.at)
 * @version $Date$
 */
abstract class NamespaceImp extends ModelElementImp implements MofNamespace
{
    NamespaceImp( DTD_Container xmi, MofModelElement parent )
    {
        super(xmi, parent);
    }


    private ArrayList contents_;


    /// implements {@link MofNamespace#getContainedElements}
    public List getContainedElements()
    {
        if( contents_==null )
        {
            contents_ = new ArrayList();
            Vector children = xmi_.findChildren(MNamespace_contents.xmlName__);
            for( int i=0; i<children.size(); ++i )
            {
                MNamespace_contents ns = (MNamespace_contents)children.get(i);
                for( int j=0; j<ns.size(); ++j )
                {
                    Object obj = ns.get(j);
                    if( obj instanceof Worker )
                    {
                        MofModelElement child = ((Worker)obj).mof();
                        if( child==null )
                        {
                            System.err.println("NULL CHILD in class "+obj.getClass().getName());
                        }
                        else
                        {
                            contents_.add(child);
                        }
                    }
                }
            }
        }
        return contents_;
    }


    /// implements {@link MofNamespace#processContainedElements}
    public void processContainedElements( NodeHandler handler ) throws NodeHandlerException
    {
        Iterator it = getContainedElements().iterator();
        while( it.hasNext() )
        {
            ((MofModelElement)it.next()).process(handler);
        }
    }

}
