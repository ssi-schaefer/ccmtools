package ccmtools.JavaClientLib.metamodel;

public class ByteType
	implements Type
{
	public String generateJavaMapping(PassingDirection direction)
	{
		if (direction == PassingDirection.IN
			|| direction == PassingDirection.RESULT)
		{
			return "byte";
		}
		else // INOUT, OUT
		{
			return "org.omg.CORBA.ByteHolder";
		}
	}
}
