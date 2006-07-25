package ccmtools.generator.idl.metamodel;


public class AttributeDef
	extends ModelElement
{
	private boolean isReadonly;
	private Type type;
	
	public AttributeDef(String identifier)
	{
		setIdentifier(identifier);
	}
		
	public boolean isReadonly()
	{
		return isReadonly;
	}

	public void setReadonly(boolean isReadonly)
	{
		this.isReadonly = isReadonly;
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
	
	public String generateIdl3Code()
	{
		StringBuilder code = new StringBuilder();
		if(isReadonly())
		{
			code.append("readonly ");
		}
		code.append("attribute ");
		code.append(getType().generateIdlMapping()).append(" ").append(getIdentifier());
		code.append(";").append(NL);
		return code.toString();
	}

}
