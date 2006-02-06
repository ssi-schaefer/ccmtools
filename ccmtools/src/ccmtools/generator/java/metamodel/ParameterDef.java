package ccmtools.generator.java.metamodel;

public class ParameterDef
	extends ModelElement
{
	private PassingDirection direction;
	private Type type;
	
	
	public ParameterDef(String identifier, PassingDirection direction, Type type)
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
		code.append(getType().generateJavaMapping(getDirection()));
		code.append(" ").append(getIdentifier());
		return code.toString();
	}
}
