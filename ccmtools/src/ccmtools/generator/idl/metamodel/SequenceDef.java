package ccmtools.generator.idl.metamodel;



public class SequenceDef
	extends ModelElement
	implements Type
{
	/*************************************************************************
	 * IDL Model Implementation
	 *************************************************************************/
	
	/** Type which is the same for all elements stored in a sequence. */
	private Type elementType;
	
	/** Stores the size of a bounded sequence (or null) */
	private Long bound;
	
	
	public Long getBound()
	{
		return bound;
	}

	public void setBound(Long bound)
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
	 * Type Interface Implementation
	 *************************************************************************/
	
	public String generateIdlMapping()
	{
		StringBuilder code = new StringBuilder();

		code.append("sequence< ").append(getElementType().generateIdlMapping());
		if(getBound() != null)
		{
			code.append(",").append(getBound());
		}
		code.append(" >");
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
