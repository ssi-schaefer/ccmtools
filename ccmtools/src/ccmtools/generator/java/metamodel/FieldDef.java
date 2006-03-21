package ccmtools.generator.java.metamodel;

import ccmtools.generator.java.templates.FieldDefAccessorsTemplate;


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
	 * Local Interface Generator Methods
	 * 
	 *************************************************************************/
	
	public String generateDeclaration()
	{
		return "private " + getType().generateJavaMapping() + " " + getIdentifier() +";";
	}
	
	public String generateAccessorName()
	{
		return Character.toUpperCase(getIdentifier().charAt(0)) + getIdentifier().substring(1);
	}
	
	public String generateGetterName()
	{
		if(getType() instanceof BooleanType)
		{
			return "is" + generateAccessorName();
		}
		else
		{			
			return "get" + generateAccessorName();
		}
	}
	
	public String generateSetterName()
	{
		return "set" + generateAccessorName();
	}
	
	public String generateAccessors()
	{
		return new FieldDefAccessorsTemplate().generate(this);
	}
	
	
	
	/*************************************************************************
	 * Application Generator Methods
	 * 
	 *************************************************************************/
	
	
	
	/*************************************************************************
	 * Client Library Generator Methods
	 * 
	 *************************************************************************/
	
}
