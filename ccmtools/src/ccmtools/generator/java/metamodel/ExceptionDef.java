package ccmtools.generator.java.metamodel;

import java.util.ArrayList;
import java.util.List;

public class ExceptionDef
	extends ModelElement
{
	List parameter = new ArrayList();

	public ExceptionDef(String identifier, List ns)
	{
		setIdentifier(identifier);
		setIdlNamespaceList(ns);	
	}
		
	public List getParameter()
	{
		return parameter;
	}
	
	
	/*************************************************************************
	 * Local Interface Generator Methods
	 * 
	 *************************************************************************/
	
	public String generateJavaMapping()
	{
		return generateAbsoluteIdlName();
	}
	
	
	
	/*************************************************************************
	 * Implementation Generator Methods
	 * 
	 *************************************************************************/
	
	public String generateJavaDefaultReturnValue()
	{
		return "null";		
	}
}
