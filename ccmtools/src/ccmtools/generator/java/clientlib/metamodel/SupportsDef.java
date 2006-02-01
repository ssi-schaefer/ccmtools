package ccmtools.generator.java.clientlib.metamodel;

import java.util.List;

public class SupportsDef
	extends ModelElement
{
	private InterfaceDef iface;
	
	public SupportsDef(String identifier, List ns)
	{
		super(identifier, ns);
	}

	
	public InterfaceDef getInterface()
	{
		return iface;
	}

	public void setInterface(InterfaceDef supports)
	{
		this.iface = supports;
	}

	
	// Code generator methods -------------------------------------------------	
	
	
	
}
