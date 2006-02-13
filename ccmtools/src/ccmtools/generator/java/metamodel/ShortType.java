package ccmtools.generator.java.metamodel;

public class ShortType
	implements Type
{
	public String generateJavaConstant(Object value)
	{
		return value.toString();
	}
	
	public String generateJavaMapping()
	{
		return "short";
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
			return "org.omg.CORBA.ShortHolder";
		}
	}
	
	public String generateJavaDefaultReturnValue()
	{
		return "0";		
	}
}
