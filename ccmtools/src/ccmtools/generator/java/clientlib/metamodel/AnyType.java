package ccmtools.generator.java.clientlib.metamodel;

public class AnyType
	implements Type
{
	public String generateJavaMapping()
	{
		return "org.omg.CORBA.Any";
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
			return "org.omg.CORBA.AnyHolder";
		}
	}
}
