package ccmtools.generator.idl.metamodel;



public class BooleanType
	implements Type
{
	
	/*************************************************************************
	 * IDL3 generator methods
	 *************************************************************************/
	
	public String generateIdlMapping()
	{
		return "boolean";
	}
	
	public String generateIdlConstant(Object value)
	{
		return value.toString().toUpperCase();
	}
	
	public String generateIncludePath()
	{
		return ""; // primitive typed don't need include statements
	}
}
