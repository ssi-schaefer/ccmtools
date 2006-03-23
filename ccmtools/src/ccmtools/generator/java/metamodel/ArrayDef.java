package ccmtools.generator.java.metamodel;

import java.util.List;

public class ArrayDef
	extends ModelElement
	implements Type
{
	/** Array type which is the same for all elements staored in an array. */
	private Type type;

	
	public ArrayDef(String identifier, List namespace)
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



	/*************************************************************************
	 * Local Interface Generator Methods
	 * 
	 *************************************************************************/
	
	public String generateJavaConstant(Object value)
	{
		return value.toString();
	}
		
	public String generateJavaMapping()
	{
		return getType().generateJavaMapping() + "[]";
	}
	
	public String generateJavaMapping(PassingDirection direction)
	{
		if(direction == PassingDirection.IN
			|| direction == PassingDirection.RESULT)
		{
			return generateJavaMapping();
		}
		else
		{
			return generateJavaHolderType();
		}	
	}
	
	public String generateJavaMappingObject()
	{
		return generateJavaMapping();
	}
	
	public String generateJavaHolderType()
	{
		return "ccm.local.Holder<" + generateJavaMappingObject() + ">";
	}
		
	
	/*************************************************************************
	 * Application Generator Methods
	 * 
	 *************************************************************************/
	
	public String generateJavaDefaultReturnValue()
	{
		return "null";		
	}
	
	
	/*************************************************************************
	 * Client Library Generator Methods
	 * 
	 *************************************************************************/

	public String generateCorbaMapping()
	{
		return getType().generateCorbaMapping() + "[]";
	}
	
	public String generateCorbaMapping(PassingDirection direction)
	{
		if (direction == PassingDirection.IN
			|| direction == PassingDirection.RESULT)
		{
			return generateCorbaMapping();
		}
		else // INOUT, OUT
		{
			return generateCorbaHolderType();
		}
	}		

	public String generateCorbaHolderType()
	{
		return generateAbsoluteIdlName() + "Holder";
	}	
	
	public String generateCorbaConverterType()
	{
		return "";
	}
}
