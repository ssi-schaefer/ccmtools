package ccmtools.JavaClientLib.metamodel;

import java.util.List;

import ccmtools.JavaClientLib.templates.ProvidesEquivalentMethodImplementationTemplate;
import ccmtools.JavaClientLib.templates.ProvidesNavigationMethodImplementationTemplate;

public class ProvidesDef
	extends ModelElement
{
	private InterfaceDef iface;
	
	public ProvidesDef(String identifier, List ns)
	{
		super(identifier, ns);
	}

	
	public InterfaceDef getInterface()
	{
		return iface;
	}

	public void setInterface(InterfaceDef provides)
	{
		this.iface = provides;
	}
	
	
	// Code generator methods -------------------------------------------------	
	
	public String generateProvidesEquivalentMethodDeclaration()
	{
		return TAB + getInterface().getAbsoluteJavaName() + " provide_" + getIdentifier() + "();\n";
	}
		
	public String generateProvidesEquivalentMethodImplementation()
	{
		return new ProvidesEquivalentMethodImplementationTemplate().generate(this);
	}
		
	public String generateProvidesNavigationMethodImplementation()
	{		
		return new ProvidesNavigationMethodImplementationTemplate().generate(this);
	}
	
	public String generateLocalFacetAdapterDeclaration()
	{
		return TAB + "private " + getInterface().getAbsoluteJavaName() + " " + getIdentifier() + ";\n";
	}
	
}
