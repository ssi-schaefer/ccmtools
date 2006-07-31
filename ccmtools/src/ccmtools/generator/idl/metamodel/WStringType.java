package ccmtools.generator.idl.metamodel;



public class WStringType
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
}
