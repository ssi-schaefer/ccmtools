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
    public void endModelElement( MofModelElement element ) throws NodeHandlerException;
    public void beginImport( MofImport element )  throws NodeHandlerException;
    public void beginTag( MofTag element )  throws NodeHandlerException;
    public void beginConstraint( MofConstraint element )  throws NodeHandlerException;
    public void beginConstant( MofConstant element )  throws NodeHandlerException;
    public void beginStructureField( MofStructureField element )  throws NodeHandlerException;
    public void beginParameter( MofParameter element )  throws NodeHandlerException;
    public void beginAssociationEnd( MofAssociationEnd element )  throws NodeHandlerException;
    public void beginReference( MofReference element )  throws NodeHandlerException;
    public void beginAttribute( MofAttribute element )  throws NodeHandlerException;
    public void beginException( MofException element )  throws NodeHandlerException;
    public void beginOperation( MofOperation element )  throws NodeHandlerException;
    public void beginPackage( MofPackage element )  throws NodeHandlerException;
    public void beginAssociation( MofAssociation element )  throws NodeHandlerException;
    public void beginClass( MofClass element )  throws NodeHandlerException;
    public void beginPrimitiveType( MofPrimitiveType element )  throws NodeHandlerException;
    public void beginStructureType( MofStructureType element )  throws NodeHandlerException;
    public void beginEnumerationType( MofEnumerationType element )  throws NodeHandlerException;
    public void beginCollectionType( MofCollectionType element )  throws NodeHandlerException;
    public void beginAliasType( MofAliasType element )  throws NodeHandlerException;
}
