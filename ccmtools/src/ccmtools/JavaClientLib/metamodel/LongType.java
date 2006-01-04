package ccmtools.JavaClientLib.metamodel;

public class LongType
	implements Type
{
	public String generateJavaMapping(PassingDirection direction)
	{
		if (direction == PassingDirection.IN
			|| direction == PassingDirection.RESULT)
		{
			return "long";
		}
		else // INOUT, OUT
		{
			return "org.omg.CORBA.LongHolder";
		}
	}
}
