package ccmtools.generator.idl.metamodel;



public class CharType
	extends TypeImpl
{
	
	/*************************************************************************
	 * Type Interface Implementation
	 *************************************************************************/
	
	public String generateIdlMapping()
	{
		return "char";
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
