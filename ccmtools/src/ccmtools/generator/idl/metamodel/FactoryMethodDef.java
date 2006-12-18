package ccmtools.generator.idl.metamodel;



public class FactoryMethodDef
	extends OperationDef
{
	/*************************************************************************
	 * IDL Model Implementation
	 *************************************************************************/
		
	public FactoryMethodDef(String identifier)
	{
        super(identifier);
	}
	
		
	/*************************************************************************
	 * IDL3 Generator Methods Implementation
	 *************************************************************************/
	
	public String generateIdl3()
	{
		StringBuilder code = new StringBuilder();
		code.append("factory ").append(getIdentifier());
		code.append("(").append(generateParameterList()).append(")");
		if(getExceptions().size() > 0)
		{
			code.append(NL).append(TAB);
			code.append(" raises(").append(generateExceptionList()).append(")");
		}
		code.append(";").append(NL);
		return code.toString(); 
	}

    
    /*************************************************************************
     * IDL2 Generator Methods Implementation
     *************************************************************************/
	
    public String generateIdl2()
    {
        return generateIdl3();
    }
}
