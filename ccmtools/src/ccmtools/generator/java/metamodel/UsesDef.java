package ccmtools.generator.java.metamodel;

import java.util.List;

import ccmtools.generator.java.templates.ContextGetConnectionMethodImplementationTemplate;
import ccmtools.generator.java.templates.UsesDefEquivalentMethodAdapterLocalTemplate;
import ccmtools.generator.java.templates.UsesDefEquivalentMethodAdapterToCorbaTemplate;
import ccmtools.generator.java.templates.UsesDefEquivalentMethodDeclarationTemplate;
import ccmtools.generator.java.templates.UsesDefMultipleEquivalentMethodAdapterToCorbaTemplate;
import ccmtools.generator.java.templates.UsesDefMultipleEquivalentMethodDeclarationTemplate;
import ccmtools.generator.java.templates.UsesDefMultipleReceptacleDisconnectMethodAdapterToCorbaTemplate;
import ccmtools.generator.java.templates.UsesDefReceptacleConnectMethodAdapterToCorbaTemplate;
import ccmtools.generator.java.templates.UsesDefReceptacleDisconnectMethodAdapterToCorbaTemplate;

public class UsesDef
	extends ModelElement
{
	private ComponentDef component;
	private InterfaceDef iface;
	private boolean multiple;
	
	public UsesDef(String identifier, List ns)
	{
		super(identifier, ns);
	}

	
	public ComponentDef getComponent()
	{
		return component;
	}

	public void setComponent(ComponentDef component)
	{
		this.component = component;
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

	
	/*************************************************************************
	 * Local Interface Generator
	 * 
	 *************************************************************************/
	
	public String generateUsesDefEquivalentMethodDeclaration()
	{
		if(isMultiple())
		{
			return new UsesDefMultipleEquivalentMethodDeclarationTemplate().generate(this);
		}
		else
		{
			return new UsesDefEquivalentMethodDeclarationTemplate().generate(this);			
		}
	}
	

	
	/*************************************************************************
	 * Local Component Generator
	 * 
	 *************************************************************************/
	
	public String generateReceptacleAdapterReference()
	{
		return TAB + "private " +  getInterface().generateAbsoluteJavaName() + 
			" " + getIdentifier() + "Receptacle = null;";
	}
	
	public String generateContextGetConnectionMethodDeclaration()
	{
		return TAB + getInterface().generateAbsoluteJavaName() + 
				" get_connection_" + getIdentifier() + "()\n" + 
				TAB + "throws ccm.local.Components.NoConnection;";
	}
	
	public String generateContextGetConnectionMethodImplementation()
	{
		return new ContextGetConnectionMethodImplementationTemplate().generate(this);
	}
	
	public String generateUsesDefEquivalentMethodAdapterLocal()
	{
		if(isMultiple())
		{
			//return new UsesMultipleEquivalentMethodAdapterTemplate().generate(this);
			return null;
		}
		else
		{
			return new UsesDefEquivalentMethodAdapterLocalTemplate().generate(this);			
		}
	}
	
		
	
	/*************************************************************************
	 * Client Library Generator
	 * 
	 *************************************************************************/

	public String generateReceptacleReferenceAdapterToCorba()
	{
		return TAB + "private " + getInterface().generateAbsoluteJavaName() + 
				" " + getIdentifier() + ";\n";
	}

	public String generateUsesDefEquivalentMethodAdapterToCorba()
	{		
		if(isMultiple())
		{
			return new UsesDefMultipleEquivalentMethodAdapterToCorbaTemplate().generate(this);
		}
		else
		{
			return new UsesDefEquivalentMethodAdapterToCorbaTemplate().generate(this);
		}
	}	

	public String generateUsesDefReceptacleConnectMethodAdapterToCorba()
	{
		return new UsesDefReceptacleConnectMethodAdapterToCorbaTemplate().generate(this);
	}
	
	public String generateUsesDefReceptacleDisconnectMethodAdapterToCorba()
	{
		if(isMultiple())
		{
			return new UsesDefMultipleReceptacleDisconnectMethodAdapterToCorbaTemplate().generate(this);
		}
		else
		{
			return new UsesDefReceptacleDisconnectMethodAdapterToCorbaTemplate().generate(this);			
		}
	}

}
