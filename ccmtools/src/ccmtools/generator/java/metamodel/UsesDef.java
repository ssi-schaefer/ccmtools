package ccmtools.generator.java.metamodel;

import java.util.List;

import ccmtools.generator.java.templates.UsesDefContextGetConnectionMethodImplementationTemplate;
import ccmtools.generator.java.templates.UsesDefEquivalentMethodAdapterFromCorbaTemplate;
import ccmtools.generator.java.templates.UsesDefEquivalentMethodAdapterLocalTemplate;
import ccmtools.generator.java.templates.UsesDefEquivalentMethodAdapterToCorbaTemplate;
import ccmtools.generator.java.templates.UsesDefEquivalentMethodDeclarationTemplate;
import ccmtools.generator.java.templates.UsesDefMultipleContextGetConnectionMethodImplementationTemplate;
import ccmtools.generator.java.templates.UsesDefMultipleEquivalentMethodAdapterFromCorbaTemplate;
import ccmtools.generator.java.templates.UsesDefMultipleEquivalentMethodAdapterLocalTemplate;
import ccmtools.generator.java.templates.UsesDefMultipleEquivalentMethodAdapterToCorbaTemplate;
import ccmtools.generator.java.templates.UsesDefMultipleEquivalentMethodDeclarationTemplate;
import ccmtools.generator.java.templates.UsesDefMultipleReceptacleConnectMethodAdapterFromCorbaTemplate;
import ccmtools.generator.java.templates.UsesDefMultipleReceptacleDisconnectMethodAdapterFromCorbaTemplate;
import ccmtools.generator.java.templates.UsesDefMultipleReceptacleDisconnectMethodAdapterLocalTemplate;
import ccmtools.generator.java.templates.UsesDefMultipleReceptacleDisconnectMethodAdapterToCorbaTemplate;
import ccmtools.generator.java.templates.UsesDefReceptacleConnectMethodAdapterFromCorbaTemplate;
import ccmtools.generator.java.templates.UsesDefReceptacleConnectMethodAdapterLocalTemplate;
import ccmtools.generator.java.templates.UsesDefReceptacleConnectMethodAdapterToCorbaTemplate;
import ccmtools.generator.java.templates.UsesDefReceptacleDisconnectMethodAdapterFromCorbaTemplate;
import ccmtools.generator.java.templates.UsesDefReceptacleDisconnectMethodAdapterLocalTemplate;
import ccmtools.generator.java.templates.UsesDefReceptacleDisconnectMethodAdapterToCorbaTemplate;

public class UsesDef
	extends ModelElement
{
	private ComponentDef component;
	private InterfaceDef iface;
	private boolean multiple;
	
	public UsesDef(String identifier, List<String> namespace)
	{
		super(identifier, namespace);
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
	 *************************************************************************/
	
	public String generateEquivalentMethodDeclaration()
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
	 * Local Adapter Generator
	 *************************************************************************/
	
	public String generateReceptacleAdapterReference()
	{
		if(isMultiple())
		{
			return TAB + "private java.util.Map<Cookie, " + getInterface().generateAbsoluteJavaName() 
					+ "> " + getIdentifier() 
					+ "ReceptacleMap = " + NL + TAB2 
					+ " new java.util.HashMap<Cookie, " + getInterface().generateAbsoluteJavaName() 
					+ ">();";
		}
		else
		{
			return TAB + "private " +  getInterface().generateAbsoluteJavaName() + 
					" " + getIdentifier() + "Receptacle = null;";			
		}
	}
	
	public String generateContextGetConnectionMethodDeclaration()
	{
		if(isMultiple())
		{
			return TAB + "java.util.Map get_connections_" +  getIdentifier() + "();"; 
		}
		else
		{
			return TAB + getInterface().generateAbsoluteJavaName() + 
				" get_connection_" + getIdentifier() + "()\n" + 
				TAB2 + "throws NoConnection;";
		}	
	}
	
	public String generateContextGetConnectionMethodImplementation()
	{
		if(isMultiple())
		{
			return new UsesDefMultipleContextGetConnectionMethodImplementationTemplate().generate(this);
		}
		else
		{
			return new UsesDefContextGetConnectionMethodImplementationTemplate().generate(this);
		}
	}
	
	public String generateEquivalentMethodAdapterLocal()
	{
		if(isMultiple())
		{
			return new UsesDefMultipleEquivalentMethodAdapterLocalTemplate().generate(this);
		}
		else
		{
			return new UsesDefEquivalentMethodAdapterLocalTemplate().generate(this);			
		}
	}
	
	public String generateReceptacleConnectMethodAdapterLocal()
	{
		return new UsesDefReceptacleConnectMethodAdapterLocalTemplate().generate(this);
	}
		
	public String generateReceptacleDisconnectMethodAdapterLocal()
	{
		if(isMultiple())
		{
			return new UsesDefMultipleReceptacleDisconnectMethodAdapterLocalTemplate().generate(this);
		}
		else
		{
			return new UsesDefReceptacleDisconnectMethodAdapterLocalTemplate().generate(this);
		}
	}

	
	
	/*************************************************************************
	 * CORBA Adapter Generator Methods
	 *************************************************************************/

	public String generateReceptacleReferenceAdapterToCorba()
	{
		if(isMultiple())
		{
			return TAB +  "private Map<Cookie, " 
				+ getInterface().generateAbsoluteJavaName() + "> " 
				+ getIdentifier() + "ReceptacleMap = " + NL + TAB2 
				+ "new HashMap<Cookie, " 
				+ getInterface().generateAbsoluteJavaName() + ">();\n";
		}
		else
		{
			return TAB + "private " + getInterface().generateAbsoluteJavaName()  
				+ " " + getIdentifier() + ";\n";			
		}
	}

	public String generateEquivalentMethodAdapterToCorba()
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

	public String generateEquivalentMethodAdapterFromCorba()
	{
		if(isMultiple())
		{
			return new UsesDefMultipleEquivalentMethodAdapterFromCorbaTemplate().generate(this);
		}
		else
		{
			return new UsesDefEquivalentMethodAdapterFromCorbaTemplate().generate(this);
		}
	}
	
	public String generateReceptacleConnectMethodAdapterToCorba()
	{
		return new UsesDefReceptacleConnectMethodAdapterToCorbaTemplate().generate(this);
	}
	
	public String generateReceptacleDisconnectMethodAdapterToCorba()
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

	public String generateReceptacleConnectMethodAdapterFromCorba()
	{
		if(isMultiple())
		{
			return new UsesDefMultipleReceptacleConnectMethodAdapterFromCorbaTemplate().generate(this);
		}
		else
		{
			return new UsesDefReceptacleConnectMethodAdapterFromCorbaTemplate().generate(this);
		}
	}
	
	public String generateReceptacleDisconnectMethodAdapterFromCorba()
	{
		if(isMultiple())
		{
			return new UsesDefMultipleReceptacleDisconnectMethodAdapterFromCorbaTemplate().generate(this);
		}
		else
		{
			return new UsesDefReceptacleDisconnectMethodAdapterFromCorbaTemplate().generate(this);			
		}
	}

	public String generateCorbaReceptacleReferenceDeclaration()
	{
		if(isMultiple())
		{
			return TAB +  "private Map<ccmtools.corba.Components.Cookie, " 
				+ getInterface().generateAbsoluteIdlName() + "> " 
				+ getIdentifier() + "ReceptacleMap;\n";
		}
		else
		{
			return TAB +  "private " + getInterface().generateAbsoluteIdlName() + " " 
				+ getIdentifier() + "Receptacle;\n";
		}
	}
	
	public String generateCorbaReceptacleReferenceInit()
	{
		if(isMultiple())
		{
			return TAB2 + getIdentifier()  
                + "ReceptacleMap = new HashMap<ccmtools.corba.Components.Cookie, " 
				+ getInterface().generateAbsoluteIdlName() + ">();\n";	
		}
		else
		{
			return TAB2 + getIdentifier() + "Receptacle = null;\n";			
		}
	}
}
