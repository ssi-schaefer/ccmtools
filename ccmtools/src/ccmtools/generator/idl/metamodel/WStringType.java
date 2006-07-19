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
}
