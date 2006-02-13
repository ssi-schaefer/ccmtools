package ccmtools.generator.java.metamodel;

public class IntegerType
	implements Type
{
	public String generateJavaConstant(Object value)
	{
		return value.toString();
	}
	
	public String generateJavaMapping()
	{
		return "int";
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
			return "org.omg.CORBA.IntHolder";
		}
	}
	
	public String generateJavaDefaultReturnValue()
	{
		return "0";		
	}
}
