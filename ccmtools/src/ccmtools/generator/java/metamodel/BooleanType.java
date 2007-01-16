package ccmtools.generator.java.metamodel;

import java.util.Set;
import java.util.TreeSet;

public class BooleanType
	implements Type
{
	public Set<String> getJavaImportStatements()
	{
		return new TreeSet<String>();
	}

	
	/*************************************************************************
	 * Local Interface Generator Methods
	 *************************************************************************/
	
	public String generateJavaConstant(Object value)
	{
		return value.toString();
	}
	
	public String generateJavaMapping()
	{
		return "boolean";
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
		return "Boolean";
	}
	
	public String generateJavaHolderType()
	{
		return "Holder<" + generateJavaMappingObject() + ">";
	}
	
	
	
	/*************************************************************************
	 * Application Generator Methods
	 *************************************************************************/
		
	public String generateJavaDefaultReturnValue()
	{
		return "false";		
	}
	
	
	/*************************************************************************
	 * CORBA Adapter Generator Methods
	 *************************************************************************/
	
	public String generateCorbaMapping()
	{
		return "boolean";
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
	
	public String generateCorbaHolderType()
	{
		return "org.omg.CORBA.BooleanHolder";
	}


	public String generateCorbaConverterType()
	{
		return "";
	}	
}
