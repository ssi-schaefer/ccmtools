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
        if(qName.equals(ImportImp.xmlName__))  return new ImportImp(attrs);
        if(qName.equals(TagImp.xmlName__))  return new TagImp(attrs);
        if(qName.equals(ConstraintImp.xmlName__))  return new ConstraintImp(attrs);
        if(qName.equals(PackageImp.xmlName__))  return new PackageImp(attrs);
        if(qName.equals(AssociationImp.xmlName__))  return new AssociationImp(attrs);
        if(qName.equals(ClassImp.xmlName__))  return new ClassImp(attrs);
        if(qName.equals(PrimitiveTypeImp.xmlName__))  return new PrimitiveTypeImp(attrs);
        if(qName.equals(StructureTypeImp.xmlName__))  return new StructureTypeImp(attrs);
        if(qName.equals(EnumerationTypeImp.xmlName__))  return new EnumerationTypeImp(attrs);
        if(qName.equals(StructureFieldImp.xmlName__))  return new StructureFieldImp(attrs);
        if(qName.equals(ConstantImp.xmlName__))  return new ConstantImp(attrs);
        if(qName.equals(ParameterImp.xmlName__))  return new ParameterImp(attrs);
        if(qName.equals(AssociationEndImp.xmlName__))  return new AssociationEndImp(attrs);
        if(qName.equals(ReferenceImp.xmlName__))  return new ReferenceImp(attrs);
        if(qName.equals(AttributeImp.xmlName__))  return new AttributeImp(attrs);
        if(qName.equals(AliasTypeImp.xmlName__))  return new AliasTypeImp(attrs);
        if(qName.equals(CollectionTypeImp.xmlName__))  return new CollectionTypeImp(attrs);
        if(qName.equals(ExceptionImp.xmlName__))  return new ExceptionImp(attrs);
        if(qName.equals(OperationImp.xmlName__))  return new OperationImp(attrs);
        return super.create(qName, attrs);
    }
}
