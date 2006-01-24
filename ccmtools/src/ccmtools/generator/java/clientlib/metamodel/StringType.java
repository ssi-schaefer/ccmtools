package ccmtools.generator.java.clientlib.metamodel;

public class StringType
	implements Type
{
	public String generateJavaMapping()
	{
		return "String";
	}
	
	public String generateJavaMapping(PassingDirection direction)
	{
		if(direction == PassingDirection.IN
			|| direction == PassingDirection.RESULT)
		{
			return generateJavaMapping();
		}
		else
		{
			return "org.omg.CORBA.StringHolder";
		}	
	}
}
