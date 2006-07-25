package ccmtools.generator.idl.metamodel;


public class ParameterDef
	extends ModelElement
{
	private PassingDirection direction;
	private Type type;
	
	
	public ParameterDef(String identifier)
	{
		setIdentifier(identifier);
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
	
	/*************************************************************************
	 * IDL3 generator methods
	 *************************************************************************/
	
	public String generateIncludePath()
	{
		return getType().generateIncludePath();
	}
	
	public String generateIdl3Code()
	{
		return getDirection() + " " + getType().generateIdlMapping() + " " + getIdentifier(); 
	}
	
}


