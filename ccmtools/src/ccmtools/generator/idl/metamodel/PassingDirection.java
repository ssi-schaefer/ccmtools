package ccmtools.generator.idl.metamodel;

public enum PassingDirection
{
	IN,
	INOUT, 
	OUT;

	public String toString()
	{
		switch(this)
		{
		case IN: 	
			return "in";
		case INOUT:
			return "inout";
		case OUT:
			return "out";
		default: 
			return "";
		}
	}
}
