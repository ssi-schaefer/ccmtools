package ccmtools.generator.java.clientlib.metamodel;

public class AnyType
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
			return "org.omg.CORBA.Any";
		}
		else // INOUT, OUT
		{
			return "org.omg.CORBA.AnyHolder";
		}
	}
}
