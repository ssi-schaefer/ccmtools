package ccmtools.generator.idl.metamodel;


public class FieldDef
	extends ModelElement
{
	/*************************************************************************
	 * IDL Model Implementation
	 *************************************************************************/
	
	private Type type;
			
	public Type getType()
	{
		return type;
	}
	
	public void setType(Type type)
	{
		this.type = type;
	}

	
	/*************************************************************************
	 * IDL3 Generator Methods Implementation
	 *************************************************************************/
	
	public String generateIdl3()
	{
		return TAB + getType().generateIdlMapping() + " " + getIdentifier() + ";" + NL; 
	}		

    
    /*************************************************************************
     * IDL2 Generator Methods Implementation
     *************************************************************************/
   
    public String generateIdl2()
    {
        return generateIdl3();
    }
}
