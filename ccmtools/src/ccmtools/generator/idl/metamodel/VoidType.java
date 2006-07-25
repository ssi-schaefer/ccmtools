package ccmtools.generator.idl.metamodel;



public class VoidType
	extends TypeImpl
{
	/*************************************************************************
	 * Type Interface Implementation
	 *************************************************************************/
	
	public String generateIdlMapping()
	{
		return "void";
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
