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
import mof_xmi_parser.model.MNamespace_contents;


/**
 * StructureType XMI-object
 *
 * @author Robert Lechner (robert.lechner@salomon.at)
 * @version $Date$
 */
class StructureTypeXmi extends mof_xmi_parser.model.MStructureType implements Worker
{
    StructureTypeXmi( org.xml.sax.Attributes attrs )
    {
        super(attrs);
    }

    private StructureTypeImp implementation_;
    private Worker parent_;

    /// implements {@link Worker#register}
    public void register( java.util.Map map, Worker parent )
    {
        parent_ = parent;
        implementation_ = new StructureTypeImp(this, parent==null ? null : parent.mof());
        map.put(TagXmi.createId(xmi_id_), this);
        Iterator it = content().iterator();
        while( it.hasNext() )
        {
            Object obj = it.next();
            if( obj instanceof Worker )
            {
                ((Worker)obj).register(map, this);
            }
            else if( obj instanceof MNamespace_contents )
            {
                Iterator it2 = ((MNamespace_contents)obj).content().iterator();
                while( it2.hasNext() )
                {
                    Object o2 = it2.next();
                    if( o2 instanceof Worker )
                    {
                        ((Worker)o2).register(map, this);
                    }
                    else
                    {
                        System.err.println("StructureTypeXmi.register - it2: unknown child : "+
                                            o2.getClass().getName());
                    }
                }
            }
            else
            {
                System.err.println("StructureTypeXmi.register - main: unknown child : "+obj.getClass().getName());
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
            implementation_ = (StructureTypeImp)w.mof();
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


    public void moveAssociationEnds()
    {
        Iterator it = content().iterator();
        while( it.hasNext() )
        {
            Object obj = it.next();
            if( obj instanceof Worker )
            {
                ((Worker)obj).moveAssociationEnds();
            }
        }
    }

}
