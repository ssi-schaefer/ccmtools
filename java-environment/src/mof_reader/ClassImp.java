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
 * Class implementation
 *
 * @author Robert Lechner (robert.lechner@salomon.at)
 * @version $Date$
 */
class ClassImp extends GeneralizableElementImp implements MofClass
{
    ClassImp( ClassXmi xmi, MofModelElement parent )
    {
        super(xmi, parent);
    }

    String getXmiAnnotation()
    { return ((ClassXmi)xmi_).annotation_; }

    String getXmiName()
    { return ((ClassXmi)xmi_).name_; }

    String getXmiAbstract()
    { return ((ClassXmi)xmi_).isAbstract_; }

    String getXmiLeaf()
    { return ((ClassXmi)xmi_).isLeaf_; }

    String getXmiRoot()
    { return ((ClassXmi)xmi_).isRoot_; }

    String getXmiVisibility()
    { return ((ClassXmi)xmi_).visibility_; }


    private String isSingleton_;


    /// implements {@link MofClass#isSingleton}
    public boolean isSingleton()
    {
        if( isSingleton_==null )
        {
            isSingleton_ = ((ClassXmi)xmi_).isSingleton_;
            if( isSingleton_==null )
            {
                isSingleton_ = "false";
            }
        }
        return isSingleton_.equalsIgnoreCase("true");
    }


    /// implements {@link MofModelElement#process}
    public void process( NodeHandler handler ) throws NodeHandlerException
    {
        handler.beginClass(this);
        handler.endModelElement(this);
    }
}
