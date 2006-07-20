package ccmtools.generator.idl.metamodel;



public class WStringType
	implements Type
{
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
	 * IDL3 generator methods
	 *************************************************************************/

	public String generateIdlMapping()
	{
		if(getBound() == null)
		{
			return "wstring";
		}
		else
		{
			return "wstring<" + getBound().longValue() + ">";
		}
	}
	
	public String generateIdlConstant(Object value)
	{
		return "L\"" + value.toString() + "\"";
	}
	
	public String generateIncludePath()
	{
		return ""; // primitive typed don't need include statements
	}
}
