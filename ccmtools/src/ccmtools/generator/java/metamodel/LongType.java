package ccmtools.generator.java.metamodel;

public class LongType
	implements Type
{
	public String generateJavaConstant(Object value)
	{
		return value.toString();
	}
	
	public String generateJavaMapping()
	{
		return "long";
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
			return "org.omg.CORBA.LongHolder";
		}
	}
	
	public String generateJavaDefaultReturnValue()
	{
		return "0";		
	}
}
