package ccmtools.generator.java.clientlib.metamodel;

import java.util.List;

public class ArrayDef
	extends ModelElement
	implements Type
{
	public ArrayDef(String identifier, List namespace)
	{
		super(identifier, namespace);
	}

	public String generateJavaMapping()
	{
		return getAbsoluteIdlName();
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
			return getAbsoluteIdlName() + "Holder";
		}	
	}
}
