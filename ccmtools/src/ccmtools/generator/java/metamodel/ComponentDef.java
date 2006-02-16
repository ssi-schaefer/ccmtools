package ccmtools.generator.java.metamodel;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import ccmtools.generator.java.templates.ComponentDefAdapterLocalTemplate;
import ccmtools.generator.java.templates.ComponentDefAdapterToCorbaTemplate;
import ccmtools.generator.java.templates.ComponentDefApplicationClassTemplate;
import ccmtools.generator.java.templates.ComponentDefApplicationInterfaceTemplate;
import ccmtools.generator.java.templates.ComponentDefContextClassTemplate;
import ccmtools.generator.java.templates.ComponentDefContextInterfaceTemplate;
import ccmtools.generator.java.templates.ComponentDefInterfaceTemplate;
import ccmtools.utils.SourceFile;
import ccmtools.utils.Text;

public class ComponentDef
	extends ModelElement
{
	private List attributes = new ArrayList();
	private List facet = new ArrayList();
	private List receptacle = new ArrayList();
	private List supports = new ArrayList();
	private HomeDef home;
	
	
	public ComponentDef(String identifier, List namespace)
	{
		super(identifier, namespace);
	}
	
	
	public List getAttributes()
	{
		return attributes;
	}
	
	public List getFacet()
	{
		return facet;
	}
	
	public List getReceptacle()
	{
		return receptacle;
	}
	
	public List getSupports()
	{
		return supports;
	}


	public HomeDef getHome()
	{
		return home;
	}
	
	public void setHome(HomeDef home)
	{
		this.home = home;
	}
	
	
	
	/*************************************************************************
	 * Local Interface Generator Methods
	 * 
	 *************************************************************************/
	
	// Code generator methods -------------------------------------------------

	public String generateInterface()
	{
		return new ComponentDefInterfaceTemplate().generate(this);
	}
	
	public String generateSupportsDeclarations()
	{
		List supportsList = new ArrayList();
		for(Iterator i=getSupports().iterator(); i.hasNext();)
		{
			SupportsDef s = (SupportsDef)i.next();
			supportsList.add(s.getInterface().generateAbsoluteJavaName());
		}
		if(supportsList.size() > 0)
		{
			return ", " + Text.joinList(", ", supportsList);
		}
		else
		{
			return ""; // no supported interfaces
		}
	}
	

	// Generate SourceFile objects --------------------------------------------
	
	public List generateLocalInterfaceSourceFiles()
	{
		List sourceFileList = new ArrayList();
		String localPackageName = Text.joinList(File.separator, getJavaNamespaceList()); 
	
		SourceFile iface = 
			new SourceFile(localPackageName, getIdentifier() + ".java", generateInterface());
		sourceFileList.add(iface);
		
		return sourceFileList;
	}
	
	
	
	
	/*************************************************************************
	 * Local Component Generator Methods
	 * 
	 *************************************************************************/
	
	// Code generator methods -------------------------------------------------
	
	public String generateApplicationInterface()
	{
		return new ComponentDefApplicationInterfaceTemplate().generate(this);
	}	
		
	public String generateContextInterface()
	{
		return new ComponentDefContextInterfaceTemplate().generate(this);
	}
	
	public String generateContextClass()
	{
		return new ComponentDefContextClassTemplate().generate(this);
	}
	
	public String generateAdapterLocal()
	{              
		return new ComponentDefAdapterLocalTemplate().generate(this);
	}
	
	
	// Generate SourceFile objects --------------------------------------------
	
	public List generateLocalComponentSourceFiles()
	{
		List sourceFileList = new ArrayList();
		String localPackageName = Text.joinList(File.separator, getJavaNamespaceList());
		
		SourceFile applicationInterface = 
			new SourceFile(localPackageName, generateCcmIdentifier() + ".java", generateApplicationInterface());
		sourceFileList.add(applicationInterface);
		
		SourceFile contextInterface = 
			new SourceFile(localPackageName, generateCcmIdentifier() + "_Context.java", generateContextInterface());
		sourceFileList.add(contextInterface);

		SourceFile contextClass = 
			new SourceFile(localPackageName, generateCcmIdentifier() + "_ContextImpl.java", generateContextClass());
		sourceFileList.add(contextClass);
		
		SourceFile adapterLocal = 
			new SourceFile(localPackageName, getIdentifier() + "Adapter.java", generateAdapterLocal());
		sourceFileList.add(adapterLocal);
	
		return sourceFileList;
	}
	
	
	
	/*************************************************************************
	 * Application Generator Methods
	 * 
	 *************************************************************************/
	
	// Code generator methods -------------------------------------------------
	
	public String generateApplicationClass()
	{
		return new ComponentDefApplicationClassTemplate().generate(this);
	}	
	
	
	// Generate SourceFile objects --------------------------------------------
	
	public List generateApplicationSourceFiles()
	{
		List sourceFileList = new ArrayList();
		String localPackageName = Text.joinList(File.separator, getJavaNamespaceList());
		
		SourceFile applicationClass = 
			new SourceFile(localPackageName, getIdentifier() + "Impl.java", generateApplicationClass());		
		sourceFileList.add(applicationClass);
		
		return sourceFileList;
	}
	
	
	
	/*************************************************************************
	 * Client Library Generator Methods
	 * 
	 *************************************************************************/
	
	// Code generator methods -------------------------------------------------	

	public String generateAdapterToCorba()
	{
		return new ComponentDefAdapterToCorbaTemplate().generate(this);
	}
	
	
	// Generate SourceFile objects --------------------------------------------
	
	public List generateClientLibSourceFiles()
	{
		List sourceFileList = new ArrayList();
		String remotePackageName = Text.joinList(File.separator, getJavaRemoteNamespaceList());
		
		SourceFile adapterToCorba = 
			new SourceFile(remotePackageName, getIdentifier() + "AdapterToCorba.java",generateAdapterToCorba());
		sourceFileList.add(adapterToCorba);
		
		return sourceFileList;
	}
}