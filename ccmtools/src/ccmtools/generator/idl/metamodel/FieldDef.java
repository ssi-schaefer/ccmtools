package ccmtools.generator.idl.metamodel;


public class FieldDef
	extends ModelElement
{
	private Type type;
		
	public FieldDef()
	{
		super();
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
		return TAB + getType().generateIdlMapping() + " " + getIdentifier() + ";" + NL; 
	}		
}
