package ccmtools.generator.java.metamodel;

public interface Type
{
	String generateJavaConstant(Object value);
	
	String generateJavaMapping();	
	String generateJavaMapping(PassingDirection direction);
	
	String generateJavaDefaultReturnValue();
}
