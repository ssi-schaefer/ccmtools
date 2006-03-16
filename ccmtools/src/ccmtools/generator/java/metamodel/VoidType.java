package ccmtools.generator.java.metamodel;

public class VoidType
	implements Type
{
	/*************************************************************************
	 * Local Interface Generator Methods
	 * 
	 *************************************************************************/
	
	public String generateJavaConstant(Object value)
	{
		return "";
	}
	
	public String generateJavaMapping()
	{
		return "void";
	}
	
	public String generateJavaMapping(PassingDirection direction)
	{
		if(direction == PassingDirection.RESULT)
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
		return "Void";
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
		return "";		
	}
	
	
	
	/*************************************************************************
	 * Client Library Generator Methods
	 * 
	 *************************************************************************/

	public String generateCorbaMapping()
	{
		return "void";
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
		return "";
	}	
	
	public String generateCorbaConverterType()
	{
		return "";
	}
}
