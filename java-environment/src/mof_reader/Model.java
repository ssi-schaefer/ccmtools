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
import java.util.Hashtable;
import java.util.Map;
import java.util.Collection;
import java.util.Iterator;

import mof_xmi_parser.DTD_Container;
import mof_xmi_parser.MXMI;
import mof_xmi_parser.MXMI_header;
import mof_xmi_parser.MXMI_content;


/**
 * The MOF model.
 *
 * @author Robert Lechner (robert.lechner@salomon.at)
 * @version $Date$
 */
public class Model
{
    /**
     * Creates the MOF model.
     *
     * @param root  the result of {@link mof_xmi_parser.DTD_Root#parse}
     *
     * @throws IllegalArgumentException if root is not of type {@link mof_xmi_parser.MXMI}
     */
    Model( DTD_Container root ) throws IllegalArgumentException
    {
        if( !(root instanceof MXMI) )
        {
            throw new IllegalArgumentException("wrong root class: "+root.getClass().getName());
        }
        root_ = (MXMI)root;
        int s = root.size();
        for( int index=0; index<s; ++index )
        {
            Object element = root.get(index);
            if( element instanceof MXMI_header )
            {
                header_ = (MXMI_header)element;
            }
            else if( element instanceof MXMI_content )
            {
                process((MXMI_content)element);
            }
            else
            {
                System.out.println("root: unknown element class: "+element.getClass().getName());
            }
        }
        Iterator it = workers_.values().iterator();
        while( it.hasNext() )
        {
            ((Worker)it.next()).process(this);
        }
    }


    /// returns a known worker
    Worker getWorker( Object id )
    {
        return (Worker)workers_.get(id);
    }


    /// the main container
    private MXMI root_;

    /// the XMI-header
    private MXMI_header header_;


    /**
     * returns the XMI-version
     */
    public String getXmiVersion()
    {
        return root_.xmi_version_;
    }

    /**
     * returns the timestamp (or null)
     */
    public String getTimestamp()
    {
        return root_.timestamp_;
    }


    /// all known root model elements
    private Map workers_ = new Hashtable();

    /// all root model elements
    private Collection content_;


    private void process( MXMI_content xmiContent )
    {
        content_ = xmiContent.content();
        int s = xmiContent.size();
        for( int index=0; index<s; ++index )
        {
            Object obj = xmiContent.get(index);
            if( obj instanceof Worker )
            {
                ((Worker)obj).register(workers_, null);
            }
            else
            {
                System.out.println("content: unknown class: "+obj.getClass().getName());
            }
        }
    }


    /**
     * returns an iterator for all root model elements ({@link Worker})
     */
    public Iterator iterator()
    {
        return content_.iterator();
    }
}
