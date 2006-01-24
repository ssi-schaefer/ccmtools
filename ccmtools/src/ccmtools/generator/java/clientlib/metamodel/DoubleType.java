package ccmtools.generator.java.clientlib.metamodel;

public class DoubleType
	implements Type
{
	public String generateJavaMapping()
	{
		return "double";
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
			return "org.omg.CORBA.DoubleHolder";
		}
	}
}
