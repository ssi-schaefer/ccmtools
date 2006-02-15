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
			return "org.omg.CORBA.AnyHolder";
		}
	}
	
	
	/*************************************************************************
	 * Implementation Generator Methods
	 * 
	 *************************************************************************/
	
	public String generateJavaDefaultReturnValue()
	{
		return "null";		
	}
}
