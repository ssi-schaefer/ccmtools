package ccmtools.generator.java.metamodel.plugin;

import java.util.HashSet;
import java.util.Set;

import ccmtools.generator.java.metamodel.PassingDirection;
import ccmtools.generator.java.metamodel.Type;

public class EntityContainer
	implements Type
{
	/*************************************************************************
	 * Local Interface Generator Methods
	 * 
	 *************************************************************************/
	
	public Set getJavaImportStatements()
	{
		Set importStatements = new HashSet();
		importStatements.add("import wx.entitycontainer.IEntityContainer;");
		return importStatements;
	}
	
	public String generateJavaConstant(Object value)
	{
		return value.toString();
	}
	
	public String generateJavaMapping()
	{
		return "wx.entitycontainer.IEntityContainer";
	}
	
	public String generateJavaMapping(PassingDirection direction)
	{
		if (direction == PassingDirection.IN
			|| direction == PassingDirection.RESULT)
		{			
			return generateJavaMapping();
		}
		else // INOUT, OUT
		{
			return generateJavaHolderType();
		}
	}
	
	public String generateJavaMappingObject()
	{
		return generateJavaMapping();
	}
	
	public String generateJavaHolderType()
	{
		return "ccm.localHolder<" + generateJavaMappingObject() + ">";
	}
	

	
	/*************************************************************************
	 * Application Generator Methods
	 * 
	 *************************************************************************/
	
	public String generateJavaDefaultReturnValue()
	{
		return "null";		
	}
	
	
	/*************************************************************************
	 * Client Library Generator Methods
	 * 
	 *************************************************************************/

	public String generateCorbaMapping()
	{
		return "org.omg.CORBA.Any";
	}
	
	public String generateCorbaMapping(PassingDirection direction)
	{
		if (direction == PassingDirection.IN
			|| direction == PassingDirection.RESULT)
		{
			return generateCorbaMapping();
		}
		else // INOUT, OUT
		{
			return generateCorbaHolderType();
		}
	}		
	
	public String generateCorbaConverterType()
	{
		return "EntityContainerCorbaConverter.convert";
	}
	
	public String generateCorbaHolderType()
	{
		return "org.omg.CORBA.AnyHolder";
	}	
}
