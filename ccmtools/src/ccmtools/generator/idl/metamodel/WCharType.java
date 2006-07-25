package ccmtools.generator.idl.metamodel;



public class WCharType
	extends TypeImpl
{
	/*************************************************************************
	 * Type Interface Implementation
	 *************************************************************************/
	
	public String generateIdlMapping()
	{
		return "wchar";
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
