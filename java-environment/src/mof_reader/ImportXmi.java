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
import mof_xmi_parser.model.MImport_importedNamespace;


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
            else if( obj instanceof MImport_importedNamespace )
            {
                registerHelpers(((MImport_importedNamespace)obj).content().iterator(), map);
            }
            else
            {
                System.err.println("ImportXmi.register - main: unknown child : "+obj.getClass().getName());
            }
        }
    }

    private void registerHelpers( Iterator it2, java.util.Map map )
    {
        while( it2.hasNext() )
        {
            Object o2 = it2.next();
            if( o2 instanceof Worker )
            {
                ((Worker)o2).register(map, this);
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
