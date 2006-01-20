package ccmtools.generator.java.clientlib.metamodel;

import ccmtools.generator.java.clientlib.templates.AttributeAdapterFromCorbaTemplate;
import ccmtools.generator.java.clientlib.templates.AttributeAdapterToCorbaTemplate;
import ccmtools.generator.java.clientlib.templates.AttributeDeclarationTemplate;


public class AttributeDef
	extends ModelElement
{
	private boolean isReadonly;
	private Type type;
	
	public AttributeDef(String identifier, Type type, boolean mode)
	{
		setIdentifier(identifier);
		setType(type);	
		setReadonly(mode);
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
	
	// Generator methods ------------------------------------------------------
	
	public String generateAttributeDeclaration()
	{
		return new AttributeDeclarationTemplate().generate(this);
	}
	
	public String generateAttributeAdapterFromCorba()
	{
		return new AttributeAdapterFromCorbaTemplate().generate(this);
	}
	
	public String generateAttributeAdapterToCorba()
	{
		return new AttributeAdapterToCorbaTemplate().generate(this);
	}
}
