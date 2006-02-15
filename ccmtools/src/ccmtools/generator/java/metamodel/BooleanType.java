package ccmtools.generator.java.metamodel;

public class BooleanType
	implements Type
{
	/**
	 * Java Local Interface Generator
	 * 
	 */
	
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
			return "org.omg.CORBA.BooleanHolder";
		}
	}

	
	
	/*************************************************************************
	 * Implementation Generator Methods
	 * 
	 *************************************************************************/
		
	public String generateJavaDefaultReturnValue()
	{
		return "false";		
	}
}
