package ccmtools.generator.java.clientlib.metamodel;

import java.util.List;

public class TypedefDef
	extends ModelElement
	implements Type
{
	public TypedefDef(String identifier, List namespace)
	{
		super(identifier, namespace);
	}

	public String generateJavaMapping()
	{
		return generateJavaMapping(PassingDirection.IN);
	}
	
	public String generateJavaMapping(PassingDirection direction)
	{
		if(direction == PassingDirection.IN
			|| direction == PassingDirection.RESULT)
		{
			return getAbsoluteIdlName();
		}
		else
		{
			return getAbsoluteIdlName() + "Holder";
		}	
	}
}
