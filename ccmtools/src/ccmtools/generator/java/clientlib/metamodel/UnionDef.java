package ccmtools.generator.java.clientlib.metamodel;

import java.util.List;

public class UnionDef
	extends ModelElement
	implements Type
{
	private List members;
	
	public UnionDef(String identifier, List namespace)
	{
		super(identifier, namespace);
	}

	
	public List getMembers()
	{
		return members;
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
