package ccmtools.generator.java.clientlib.metamodel;

public class DoubleType
	implements Type
{
	public String generateJavaMapping(PassingDirection direction)
	{
		if (direction == PassingDirection.IN
			|| direction == PassingDirection.RESULT)
		{
			return "double";
		}
		else // INOUT, OUT
		{
			return "org.omg.CORBA.DoubleHolder";
		}
	}
}
