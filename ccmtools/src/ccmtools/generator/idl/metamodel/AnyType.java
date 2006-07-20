package ccmtools.generator.idl.metamodel;



public class AnyType
	implements Type
{	
	
	/*************************************************************************
	 * IDL3 generator methods
	 *************************************************************************/
	
	public String generateIdlMapping()
	{
		return "any";
	}
	
	public String generateIdlConstant(Object value)
	{
		return ""; // not allowed as a constant
	}

	public String generateIncludePath()
	{
		return ""; // primitive typed don't need include statements
	}
}
