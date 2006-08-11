package ccmtools.generator.idl.metamodel;


public abstract class TypeImpl
	implements Type
{	
	public String generateIdlConstant(Object value)
	{
		return value.toString();
	}
	
	public String generateIncludePath()
	{
		return ""; // primitive typed don't need include statements
	}	
    
    public String generateIdl2IncludePath()
    {
        return ""; // primitive typed don't need include statements
    }   
}
