package ccmtools.generator.idl.metamodel;



public class StringType
	extends TypeImpl
{
	/*************************************************************************
	 * IDL Model Implementation
	 *************************************************************************/
	
	private Long bound = null;
	
	
	public Long getBound()
	{
		return bound;
	}

	public void setBound(Long bound)
	{
		this.bound = bound;
	}


	/*************************************************************************
	 * Type Interface Implementation
	 *************************************************************************/
	
	public String generateIdlMapping()
	{
		if(getBound() == null)
		{
			return "string";
		}
		else
		{
			return "string<" + getBound().longValue() + ">";
		}
	}
	
	public String generateIdlConstant(Object value)
	{
		return "\"" + value.toString() + "\"";
	}
	
//	public String generateIncludePath()
//	{
//		return ""; // primitive typed don't need include statements
//	}
}
