package ccmtools.JavaClientLib.metamodel;

public class BooleanType
	implements Type
{
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
