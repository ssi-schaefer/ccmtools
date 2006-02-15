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
			return "org.omg.CORBA.CharHolder";
		}
	}
	
	
	
	/*************************************************************************
	 * Implementation Generator Methods
	 * 
	 *************************************************************************/
	
	public String generateJavaDefaultReturnValue()
	{
		return "' '";		
	}
}
