package ccmtools.generator.idl.metamodel;



public class AnyType
	extends TypeImpl
{	
	
	/*************************************************************************
	 * Type Interface Implementation
	 *************************************************************************/
	
	public String generateIdlMapping()
	{
		return "any";
	}
	
	public String generateIdlConstant(Object value)
	{
		return ""; // not allowed as a constant
	}
}
