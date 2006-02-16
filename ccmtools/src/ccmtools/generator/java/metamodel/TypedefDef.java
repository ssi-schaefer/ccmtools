package ccmtools.generator.java.metamodel;

import java.util.List;

public class TypedefDef
	extends ModelElement
	implements Type
{
	public TypedefDef(String identifier, List namespace)
	{
		super(identifier, namespace);
	}
	
	
	/*************************************************************************
	 * Local Interface Generator Methods
	 * 
	 *************************************************************************/

	public String generateJavaConstant(Object value)
	{
		return value.toString();
	}
	
	public String generateJavaMapping()
	{
		return generateAbsoluteIdlName();
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
			return generateAbsoluteIdlName() + "Holder";
		}	
	}
	
	
	
	/*************************************************************************
	 * Application Generator Methods
	 * 
	 *************************************************************************/
	
	public String generateJavaDefaultReturnValue()
	{
		return "null";		
	}
}
