package ccmtools.generator.java.clientlib.metamodel;

public class CharType
	implements Type
{
	public String generateJavaMapping()
	{
		return "char";
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
			return "org.omg.CORBA.CharHolder";
		}
	}
}
