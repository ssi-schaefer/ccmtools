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
 * Attribute implementation
 *
 * @author Robert Lechner (robert.lechner@salomon.at)
 * @version $Date$
 */
class AttributeImp extends mof_xmi_parser.model.MAttribute implements MofAttribute, Worker
{
    AttributeImp( org.xml.sax.Attributes attrs )
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


    /// implements {@link MofTypedElement#getType}
    public MofClassifier getType()
    {
        // TODO
        return null;
    }


    /// implements {@link MofFeature#getScope}
    public MofScopeKind getScope()
    {
        // TODO
        return null;
    }

    /// implements {@link MofFeature#getVisibility}
    public MofVisibilityKind getVisibility()
    {
        // TODO
        return null;
    }


    /// implements {@link MofStructuralFeature#isChangeable}
    public boolean isChangeable()
    {
        // TODO
        return false;
    }

    /// implements {@link MofStructuralFeature#getMultiplicity}
    public MofMultiplicityType getMultiplicity()
    {
        // TODO
        return null;
    }


    /// implements {@link MofAttribute#isDerived}
    public boolean isDerived()
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
