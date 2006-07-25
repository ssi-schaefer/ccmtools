package ccmtools.generator.idl.metamodel;


public class ParameterDef
	extends ModelElement
{
	/*************************************************************************
	 * IDL Model Implementation
	 *************************************************************************/
	
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
	 * IDL3 Generator Methods Implementation
	 *************************************************************************/

	public String generateIdl3()
	{
		return getDirection() + " " + getType().generateIdlMapping() + " " + getIdentifier(); 
	}
		
	public String generateIncludePath()
	{
		return getType().generateIncludePath();
	}	
}


