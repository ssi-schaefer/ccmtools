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


/**
 * Only used by the implementations of {@link MofModelElement#process}.
 * Implemented by an external application.
 *
 * @author Robert Lechner (robert.lechner@salomon.at)
 * @version $Date$
 */
public interface NodeHandler
{
    public void endModelElement( mof_reader.MofModelElement element ) throws NodeHandlerException;
    public void beginImport( mof_reader.MofImport element )  throws NodeHandlerException;
    public void beginTag( mof_reader.MofTag element )  throws NodeHandlerException;
    public void beginConstraint( mof_reader.MofConstraint element )  throws NodeHandlerException;
    public void beginConstant( mof_reader.MofConstant element )  throws NodeHandlerException;
    public void beginStructureField( mof_reader.MofStructureField element )  throws NodeHandlerException;
    public void beginParameter( mof_reader.MofParameter element )  throws NodeHandlerException;
    public void beginAssociationEnd( mof_reader.MofAssociationEnd element )  throws NodeHandlerException;
    public void beginReference( mof_reader.MofReference element )  throws NodeHandlerException;
    public void beginAttribute( mof_reader.MofAttribute element )  throws NodeHandlerException;
    public void beginException( mof_reader.MofException element )  throws NodeHandlerException;
    public void beginOperation( mof_reader.MofOperation element )  throws NodeHandlerException;
    public void beginPackage( mof_reader.MofPackage element )  throws NodeHandlerException;
    public void beginAssociation( mof_reader.MofAssociation element )  throws NodeHandlerException;
    public void beginClass( mof_reader.MofClass element )  throws NodeHandlerException;
    public void beginPrimitiveType( mof_reader.MofPrimitiveType element )  throws NodeHandlerException;
    public void beginStructureType( mof_reader.MofStructureType element )  throws NodeHandlerException;
    public void beginEnumerationType( mof_reader.MofEnumerationType element )  throws NodeHandlerException;
    public void beginCollectionType( mof_reader.MofCollectionType element )  throws NodeHandlerException;
    public void beginAliasType( mof_reader.MofAliasType element )  throws NodeHandlerException;
}
