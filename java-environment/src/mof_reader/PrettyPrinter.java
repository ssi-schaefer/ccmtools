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
import java.util.Collection;
import java.util.Iterator;


/**
 * Writes the model to {@link java.lang.System#out}.
 *
 * @author Robert Lechner (robert.lechner@salomon.at)
 * @version $Date$
 */
public class PrettyPrinter implements NodeHandler
{
    private int depth_;

    private void spaces()
    {
        for( int i=0; i<depth_; ++i )
        {
            System.out.print("  ");
        }
    }


    public void endModelElement( MofModelElement element ) throws NodeHandlerException
    {
        --depth_;
    }

    public void beginImport( MofImport element )  throws NodeHandlerException
    {
        spaces();
        ++depth_;
        System.out.println("Import");
        showModelElement(element);
        spaces(); System.out.println("isClustered="+element.isClustered());
        spaces(); System.out.println("visibility="+element.getVisibility());
        spaces(); System.out.println("imported="+element.getImported().getName());
    }

    public void beginTag( MofTag element )  throws NodeHandlerException
    {
        spaces();
        ++depth_;
        System.out.println("Tag");
        showModelElement(element);
        spaces(); System.out.println("tagid="+element.getTagId());
        Iterator it = element.getValues().iterator();
        while( it.hasNext() )
        {
            spaces(); System.out.print("-> ");
            System.out.println(it.next().toString());
        }
    }

    public void beginConstraint( MofConstraint element )  throws NodeHandlerException
    {
        spaces();
        ++depth_;
        System.out.println("Constraint");
        showModelElement(element);
        spaces(); System.out.println("evaluationPolicy="+element.getEvaluationPolicy());
        spaces(); System.out.println("expression="+element.getExpression());
        spaces(); System.out.println("language="+element.getLanguage());
    }

    public void beginConstant( MofConstant element )  throws NodeHandlerException
    {
        spaces();
        ++depth_;
        System.out.println("Constant");
        showTypedElement(element);
        spaces(); System.out.println("value="+element.getValue());
    }

    public void beginStructureField( MofStructureField element )  throws NodeHandlerException
    {
        spaces();
        ++depth_;
        System.out.println("StructureField");
        showTypedElement(element);
    }

    public void beginParameter( MofParameter element )  throws NodeHandlerException
    {
        spaces();
        ++depth_;
        System.out.println("Parameter");
        showTypedElement(element);
        spaces(); System.out.println("direction="+element.getDirection());
        spaces(); System.out.println("multiplicity="+element.getMultiplicity());
    }

    public void beginAssociationEnd( MofAssociationEnd element )  throws NodeHandlerException
    {
        spaces();
        ++depth_;
        System.out.println("AssociationEnd");
        showTypedElement(element);
        spaces(); System.out.println("aggregation="+element.getAggregation());
        spaces(); System.out.println("isChangeable="+element.isChangeable());
        spaces(); System.out.println("isNavigable="+element.isNavigable());
        spaces(); System.out.println("multiplicity="+element.getMultiplicity());
    }

    private void showTypedElement( MofTypedElement element )  throws NodeHandlerException
    {
        showModelElement(element);
        MofClassifier type = element.getType();
        spaces(); System.out.println("type="+(type==null ? "NULL" : type.getName()));
    }

    public void beginReference( MofReference element )  throws NodeHandlerException
    {
        spaces();
        ++depth_;
        System.out.println("Reference");
        showStructuralFeature(element);
        spaces(); System.out.println("referencedEnd="+element.getReferencedEnd().getName());
    }

    public void beginAttribute( MofAttribute element )  throws NodeHandlerException
    {
        spaces();
        ++depth_;
        System.out.println("Attribute");
        showStructuralFeature(element);
        spaces(); System.out.println("isDerived="+element.isDerived());
    }

    private void showStructuralFeature( MofStructuralFeature element )  throws NodeHandlerException
    {
        showTypedElement(element);
        showFeature(element);
        spaces(); System.out.println("isChangeable="+element.isChangeable());
        spaces(); System.out.println("multiplicity="+element.getMultiplicity());
    }

    private void showFeature( MofFeature element )
    {
        spaces(); System.out.println("scope="+element.getScope());
        spaces(); System.out.println("visibility="+element.getVisibility());
    }

    public void beginException( MofException element )  throws NodeHandlerException
    {
        spaces();
        ++depth_;
        System.out.println("Exception");
        showBehavioralFeature(element);
    }

    private void showBehavioralFeature( MofBehavioralFeature element )  throws NodeHandlerException
    {
        showNamespace(element);
        showFeature(element);
    }

    public void beginOperation( MofOperation element )  throws NodeHandlerException
    {
        spaces();
        ++depth_;
        System.out.println("Operation");
        showBehavioralFeature(element);
        spaces(); System.out.println("isQuery="+element.isQuery());
        Iterator it = element.getExceptions().iterator();
        while( it.hasNext() )
        {
            spaces(); System.out.print("  raises ");
            System.out.println(((MofException)it.next()).getName());
        }
    }

    public void beginPackage( MofPackage element )  throws NodeHandlerException
    {
        spaces();
        ++depth_;
        System.out.println("Package");
        showGeneralizableElement(element);
        showChildren(element.getContainedElements().iterator());
    }

    private void showChildren( Iterator it )  throws NodeHandlerException
    {
        while( it.hasNext() )
        {
            MofModelElement element = (MofModelElement)it.next();
            if( element==null )
            {
                spaces(); System.out.println("NULL CHILD");
            }
            else
            {
                element.process(this);
            }
        }
    }

    private void showGeneralizableElement( MofGeneralizableElement element )  throws NodeHandlerException
    {
        showNamespace(element);
        spaces(); System.out.println("isAbstract="+element.isAbstract());
        spaces(); System.out.println("isLeaf="+element.isLeaf());
        spaces(); System.out.println("isRoot="+element.isRoot());
        spaces(); System.out.println("visibility="+element.getVisibility());
        spaces(); System.out.println("number of supertypes="+element.getSupertypes().size());
        Iterator it = element.getSupertypes().iterator();
        while( it.hasNext() )
        {
            spaces(); System.out.println("=> "+((MofGeneralizableElement)it.next()).getName());
        }
    }

    private void showNamespace( MofNamespace element )  throws NodeHandlerException
    {
        showModelElement(element);
        List elements = element.getContainedElements();
        spaces(); System.out.println("number of contained elements="+elements.size());
    }

    private void showModelElement( MofModelElement element )  throws NodeHandlerException
    {
        spaces(); System.out.println("name="+element.getName());
        spaces(); System.out.println("annotation="+element.getAnnotation());
        spaces(); System.out.print("qualified name=");
        boolean setPoint=false;
        Iterator it = element.getQualifiedName().iterator();
        while( it.hasNext() )
        {
            if( setPoint )  System.out.print("::");
            else setPoint=true;
            System.out.print(it.next().toString());
        }
        System.out.println("");
        showChildren(element.getConstraints().iterator());
        showChildren(element.getTags().iterator());
    }

    public void beginAssociation( MofAssociation element )  throws NodeHandlerException
    {
        spaces();
        ++depth_;
        System.out.println("Association");
        showGeneralizableElement(element);
        spaces(); System.out.println("isDerived="+element.isDerived());
        showChildren(element.getContainedElements().iterator());
    }

    public void beginClass( MofClass element )  throws NodeHandlerException
    {
        spaces();
        ++depth_;
        System.out.println("Class");
        showGeneralizableElement(element);
        spaces(); System.out.println("isSingleton="+element.isSingleton());
        showChildren(element.getContainedElements().iterator());
    }

    public void beginPrimitiveType( MofPrimitiveType element )  throws NodeHandlerException
    {
        spaces();
        ++depth_;
        System.out.println("PrimitiveType");
        showGeneralizableElement(element);
        showChildren(element.getContainedElements().iterator());
    }

    public void beginStructureType( MofStructureType element )  throws NodeHandlerException
    {
        spaces();
        ++depth_;
        System.out.println("StructureType");
        showGeneralizableElement(element);
        showChildren(element.getContainedElements().iterator());
    }

    public void beginEnumerationType( MofEnumerationType element )  throws NodeHandlerException
    {
        spaces();
        ++depth_;
        System.out.println("EnumerationType");
        showGeneralizableElement(element);
        Iterator it = element.getLabels().iterator();
        while( it.hasNext() )
        {
            spaces(); System.out.println("-> "+it.next().toString());
        }
        showChildren(element.getContainedElements().iterator());
    }

    public void beginCollectionType( MofCollectionType element )  throws NodeHandlerException
    {
        spaces();
        ++depth_;
        System.out.println("AliasType");
        showGeneralizableElement(element);
        MofClassifier type = element.getType();
        spaces(); System.out.println("type="+(type==null ? "NULL" : type.getName()));
        spaces(); System.out.println("multiplicity="+element.getMultiplicity());
        showChildren(element.getContainedElements().iterator());
    }

    public void beginAliasType( MofAliasType element )  throws NodeHandlerException
    {
        spaces();
        ++depth_;
        System.out.println("AliasType");
        showGeneralizableElement(element);
        MofClassifier type = element.getType();
        spaces(); System.out.println("type="+(type==null ? "NULL" : type.getName()));
        showChildren(element.getContainedElements().iterator());
    }

}
