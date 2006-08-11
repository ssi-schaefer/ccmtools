package ccmtools.generator.idl.metamodel;

import java.util.List;

import ccmtools.generator.idl.templates.ConstantDefTemplate;

public class ConstantDef
	extends ModelElement
{
	/*************************************************************************
	 * IDL Model Implementation
	 *************************************************************************/
	
	private Type type;
	private Object constValue;
		
	public ConstantDef(String identifier, List<String> namespace)
	{
		super(identifier, namespace);
	}
			
	public Type getType()
	{
		return type;
	}

	public void setType(Type type)
	{
		this.type = type;
	}
	
	
	public Object getConstValue()
	{
		return constValue;
	}	
	
	public void setConstValue(Object value)
	{
		this.constValue = value;
	}
	
	
	/*************************************************************************
	 * IDL3 Generator Methods Implementation
	 *************************************************************************/
	
	public String generateIdl3()
	{
		return new ConstantDefTemplate().generate(this); 
	}
	
	public String generateConstantValue()
	{
		return getType().generateIdlConstant(getConstValue());
	}
	
	public String generateInterfaceConstant()
	{
		return "const " + getType().generateIdlMapping() + " " + getIdentifier() + " = " 
			+ generateConstantValue() + ";" + NL;
	}
    
    
    /*************************************************************************
     * IDL2 Generator Methods Implementation
     *************************************************************************/
	
}
