package ccmtools.generator.idl.metamodel;



public class UnsignedLongLongType
	implements Type
{
	
	/*************************************************************************
	 * IDL3 generator methods
	 *************************************************************************/
	
	public String generateIdlMapping()
	{
		return "unsigned long long";
	}
	
	public String generateIdlConstant(Object value)
	{
		return value.toString();
	}
	
	public String generateIncludePath()
	{
		return ""; // primitive typed don't need include statements
	}
}
