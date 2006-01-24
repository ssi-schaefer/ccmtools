package ccmtools.generator.java.clientlib.metamodel;

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
