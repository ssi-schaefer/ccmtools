package ccmtools.generator.java.metamodel;

public class DoubleType
	implements Type
{
	public String generateJavaConstant(Object value)
	{
		return value.toString();
	}
		
	public String generateJavaMapping()
	{
		return "double";
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
			return "org.omg.CORBA.DoubleHolder";
		}
	}
	
	public String generateJavaDefaultReturnValue()
	{
		return "0.0";		
	}
}
