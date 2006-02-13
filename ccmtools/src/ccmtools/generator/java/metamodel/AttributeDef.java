package ccmtools.generator.java.metamodel;

import ccmtools.generator.java.templates.AttributeAdapterFromCorbaTemplate;
import ccmtools.generator.java.templates.AttributeAdapterToCorbaTemplate;
import ccmtools.generator.java.templates.AttributeDeclarationTemplate;


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
	
	
	/**
	 * Java Local Interface Generator
	 * 
	 */
	
	public String generateAttributeDeclaration()
	{
		return new AttributeDeclarationTemplate().generate(this);
	}

	
	
	/**
	 * Java Local Component Generator
	 * 
	 */
	
	public String generateAttributeAdapter()
	{
		//return new AttributeAdapterTemplate().generate(this);
		return "";
	}
	
	
	
	
	
	/**
	 * Java Client Library Generator
	 * 
	 */
	
	public String generateAttributeAdapterFromCorba()
	{
		return new AttributeAdapterFromCorbaTemplate().generate(this);
	}
	
	public String generateAttributeAdapterToCorba()
	{
		return new AttributeAdapterToCorbaTemplate().generate(this);
	}
}
