package ccmtools.JavaClientLib.metamodel;

public class LongType
	extends Type
{

	
	public String generateJavaMapping(PassingDirection direction)
	{
		if (direction == PassingDirection.IN
			|| direction == PassingDirection.RESULT)
		{
			return "int";
		}
		else // INOUT, OUT
		{
			return "org.omg.CORBA.IntHolder";
		}
	}
}
