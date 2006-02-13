package ccmtools.generator.java.metamodel;

public class FloatType
	implements Type
{
	public String generateJavaConstant(Object value)
	{
		return value.toString();
	}
	
	public String generateJavaMapping()
	{
		return "float";
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
			return "org.omg.CORBA.FloatHolder";
		}
	}
	
	public String generateJavaDefaultReturnValue()
	{
		return "0.0";		
	}
}
