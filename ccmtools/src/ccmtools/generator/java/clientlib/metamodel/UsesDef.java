package ccmtools.generator.java.clientlib.metamodel;

import java.util.List;

import ccmtools.generator.java.clientlib.templates.UsesEquivalentMethodDeclarationTemplate;
import ccmtools.generator.java.clientlib.templates.UsesEquivalentMethodImplementationTemplate;
import ccmtools.generator.java.clientlib.templates.UsesReceptacleConnectMethodImplementationTemplate;
import ccmtools.generator.java.clientlib.templates.UsesReceptacleDisconnectMethodImplementationTemplate;

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
	
	public String generateUsesEquivalentMethodImplementation()
	{		
		return new UsesEquivalentMethodImplementationTemplate().generate(this);
	}
	
	public String generateUsesReceptacleConnectMethodImplementation()
	{
		return new UsesReceptacleConnectMethodImplementationTemplate().generate(this);
	}
	
	public String generateUsesReceptacleDisconnectMethodImplementation()
	{
		return new UsesReceptacleDisconnectMethodImplementationTemplate().generate(this);
	}
}
