package ccmtools.generator.java.clientlib.metamodel;

public class IntegerType
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
			return "int";
		}
		else // INOUT, OUT
		{
			return "org.omg.CORBA.IntHolder";
		}
	}
}
