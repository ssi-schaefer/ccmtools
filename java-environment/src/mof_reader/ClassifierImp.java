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
 * Classifier implementation
 *
 * @author Robert Lechner (robert.lechner@salomon.at)
 * @version $Date$
 */
class ClassifierImp extends GeneralizableElementImp implements MofClassifier
{
    ClassifierImp( ClassifierXmi xmi, MofModelElement parent )
    {
        super(xmi, parent);
    }

    String getXmiAnnotation()
    { return ((ClassifierXmi)xmi_).annotation_; }

    String getXmiName()
    { return ((ClassifierXmi)xmi_).name_; }

    String getXmiAbstract()
    { return ((ClassifierXmi)xmi_).isAbstract_; }

    String getXmiLeaf()
    { return ((ClassifierXmi)xmi_).isLeaf_; }

    String getXmiRoot()
    { return ((ClassifierXmi)xmi_).isRoot_; }

    String getXmiVisibility()
    { return ((ClassifierXmi)xmi_).visibility_; }


    /// implements {@link MofModelElement#process}
    public void process( NodeHandler handler ) throws NodeHandlerException
    {
        throw new NodeHandlerException("ClassifierImp.process(NodeHandler) may not be called");
    }
}
