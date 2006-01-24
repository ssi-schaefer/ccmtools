package ccmtools.generator.java.clientlib.metamodel;

public interface Type
{
	String generateJavaConstant(Object value);
	
	String generateJavaMapping();	
	String generateJavaMapping(PassingDirection direction);
}
