package ccmtools.JavaClientLib.metamodel;

public class CharType
	implements Type
{
	public String generateJavaMapping(PassingDirection direction)
	{
		if (direction == PassingDirection.IN
			|| direction == PassingDirection.RESULT)
		{
			return "char";
		}
		else // INOUT, OUT
		{
			return "org.omg.CORBA.CharHolder";
		}
	}
}
