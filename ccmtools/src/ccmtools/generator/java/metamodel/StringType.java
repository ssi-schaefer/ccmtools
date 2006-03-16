package ccmtools.generator.java.metamodel;

public class StringType
	implements Type
{
	/*************************************************************************
	 * Local Interface Generator Methods
	 * 
	 *************************************************************************/
	
	public String generateJavaConstant(Object value)
	{
		return "\"" + value.toString() + "\"";
	}

	public String generateJavaMapping()
	{
		return "String";
	}
	
	public String generateJavaMapping(PassingDirection direction)
	{
		if(direction == PassingDirection.IN
			|| direction == PassingDirection.RESULT)
		{
			return generateJavaMapping();
		}
		else
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
		return "ccm.local.Holder<" + generateJavaMappingObject() + ">";
	}
	
	
	
	/*************************************************************************
	 * Application Generator Methods
	 * 
	 *************************************************************************/
	
	public String generateJavaDefaultReturnValue()
	{
		return "\"\"";		
	}
	
	
	
	/*************************************************************************
	 * Client Library Generator Methods
	 * 
	 *************************************************************************/

	public String generateCorbaMapping()
	{
		return "String";
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
		return "";
	}
	
	public String generateCorbaHolderType()
	{
		return "org.omg.CORBA.StringHolder";
	}
}
