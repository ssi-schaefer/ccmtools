package ccmtools.generator.idl.metamodel;



public class UnsignedLongType
	extends TypeImpl
{
	/*************************************************************************
	 * Type Interface Implementation
	 *************************************************************************/
	
	public String generateIdlMapping()
	{
		return "unsigned long";
	}
	
//	public String generateIdlConstant(Object value)
//	{
//		return value.toString();
//	}
//	
//	public String generateIncludePath()
//	{
//		return ""; // primitive typed don't need include statements
//	}
}
