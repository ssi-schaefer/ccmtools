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

import java.util.Iterator;
import mof_xmi_parser.DTD_Container;


/**
 * Tag XMI-object
 *
 * @author Robert Lechner (robert.lechner@salomon.at)
 * @version $Date$
 */
class TagXmi extends mof_xmi_parser.model.MTag implements Worker
{
    TagXmi( org.xml.sax.Attributes attrs )
    {
        super(attrs);
    }

    private TagImp implementation_;
    private Worker parent_;

    /// implements {@link Worker#register}
    public void register( java.util.Map map, Worker parent )
    {
        parent_ = parent;
        implementation_ = new TagImp(this, parent==null ? null : parent.mof());
        map.put(TagXmi.createId(xmi_id_), this);
        Iterator it = content().iterator();
        while( it.hasNext() )
        {
            Object obj = it.next();
            if( obj instanceof Worker )
            {
                ((Worker)obj).register(map, this);
            }
        }
    }

    /// implements {@link Worker#mof}
    public MofModelElement mof()
    {
        return implementation_;
    }

    private boolean processed_;

    /// implements {@link Worker#process}
    public void process( Model model )
    {
        if( processed_ )
        {
            return;
        }
        if( xmi_idref_!=null )
        {
            Worker w = model.getWorker(xmi_idref_);
            if( w==null )
            {
                throw new RuntimeException("cannot find xmi.idref=="+xmi_idref_);
            }
            w.process(model);
            implementation_ = (TagImp)w.mof();
        }
        processed_ = true;
        Iterator it = content().iterator();
        while( it.hasNext() )
        {
            Object obj = it.next();
            if( obj instanceof Worker )
            {
                ((Worker)obj).process(model);
            }
        }
    }

    static String createId( String xmi_id )
    {
        if( xmi_id!=null )
        {
            return xmi_id;
        }
        ++id_counter_;
        return "\"'"+id_counter_;
    }

    private static int id_counter_;
}
