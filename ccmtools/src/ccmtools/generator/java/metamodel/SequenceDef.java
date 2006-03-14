package ccmtools.generator.java.metamodel;

import java.util.List;

public class SequenceDef
	extends ModelElement
	implements Type
{
	private long bound;
	private Type elementType;
	
	
	public SequenceDef(String identifier, List namespace, Type elementType)
	{
		super(identifier, namespace);
		setElementType(elementType);
	}

	public long getBound()
	{
		return bound;
	}

	public void setBound(long bound)
	{
		this.bound = bound;
	}
	

	public Type getElementType()
	{
		return elementType;
	}

	public void setElementType(Type elementType)
	{
		this.elementType = elementType;
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
		return getElementType().generateJavaMapping() + "[]";
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
	
	public String generateJavaHolderType()
	{
		return "ccm.local.Holder<" + generateJavaMapping() + ">";
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
		return getElementType().generateJavaMapping() + "[]";
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
