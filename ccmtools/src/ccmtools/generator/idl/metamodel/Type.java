package ccmtools.generator.idl.metamodel;



public interface Type
{
	/**
	 * Generate the IDL mapping for a particular type. 
	 */
	String generateIdlMapping();	

	/**
	 * Generate the IDL mapping for a particular constant. 
	 */
	String generateIdlConstant(Object value);
	
	
	String generateIncludePath();
    
    String generateIdl2IncludePath();
}
