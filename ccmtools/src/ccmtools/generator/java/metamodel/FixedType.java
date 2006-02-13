package ccmtools.generator.java.metamodel;

public class FixedType
	implements Type
{
	public String generateJavaConstant(Object value)
	{
		return value.toString();
	}
	
	public String generateJavaMapping()
	{
		return "java.math.BigDecimal";
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
			return "org.omg.CORBA.FixedHolder";
		}
	}
	
	public String generateJavaDefaultReturnValue()
	{
		return "null";		
	}
}
