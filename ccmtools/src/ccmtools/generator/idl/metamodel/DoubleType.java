package ccmtools.generator.idl.metamodel;



public class DoubleType
	extends TypeImpl
{
	/*************************************************************************
	 * IDL Model Implementation
	 *************************************************************************/
	
	public String generateIdlMapping()
	{
		return "double";
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
