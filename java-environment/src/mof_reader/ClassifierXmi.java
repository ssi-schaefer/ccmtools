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
import mof_xmi_parser.model.MGeneralizableElement_supertypes;


/**
 * Classifier XMI-object
 *
 * @author Robert Lechner (robert.lechner@salomon.at)
 * @version $Date$
 */
class ClassifierXmi extends mof_xmi_parser.model.MClassifier implements Worker
{
    ClassifierXmi( org.xml.sax.Attributes attrs )
    {
        super(attrs);
    }

    private ModelElementImp implementation_;
    private Worker parent_;

    /// implements {@link Worker#register}
    public void register( java.util.Map map, Worker parent )
    {
        parent_ = parent;
        implementation_ = new ClassifierImp(this, parent==null ? null : parent.mof());
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
                        System.err.println("ClassifierXmi.register - it2: unknown child : "+
                                            o2.getClass().getName());
                    }
                }
            }
            else if( obj instanceof MGeneralizableElement_supertypes )
            {
                Iterator it3 = ((MGeneralizableElement_supertypes)obj).content().iterator();
                while( it3.hasNext() )
                {
                    Object o3 = it3.next();
                    if( o3 instanceof Worker )
                    {
                        ((Worker)o3).register(map, this);
                    }
                    else
                    {
                        System.err.println("ClassifierXmi.register - it3: unknown child : "+
                                            o3.getClass().getName());
                    }
                }
            }
            else
            {
                System.err.println("ClassifierXmi.register - main: unknown child : "+obj.getClass().getName());
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
        if( xmi_idref_!=null && xmi_idref_.length()>0 )
        {
            Worker w = model.getWorker(xmi_idref_);
            if( w==null )
            {
                throw new RuntimeException("cannot find xmi.idref=="+xmi_idref_);
            }
            else
            {
                w.process(model);
                implementation_ = (ModelElementImp)w.mof();
            }
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
