package ccmtools.generator.java.metamodel;

public class ByteType
	implements Type
{
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
			return "org.omg.CORBA.ByteHolder";
		}
	}
	
	public String generateJavaDefaultReturnValue()
	{
		return "0";		
	}
}
