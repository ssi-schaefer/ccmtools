package ccmtools.generator.idl.metamodel;

import java.util.ArrayList;
import java.util.List;

public class ArrayDef
	extends ModelElement
	implements Type
{
	/*************************************************************************
	 * IDL Model Implementation
	 *************************************************************************/
	
	/** Type which is the same for all elements staored in an array. */
	private Type elementType;

	/** Stores the bound of every array dimension */
	private List<Integer> bounds = new ArrayList<Integer>();
	
	
	public Type getElementType()
	{
		return elementType;
	}

	public void setElementType(Type type)
	{
		this.elementType = type;
	}
	
	public List<Integer> getBounds()
	{
		return bounds;
	}

	
	/*************************************************************************
	 * Type Interface Implementation
	 *************************************************************************/
	
	public String generateIdlMapping()
	{
		StringBuilder code = new StringBuilder();
		for(Integer bound: getBounds())
		{
			code.append("[").append(bound.longValue()).append("]");
		}		
		return code.toString();
	}
		
	public String generateIncludePath()
	{
		return getElementType().generateIncludePath();
	}
	
    
    /*************************************************************************
     * IDL2 Generator Methods Implementation
     *************************************************************************/
    
    public String generateIdl2()
    {
        return generateIdl3();
    }
    
    public String generateIdl2IncludePath()
    {
        return getElementType().generateIdl2IncludePath();
    }
}
