package ccmtools.generator.java.metamodel;

import ccmtools.generator.java.templates.AttributeDefAdapterFromCorbaTemplate;
import ccmtools.generator.java.templates.AttributeDefAdapterLocalTemplate;
import ccmtools.generator.java.templates.AttributeDefAdapterToCorbaTemplate;
import ccmtools.generator.java.templates.AttributeDefApplicationImplementationTemplate;
import ccmtools.generator.java.templates.AttributeDefDeclarationTemplate;


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
	
	
	/*************************************************************************
	 * Local Interface Generator Methods
	 * 
	 *************************************************************************/
	
	public String generateDeclaration()
	{
		return new AttributeDefDeclarationTemplate().generate(this);
	}

	
	
	/*************************************************************************
	 * Local Component Generator
	 * 
	 *************************************************************************/
	
	public String generateAdapterLocal()
	{
		return new AttributeDefAdapterLocalTemplate().generate(this);
	}
	
	
	/*************************************************************************
	 * Application Generator Methods
	 * 
	 *************************************************************************/
	
	public String generateApplicationDeclaration()
	{
		return TAB + "private " + getType().generateJavaMapping() + " " + 
				getIdentifier() + "_;";		
	}
	
	public String generateApplicationImplementation()
	{
		return new AttributeDefApplicationImplementationTemplate().generate(this);
	}
	
	
	/*************************************************************************
	 * Client Library Generator
	 * 
	 *************************************************************************/
	
	public String generateAdapterFromCorba()
	{
		return new AttributeDefAdapterFromCorbaTemplate().generate(this);
	}
	
	public String generateAdapterToCorba()
	{
		return new AttributeDefAdapterToCorbaTemplate().generate(this);
	}
}
