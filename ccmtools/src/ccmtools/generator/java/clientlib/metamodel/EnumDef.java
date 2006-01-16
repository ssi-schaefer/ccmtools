package ccmtools.generator.java.clientlib.metamodel;

import java.util.List;

public class EnumDef
	extends ModelElement
	implements Type
{
	private List members;
	
	public EnumDef(String identifier, List namespace)
	{
		super(identifier, namespace);
	}

	
	public List getMembers()
	{
		return members;
	}

	public String generateJavaMapping(PassingDirection direction)
	{
		if(direction == PassingDirection.IN
			|| direction == PassingDirection.RESULT)
		{
			return getIdentifier();
		}
		else
		{
			return "org.omg.CORBA." + getIdentifier() + "Holder";
		}	
	}
}
