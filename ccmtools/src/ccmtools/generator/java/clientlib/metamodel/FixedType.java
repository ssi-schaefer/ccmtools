package ccmtools.generator.java.clientlib.metamodel;

public class FixedType
	implements Type
{
	public String generateJavaMapping()
	{
		return "java.math.BigDecimal";
	}		
	
	public String generateJavaMapping(PassingDirection direction)
	{
		if (direction == PassingDirection.IN
			|| direction == PassingDirection.RESULT)
		{
			return generateJavaMapping();
		}
		else // INOUT, OUT
		{
			return "org.omg.CORBA.FixedHolder";
		}
	}
}
