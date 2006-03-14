package ccmtools.generator.java.metamodel;

public class AnyType
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
		return "org.omg.CORBA.Any";
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
	
	public String generateJavaHolderType()
	{
		return "ccm.local.Holder<org.omg.CORBA.Any>";
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
		return "";
	}
	
	public String generateCorbaHolderType()
	{
		return "org.omg.CORBA.AnyHolder";
	}	
}
