package ccmtools.generator.java.metamodel;

public class ByteType
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
			return "ccm.local.ByteHolder";
		}
	}
	
	
	
	/*************************************************************************
	 * Application Generator Methods
	 * 
	 *************************************************************************/
		
	public String generateJavaDefaultReturnValue()
	{
		return "0";		
	}
}
