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
 * AssociationEnd XMI-object
 *
 * @author Robert Lechner (robert.lechner@salomon.at)
 * @version $Date$
 */
class AssociationEndXmi extends mof_xmi_parser.model.MAssociationEnd implements Worker
{
    AssociationEndXmi( org.xml.sax.Attributes attrs )
    {
        super(attrs);
    }

    private AssociationEndImp implementation_;
    private Worker parent_;

    /// implements {@link Worker#register}
    public void register( java.util.Map map, Worker parent )
    {
        parent_ = parent;
        implementation_ = new AssociationEndImp(this, parent==null ? null : parent.mof());
        map.put(TagXmi.createId(xmi_id_), implementation_);
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

    /// implements {@link Worker#process}
    public void process( Model model )
    {
        // TODO
    }
}
