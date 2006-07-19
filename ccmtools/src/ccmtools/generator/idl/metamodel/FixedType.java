package ccmtools.generator.idl.metamodel;

public class FixedType
	implements Type
{
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
	
	
	public String generateIdlMapping()
	{
		return "fixed<" + digits + "," + scale + ">";
	}
}
