package ccmtools.generator.java.clientlib.metamodel;

import java.util.List;

public class SequenceDef
	extends ModelElement
	implements Type
{
	private long bound;
		
	
	public SequenceDef(String identifier, List namespace)
	{
		super(identifier, namespace);
	}

	public long getBound()
	{
		return bound;
	}

	public void setBound(long bound)
	{
		this.bound = bound;
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
