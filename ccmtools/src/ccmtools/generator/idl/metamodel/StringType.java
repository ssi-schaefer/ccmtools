package ccmtools.generator.idl.metamodel;


public class StringType
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
}
