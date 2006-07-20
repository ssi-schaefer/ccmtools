package ccmtools.generator.idl.metamodel;

import java.util.ArrayList;
import java.util.List;

public class ArrayDef
	extends ModelElement
	implements Type
{
	/** Type which is the same for all elements staored in an array. */
	private Type elementType;

	/** Stores the bound of every array dimension */
	private List<Long> bounds = new ArrayList<Long>();
	
	
	public Type getElementType()
	{
		return elementType;
	}

	public void setElementType(Type type)
	{
		this.elementType = type;
	}

	
	public List<Long> getBounds()
	{
		return bounds;
	}


	
	/*************************************************************************
	 * IDL3 generator methods
	 *************************************************************************/
	
	public String generateIdlMapping()
	{
		StringBuilder code = new StringBuilder();
		for(Long bound: getBounds())
		{
			code.append("[").append(bound.longValue()).append("]");
		}		
		return code.toString();
	}
	
	public String generateIdlConstant(Object value)
	{
		return ""; // not allowed
	}	
	
	public String generateIncludePath()
	{
		return getElementType().generateIncludePath();
	}
}
