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

import mof_xmi_parser.DTD_Creator;
import mof_xmi_parser.DTD_Container;
import org.xml.sax.Attributes;


/**
 * The factory for all implementation classes.
 *
 * @author Robert Lechner (robert.lechner@salomon.at)
 * @version $Date$
 */
class XmiFactory extends DTD_Creator
{
    /**
     * extends {@link mof_xmi_parser.DTD_Creator#create}
     */
    public DTD_Container create( String qName, Attributes attrs )
    {
        if(qName.equals(ImportXmi.xmlName__))  return new ImportXmi(attrs);
        if(qName.equals(TagXmi.xmlName__))  return new TagXmi(attrs);
        if(qName.equals(ConstraintXmi.xmlName__))  return new ConstraintXmi(attrs);
        if(qName.equals(PackageXmi.xmlName__))  return new PackageXmi(attrs);
        if(qName.equals(AssociationXmi.xmlName__))  return new AssociationXmi(attrs);
        if(qName.equals(ClassXmi.xmlName__))  return new ClassXmi(attrs);
        if(qName.equals(PrimitiveTypeXmi.xmlName__))  return new PrimitiveTypeXmi(attrs);
        if(qName.equals(StructureTypeXmi.xmlName__))  return new StructureTypeXmi(attrs);
        if(qName.equals(EnumerationTypeXmi.xmlName__))  return new EnumerationTypeXmi(attrs);
        if(qName.equals(StructureFieldXmi.xmlName__))  return new StructureFieldXmi(attrs);
        if(qName.equals(ConstantXmi.xmlName__))  return new ConstantXmi(attrs);
        if(qName.equals(ParameterXmi.xmlName__))  return new ParameterXmi(attrs);
        if(qName.equals(AssociationEndXmi.xmlName__))  return new AssociationEndXmi(attrs);
        if(qName.equals(ReferenceXmi.xmlName__))  return new ReferenceXmi(attrs);
        if(qName.equals(AttributeXmi.xmlName__))  return new AttributeXmi(attrs);
        if(qName.equals(AliasTypeXmi.xmlName__))  return new AliasTypeXmi(attrs);
        if(qName.equals(CollectionTypeXmi.xmlName__))  return new CollectionTypeXmi(attrs);
        if(qName.equals(ExceptionXmi.xmlName__))  return new ExceptionXmi(attrs);
        if(qName.equals(OperationXmi.xmlName__))  return new OperationXmi(attrs);
        return super.create(qName, attrs);
    }
}
