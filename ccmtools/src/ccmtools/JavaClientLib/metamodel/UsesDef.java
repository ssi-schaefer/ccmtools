package ccmtools.JavaClientLib.metamodel;

import java.util.List;

import ccmtools.JavaClientLib.templates.UsesEquivalentMethodDeclarationTemplate;

public class UsesDef
	extends ModelElement
{
	private InterfaceDef iface;
	
	public UsesDef(String identifier, List ns)
	{
		super(identifier, ns);
	}

	
	public InterfaceDef getInterface()
	{
		return iface;
	}

	public void setInterface(InterfaceDef iface)
	{
		this.iface = iface;
	}
	
	
	// Code generator methods -------------------------------------------------
	
	public String generateUsesEquivalentMethodDeclaration()
	{
		return new UsesEquivalentMethodDeclarationTemplate().generate(this);
	}
	
	public String generateLocalReceptacleAdapterDeclaration()
	{
		return TAB + "private " + getInterface().getAbsoluteJavaName() + " " + getIdentifier() + ";\n";
	}
	
}
