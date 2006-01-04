package ccmtools.JavaClientLib.metamodel;

public class VoidType
	implements Type
{
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
