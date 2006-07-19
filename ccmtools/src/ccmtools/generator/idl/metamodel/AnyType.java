package ccmtools.generator.idl.metamodel;


public class AnyType
	implements Type
{	
	public String generateIdlMapping()
	{
		return "any";
	}
}
