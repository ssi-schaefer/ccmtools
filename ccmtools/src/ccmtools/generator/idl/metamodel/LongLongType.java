package ccmtools.generator.idl.metamodel;



public class LongLongType
	extends TypeImpl
{
	/*************************************************************************
	 * Type Interface Implementation
	 *************************************************************************/
	
	public String generateIdlMapping()
	{
		return "long long";
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
