package ccmtools.generator.java.clientlib.metamodel;

public class ShortType
	implements Type
{
	public String generateJavaMapping(PassingDirection direction)
	{
		if (direction == PassingDirection.IN
			|| direction == PassingDirection.RESULT)
		{
			return "short";
		}
		else // INOUT, OUT
		{
			return "org.omg.CORBA.ShortHolder";
		}
	}
}
