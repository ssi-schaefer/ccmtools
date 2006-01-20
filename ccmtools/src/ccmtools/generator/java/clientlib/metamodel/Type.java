package ccmtools.generator.java.clientlib.metamodel;

public interface Type
{
	String generateJavaMapping();
	
	String generateJavaMapping(PassingDirection direction);
}
