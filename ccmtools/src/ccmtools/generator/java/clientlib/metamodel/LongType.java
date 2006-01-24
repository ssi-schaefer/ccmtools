package ccmtools.generator.java.clientlib.metamodel;

public class LongType
	implements Type
{
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
}
