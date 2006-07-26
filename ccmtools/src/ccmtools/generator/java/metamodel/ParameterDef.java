package ccmtools.generator.java.metamodel;

import java.util.Set;

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

	
	public Set<String> getJavaImportStatements()
	{
		Set<String> importStatements = getType().getJavaImportStatements();			
		if(getDirection() == PassingDirection.INOUT
				|| getDirection() == PassingDirection.OUT)
		{
			importStatements.add("ccm.local.Holder");
		}
		return importStatements;
	}
	
	
	/*************************************************************************
	 * Local Interface Generator Methods
	 * 
	 *************************************************************************/
	
	public String generateParameter()
	{
		StringBuffer code = new StringBuffer();
		code.append(getType().generateJavaMapping(getDirection()));
		code.append(" ").append(getIdentifier());
		return code.toString();
	}


	
	/*************************************************************************
	 * CORBA Component Generator Methods
	 * 
	 *************************************************************************/
	
	public String generateCorbaParameter()
	{
		StringBuffer code = new StringBuffer();
		code.append(getType().generateCorbaMapping(getDirection()));
		code.append(" ").append(getIdentifier());
		return code.toString();
	}
	
	
	public String generateInConverterToCorba()
	{
		if (direction == PassingDirection.IN)
		{
			return getType().generateCorbaMapping() + " " + getIdentifier() + "Remote = " + 
				getType().generateCorbaConverterType() + "(" + getIdentifier() + ");";
		}
		else if(direction == PassingDirection.INOUT)
		{
			return  getType().generateCorbaHolderType() + " " + 
				getIdentifier() + "Remote = " + NL + 
				TAB3 + "new " + getType().generateCorbaHolderType() + 
				"(" + getType().generateCorbaConverterType() + "(" + 
				getIdentifier() + ".getValue()));";			
		}
		else if(direction == PassingDirection.OUT)
		{
			return getType().generateCorbaHolderType() + " " +
				getIdentifier() + "Remote = " + NL + 
				TAB3 + "new " + getType().generateCorbaHolderType() + "();"; 
		}
		else // PassingDirection.RESULT
		{
			return "";
		}
	}	
	
	public String generateInConverterFromCorba()
	{
		if (direction == PassingDirection.IN)
		{
			return getType().generateJavaMapping() + " " + getIdentifier() + "Local = " + 
				getType().generateCorbaConverterType() + "(" + getIdentifier() + ");";
		}
		else if(direction == PassingDirection.INOUT)
		{
			return  getType().generateJavaHolderType() + " " + 
				getIdentifier() + "Local = new " + getType().generateJavaHolderType() + 
				"(" + getType().generateCorbaConverterType() + "(" + 
				getIdentifier() + ".value));";			
		}
		else if(direction == PassingDirection.OUT)
		{
			return getType().generateJavaHolderType() + " " +
				getIdentifier() + "Local = new " + getType().generateJavaHolderType() + "();"; 
		}
		else // PassingDirection.RESULT
		{
			return "";
		}
	}	
	
	
	public String generateOutConverterToCorba()
	{
		if(direction == PassingDirection.IN)
		{
			return "";
		}
		else if(direction == PassingDirection.INOUT || direction == PassingDirection.OUT)
		{
			return getIdentifier() + ".value = " + 
					getType().generateCorbaConverterType() + "(" + getIdentifier() + "Local.getValue());";
		}
		else // PassingDirection.RESULT
		{
			return "";
		}
	}
	
	public String generateOutConverterFromCorba()
	{
		if(direction == PassingDirection.IN)
		{
			return "";
		}
		else if(direction == PassingDirection.INOUT || direction == PassingDirection.OUT)
		{
			return getIdentifier() + ".setValue(" + 
					getType().generateCorbaConverterType() + "(" + getIdentifier() + "Remote.value));";
		}
		else // PassingDirection.RESULT
		{
			return "";
		}
	}
}


