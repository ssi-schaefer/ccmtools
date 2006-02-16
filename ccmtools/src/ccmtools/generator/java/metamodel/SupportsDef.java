package ccmtools.generator.java.metamodel;

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

	
	
	/*************************************************************************
	 * Local Interface Generator
	 * 
	 *************************************************************************/
	
	
	
	/*************************************************************************
	 * Local Component Generator
	 * 
	 *************************************************************************/
	
}
