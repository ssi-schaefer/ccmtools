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
import mof_xmi_parser.DTD_Container;


/**
 * GeneralizableElement implementation
 *
 * @author Robert Lechner (robert.lechner@salomon.at)
 * @version $Date$
 */
abstract class GeneralizableElementImp extends NamespaceImp implements MofGeneralizableElement
{
    GeneralizableElementImp( DTD_Container xmi, MofModelElement parent )
    {
        super(xmi, parent);
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

}
