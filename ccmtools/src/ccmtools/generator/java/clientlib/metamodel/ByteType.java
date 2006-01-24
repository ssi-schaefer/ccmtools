package ccmtools.generator.java.clientlib.metamodel;

public class ByteType
	implements Type
{
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
}
