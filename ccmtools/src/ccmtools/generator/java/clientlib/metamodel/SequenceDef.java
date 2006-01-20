package ccmtools.generator.java.clientlib.metamodel;

import java.util.List;

public class SequenceDef
	extends ModelElement
	implements Type
{
	private long bound;
	private Type elementType;
	
	
	public SequenceDef(String identifier, List namespace, Type elementType)
	{
		super(identifier, namespace);
		setElementType(elementType);
	}

	public long getBound()
	{
		return bound;
	}

	public void setBound(long bound)
	{
		this.bound = bound;
	}
	

	public Type getElementType()
	{
		return elementType;
	}

	public void setElementType(Type elementType)
	{
		this.elementType = elementType;
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
			return getElementType().generateJavaMapping(direction) + "[]";
		}
		else
		{
			return getAbsoluteIdlName() + "Holder";
		}	
	}
}
