package ccmtools.generator.idl.metamodel;


public class FixedType
	extends TypeImpl
{
	/*************************************************************************
	 * IDL Model Implementation
	 *************************************************************************/
	
	private int digits;
	private int scale;
	
	public void setDigits(int digits)
	{
		this.digits = digits;
	}
	
	public void setScale(int scale)
	{
		this.scale = scale;
	}
	
	
	/*************************************************************************
	 * Type Interface Implementation
	 *************************************************************************/
	
	public String generateIdlMapping()
	{
		return "fixed<" + digits + "," + scale + ">";
	}
}
