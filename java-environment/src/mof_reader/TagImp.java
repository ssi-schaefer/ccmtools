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

import mof_xmi_parser.DTD_Container;


/**
 * Tag implementation
 *
 * @author Robert Lechner (robert.lechner@salomon.at)
 * @version $Date$
 */
class TagImp extends ModelElementImp implements MofTag
{
    TagImp( DTD_Container xmi, MofModelElement parent )
    {
        super(xmi, parent);
    }


    /// implements {@link MofTag#getTagId}
    public String getTagId()
    {
        // TODO
        return null;
    }


    /// implements {@link MofTag#getValues}
    public List getValues()
    {
        // TODO
        return null;
    }


    /// implements {@link MofTag#getModelElement}
    public MofModelElement getModelElement()
    {
        return parent_;
    }


    /// implements {@link MofModelElement#process}
    public void process( NodeHandler handler ) throws NodeHandlerException
    {
        // TODO
    }
}
