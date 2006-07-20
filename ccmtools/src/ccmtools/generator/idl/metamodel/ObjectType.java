package ccmtools.generator.idl.metamodel;



public class ObjectType
	implements Type
{
	
	/*************************************************************************
	 * IDL3 generator methods
	 *************************************************************************/
	
	public String generateIdlMapping()
	{
		return "Object";
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
