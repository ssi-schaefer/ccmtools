package ccmtools.JavaClientLib.metamodel;

public class ParameterDefinition
	extends ModelElement
{
	private PassingDirection direction;
	private Type type;
	
	
	public ParameterDefinition(String identifier, PassingDirection direction, Type type)
	{
		setIdentifier(identifier);
		setDirection(direction);
		setType(type);
	}
	
	public PassingDirection getDirection()
	{
		return direction;
	}

	public void setDirection(PassingDirection direction)
	{
		this.direction = direction;
	}
	
	
	public Type getType()
	{
		return type;
	}

	public void setType(Type type)
	{
		this.type = type;
	}

	
	// Generator methods ------------------------------------------------------
	
	public String generateParameter()
	{
		StringBuffer code = new StringBuffer();
		if(getType() instanceof LongType) 
		{
			LongType t = (LongType)getType();
			code.append(t.generateJavaMapping(getDirection()));
		}
		else if(getType() instanceof StringType)
		{
			StringType t = (StringType)getType();
			code.append(t.generateJavaMapping(getDirection()));
		}
		// TODO...
		else 
		{
			throw new RuntimeException("generateParameter() - Unhandled type!");
		}
		code.append(" ").append(getIdentifier());
		return code.toString();
	}
}
