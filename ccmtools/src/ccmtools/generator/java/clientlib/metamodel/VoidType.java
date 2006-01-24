package ccmtools.generator.java.clientlib.metamodel;

public class VoidType
	implements Type
{
	public String generateJavaConstant(Object value)
	{
		return "";
	}
	
	public String generateJavaMapping()
	{
		return "void";
	}
	
	public String generateJavaMapping(PassingDirection direction)
	{
		if(direction == PassingDirection.RESULT)
		{
			return generateJavaMapping();
		}
		else
		{
			return "";
		}	
	}
}
