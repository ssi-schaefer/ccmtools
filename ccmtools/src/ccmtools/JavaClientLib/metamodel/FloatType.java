package ccmtools.JavaClientLib.metamodel;

public class FloatType
	implements Type
{
	public String generateJavaMapping(PassingDirection direction)
	{
		if (direction == PassingDirection.IN
			|| direction == PassingDirection.RESULT)
		{
			return "float";
		}
		else // INOUT, OUT
		{
			return "org.omg.CORBA.FloatHolder";
		}
	}
}
