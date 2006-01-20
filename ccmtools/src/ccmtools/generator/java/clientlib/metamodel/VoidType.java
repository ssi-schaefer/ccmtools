package ccmtools.generator.java.clientlib.metamodel;

public class VoidType
	implements Type
{
	public String generateJavaMapping()
	{
		return generateJavaMapping(PassingDirection.RESULT);
	}
	
	public String generateJavaMapping(PassingDirection direction)
	{
		if(direction == PassingDirection.RESULT)
		{
			return "void";
		}
		else
		{
			return "";
		}	
	}
}
