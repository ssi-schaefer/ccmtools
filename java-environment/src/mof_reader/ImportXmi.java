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


/**
 * Import XMI-object
 *
 * @author Robert Lechner (robert.lechner@salomon.at)
 * @version $Date$
 */
class ImportXmi extends mof_xmi_parser.model.MImport implements Worker
{
    ImportXmi( org.xml.sax.Attributes attrs )
    {
        super(attrs);
    }

    private ImportImp implementation_;
    private Worker parent_;

    /// implements {@link Worker#register}
    public void register( java.util.Map map, Worker parent )
    {
        parent_ = parent;
        implementation_ = new ImportImp(this, parent==null ? null : parent.mof());
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
            implementation_ = (ImportImp)w.mof();
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

}
