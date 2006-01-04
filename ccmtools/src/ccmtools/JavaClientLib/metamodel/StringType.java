package ccmtools.JavaClientLib.metamodel;

public class StringType
	implements Type
{
	public String generateJavaMapping(PassingDirection direction)
	{
		if(direction == PassingDirection.IN
			|| direction == PassingDirection.RESULT)
		{
			return "String";
		}
		else
		{
			return "org.omg.CORBA.StringHolder";
		}	
	}
}
