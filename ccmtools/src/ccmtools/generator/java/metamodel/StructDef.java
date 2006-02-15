package ccmtools.generator.java.metamodel;

import java.util.List;

public class StructDef
	extends ModelElement
	implements Type
{
	private List members;
		
	public StructDef(String identifier, List namespace)
	{
		super(identifier, namespace);
	}
	
	
	public List getMembers()
	{
		return members;
	}

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
	
	public String generateJavaDefaultReturnValue()
	{
		return "null";		
	}
}
