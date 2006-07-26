package ccmtools.generator.java.metamodel;

import java.util.Set;
import java.util.TreeSet;

public class ByteType
	implements Type
{	
	public Set<String> getJavaImportStatements()
	{
		return new TreeSet<String>();
	}

	
	/*************************************************************************
	 * Local Interface Generator Methods
	 * 
	 *************************************************************************/
	
	public String generateJavaConstant(Object value)
	{
		return value.toString();
	}
		
	public String generateJavaMapping()
	{
		return "byte";
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
		return "Byte";
	}
	
	public String generateJavaHolderType()
	{
		return "Holder<" + generateJavaMappingObject() + ">";
	}
	
	
	
	/*************************************************************************
	 * Application Generator Methods
	 * 
	 *************************************************************************/
		
	public String generateJavaDefaultReturnValue()
	{
		return "0";		
	}
	
	
	/*************************************************************************
	 * CORBA Component Generator Methods
	 * 
	 *************************************************************************/

	public String generateCorbaMapping()
	{
		return "byte";
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
		return "org.omg.CORBA.ByteHolder";
	}
	
	public String generateCorbaConverterType()
	{
		return "";
	}
}
