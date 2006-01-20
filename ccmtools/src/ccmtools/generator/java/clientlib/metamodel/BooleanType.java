package ccmtools.generator.java.clientlib.metamodel;

public class BooleanType
	implements Type
{
	public String generateJavaMapping()
	{
		return generateJavaMapping(PassingDirection.IN);
	}
	
	public String generateJavaMapping(PassingDirection direction)
	{
		if (direction == PassingDirection.IN
			|| direction == PassingDirection.RESULT)
		{
			return "boolean";
		}
		else // INOUT, OUT
		{
			return "org.omg.CORBA.BooleanHolder";
		}
	}
}
