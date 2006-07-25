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
	
//	public String generateIdlConstant(Object value)
//	{
//		return value.toString();
//	}
//	
//	public String generateIncludePath()
//	{
//		return ""; // primitive typed don't need include statements
//	}
}
