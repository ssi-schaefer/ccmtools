package ccmtools.generator.java.clientlib.metamodel;

import java.util.List;

public class SupportsDef
	extends ModelElement
{
	private InterfaceDef supports;
	
	public SupportsDef(String identifier, List ns)
	{
		super(identifier, ns);
	}

	
	public InterfaceDef getSupports()
	{
		return supports;
	}

	public void setSupports(InterfaceDef supports)
	{
		this.supports = supports;
	}

	
	// Code generator methods -------------------------------------------------	
	
	
	
}
