package ccmtools.generator.java.metamodel;

import java.util.List;

import ccmtools.generator.java.templates.UsesEquivalentMethodDeclarationTemplate;
import ccmtools.generator.java.templates.UsesEquivalentMethodImplementationTemplate;
import ccmtools.generator.java.templates.UsesMultipleEquivalentMethodDeclarationTemplate;
import ccmtools.generator.java.templates.UsesMultipleEquivalentMethodImplementationTemplate;
import ccmtools.generator.java.templates.UsesMultipleReceptacleDisconnectMethodImplementationTemplate;
import ccmtools.generator.java.templates.UsesReceptacleConnectMethodImplementationTemplate;
import ccmtools.generator.java.templates.UsesReceptacleDisconnectMethodImplementationTemplate;

public class UsesDef
	extends ModelElement
{
	private InterfaceDef iface;
	private boolean multiple;
	
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
	

	public boolean isMultiple()
	{
		return multiple;
	}


	public void setMultiple(boolean multiple)
	{
		this.multiple = multiple;
	}

	
	// Code generator methods -------------------------------------------------
	
	public String generateUsesEquivalentMethodDeclaration()
	{
		if(isMultiple())
		{
			return new UsesMultipleEquivalentMethodDeclarationTemplate().generate(this);
		}
		else
		{
			return new UsesEquivalentMethodDeclarationTemplate().generate(this);			
		}
	}
	
	public String generateLocalReceptacleAdapterDeclaration()
	{
		return TAB + "private " + getInterface().getAbsoluteJavaName() + " " + getIdentifier() + ";\n";
	}
	
	public String generateUsesEquivalentMethodImplementation()
	{		
		if(isMultiple())
		{
			return new UsesMultipleEquivalentMethodImplementationTemplate().generate(this);
		}
		else
		{
			return new UsesEquivalentMethodImplementationTemplate().generate(this);
		}
	}
	
	public String generateUsesReceptacleConnectMethodImplementation()
	{
		return new UsesReceptacleConnectMethodImplementationTemplate().generate(this);
	}
	
	public String generateUsesReceptacleDisconnectMethodImplementation()
	{
		if(isMultiple())
		{
			return new UsesMultipleReceptacleDisconnectMethodImplementationTemplate().generate(this);
		}
		else
		{
			return new UsesReceptacleDisconnectMethodImplementationTemplate().generate(this);			
		}
	}
}
