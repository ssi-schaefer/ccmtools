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


/**
 * Class implementation
 *
 * @author Robert Lechner (robert.lechner@salomon.at)
 * @version $Date$
 */
class ClassImp extends mof_xmi_parser.model.MClass implements MofClass, Worker
{
    ClassImp( org.xml.sax.Attributes attrs )
    {
        super(attrs);
    }


    /// implements {@link MofModelElement#process}
    public void process( NodeHandler handler ) throws NodeHandlerException
    {
        // TODO
    }

    /// implements {@link MofModelElement#getAnnotation}
    public String getAnnotation()
    {
        // TODO
        return null;
    }

    /// implements {@link MofModelElement#getName}
    public String getName()
    {
        // TODO
        return null;
    }

    /// implements {@link MofModelElement#getQualifiedName}
    public List getQualifiedName()
    {
        // TODO
        return null;
    }

    /// implements {@link MofModelElement#getProviders}
    public Collection getProviders()
    {
        // TODO
        return null;
    }

    /// implements {@link MofModelElement#getContainer}
    public MofNamespace getContainer()
    {
        // TODO
        return null;
    }

    /// implements {@link MofModelElement#getConstraints}
    public Collection getConstraints()
    {
        // TODO
        return null;
    }

    /// implements {@link MofModelElement#getTags}
    public List getTags()
    {
        // TODO
        return null;
    }


    /// implements {@link MofNamespace#getContainedElements}
    public List getContainedElements()
    {
        // TODO
        return null;
    }


    /// implements {@link MofGeneralizableElement#isAbstract}
    public boolean isAbstract()
    {
        // TODO
        return false;
    }

    /// implements {@link MofGeneralizableElement#isLeaf}
    public boolean isLeaf()
    {
        // TODO
        return false;
    }

    /// implements {@link MofGeneralizableElement#isRoot}
    public boolean isRoot()
    {
        // TODO
        return false;
    }

    /// implements {@link MofGeneralizableElement#getVisibility}
    public MofVisibilityKind getVisibility()
    {
        // TODO
        return null;
    }

    /// implements {@link MofGeneralizableElement#getSupertypes}
    public List getSupertypes()
    {
        // TODO
        return null;
    }


    /// implements {@link MofClass#isSingleton}
    public boolean isSingleton()
    {
        // TODO
        return false;
    }


    /// implements {@link Worker#register}
    public void register( java.util.Map map )
    {
        // TODO
    }

    /// implements {@link Worker#process}
    public void process( Model model )
    {
        // TODO
    }
}
