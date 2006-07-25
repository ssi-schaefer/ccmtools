package ccmtools.generator.idl.metamodel;



public class ObjectType
	extends TypeImpl
{
	/*************************************************************************
	 * Type Interface Implementation
	 *************************************************************************/
	
	public String generateIdlMapping()
	{
		return "Object";
	}
	
	public String generateIdlConstant(Object value)
	{
		return ""; // not allowed as a constant
	}
	
//	public String generateIncludePath()
//	{
//		return ""; // primitive typed don't need include statements
//	}
}
