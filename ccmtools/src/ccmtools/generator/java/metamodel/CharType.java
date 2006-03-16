package ccmtools.generator.java.metamodel;

public class CharType
	implements Type
{
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
		return "char";
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
		return "Character";
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
		return "' '";		
	}
	
	
	/*************************************************************************
	 * Client Library Generator Methods
	 * 
	 *************************************************************************/
	
	public String generateCorbaMapping()
	{
		return "char";
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
		return "org.omg.CORBA.CharHolder";
	}
	
	public String generateCorbaConverterType()
	{
		return "";
	}	
}
