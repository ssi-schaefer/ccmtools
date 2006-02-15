package ccmtools.generator.java.metamodel;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import ccmtools.generator.java.templates.ComponentDefAdapterLocalTemplate;
import ccmtools.generator.java.templates.ComponentDefAdapterToCorbaTemplate;
import ccmtools.generator.java.templates.ComponentDefApplicationClassTemplate;
import ccmtools.generator.java.templates.ComponentDefApplicationInterfaceTemplate;
import ccmtools.generator.java.templates.ComponentDefInterfaceTemplate;
import ccmtools.generator.java.templates.ContextClassTemplate;
import ccmtools.generator.java.templates.ContextInterfaceTemplate;
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

	public String generateComponentInterface()
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
	
		SourceFile componentDeclaration = 
			new SourceFile(localPackageName, getIdentifier() + ".java", generateComponentInterface());
		
		sourceFileList.add(componentDeclaration);
		return sourceFileList;
	}
	
	
	
	
	/*************************************************************************
	 * Local Component Generator Methods
	 * 
	 *************************************************************************/
	
	// Code generator methods -------------------------------------------------
	
	public String generateComponentDefApplicationInterface()
	{
		return new ComponentDefApplicationInterfaceTemplate().generate(this);
	}	
		
	public String generateContextInterface()
	{
		return new ContextInterfaceTemplate().generate(this);
	}
	
	public String generateContextClass()
	{
		return new ContextClassTemplate().generate(this);
	}
	
	public String generateComponentDefAdapterLocal()
	{              
		return new ComponentDefAdapterLocalTemplate().generate(this);
	}
	
	
	// Generate SourceFile objects --------------------------------------------
	
	public List generateLocalComponentSourceFiles()
	{
		List sourceFileList = new ArrayList();

		String localPackageName = Text.joinList(File.separator, getJavaNamespaceList());
		SourceFile ccmComponentDeclaration = 
			new SourceFile(localPackageName, generateCcmIdentifier() + ".java", generateComponentDefApplicationInterface());
		
		SourceFile ccmComponentContextInterface = 
			new SourceFile(localPackageName, generateCcmIdentifier() + "_Context.java", 
					generateContextInterface());
		SourceFile ccmComponentContextImpl = 
			new SourceFile(localPackageName, generateCcmIdentifier() + "_ContextImpl.java", 
					generateContextClass());
		
		SourceFile ccmComponentAdapter = 
			new SourceFile(localPackageName, getIdentifier() + "Adapter.java", generateComponentDefAdapterLocal());
	
		sourceFileList.add(ccmComponentDeclaration);
		sourceFileList.add(ccmComponentContextInterface);
		sourceFileList.add(ccmComponentContextImpl);
		sourceFileList.add(ccmComponentAdapter);
		return sourceFileList;
	}
	
	
	
	/*************************************************************************
	 * Implementation Generator Methods
	 * 
	 *************************************************************************/
	
	// Code generator methods -------------------------------------------------
	
	public String generateComponentDefApplicationClass()
	{
		return new ComponentDefApplicationClassTemplate().generate(this);
	}	
	
	
	// Generate SourceFile objects --------------------------------------------
	
	public List generateLocalImplementationSourceFiles()
	{
		List sourceFileList = new ArrayList();
		String localPackageName = Text.joinList(File.separator, getJavaNamespaceList());
		
		SourceFile ccmComponentImplementation = 
			new SourceFile(localPackageName, getIdentifier() + "Impl.java", generateComponentDefApplicationClass());
		
		sourceFileList.add(ccmComponentImplementation);
		return sourceFileList;
	}
	
	
	
	/*************************************************************************
	 * Client Library Generator Methods
	 * 
	 *************************************************************************/
	
	// Code generator methods -------------------------------------------------	

	public String generateComponentDefAdapterToCorba()
	{
		return new ComponentDefAdapterToCorbaTemplate().generate(this);
	}
	
	
	// Generate SourceFile objects --------------------------------------------
	
	public List generateClientLibSourceFiles()
	{
		List sourceFileList = new ArrayList();

		String remotePackageName = Text.joinList(File.separator, getJavaRemoteNamespaceList()); 
		SourceFile componentAdapterToCorba = 
			new SourceFile(remotePackageName, getIdentifier() + "AdapterToCorba.java",generateComponentDefAdapterToCorba());
		
		sourceFileList.add(componentAdapterToCorba);
		return sourceFileList;
	}
}