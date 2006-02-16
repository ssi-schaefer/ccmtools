package ccmtools.generator.java.metamodel;

public interface Type
{
	/**
	 * Generate a Java constant from the given value according to the
	 * particular type.
	 * 
	 * @param value Value that should be turned into a Java literal.
	 * 
	 * @return String containing the generate Java literal.
	 */
	String generateJavaConstant(Object value);
	
	
	/**
	 * Generate the Java mapping for a particular type. 
	 * 
	 * @return String containing the generated Java mapping.
	 */
	String generateJavaMapping();	
	
	
	/**
	 * Generate the Java mapping for a particular type according to
	 * the given call direction.
	 * 
	 * @param direction Call direction (IN, INOUT, OUT), if the type is 
	 * 		  used as a parameter or result.
	 * 
	 * @return String containing the generated Java mapping.
	 */
	String generateJavaMapping(PassingDirection direction);
	
	
	/**
	 * Generate the Java default initial value for the given type.
	 * This default value is, for example, used for return values of 
	 * dummy methods.
	 *
	 * @return String containing the generated Java default return value.
	 */
	String generateJavaDefaultReturnValue();
}
