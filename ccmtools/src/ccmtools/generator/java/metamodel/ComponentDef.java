package ccmtools.generator.java.metamodel;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import ccmtools.generator.java.templates.ComponentDefAdapterFromCorbaTemplate;
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
	
	public Set getJavaImportStatements()
	{
		Set importStatements = new TreeSet();
		// Some component management methods can throw this exception type
		importStatements.add("ccm.local.Components.CCMException");
		// Each component class refers to its context object
		importStatements.add(generateAbsoluteJavaCcmName() + "_Context");
		for(Iterator i = getAttributes().iterator(); i.hasNext();)
		{
			AttributeDef a = (AttributeDef)i.next();
			importStatements.addAll(a.getType().getJavaImportStatements());
		}
		for(Iterator i=getSupports().iterator(); i.hasNext();)
		{
			SupportsDef s = (SupportsDef)i.next();
			importStatements.addAll(s.getJavaImportStatements());
		}
		importStatements.add(generateAbsoluteJavaName());
		return importStatements;
	}

	
	/*************************************************************************
	 * Local Interface Generator Methods
	 * 
	 *************************************************************************/
	
	public String generateJavaImportStatements()
	{
		return generateJavaImportStatements(getJavaImportStatements());
	}
	
	public String generateJavaImportStatements(String namespace)
	{
		return generateJavaImportStatements(namespace, getJavaImportStatements());
	}
	
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
			supportsList.add(s.getInterface().getIdentifier());
		}
		if(supportsList.size() > 0)
		{
			return "," + NL + TAB + Text.joinList( "," + NL + TAB, supportsList);
		}
		else
		{
			return ""; // no supported interfaces
		}
	}
	
	
	public String generateSupportsCcmDeclarations()
	{
		List supportsList = new ArrayList();
		for(Iterator i=getSupports().iterator(); i.hasNext();)
		{
			SupportsDef s = (SupportsDef)i.next();
			supportsList.add(s.getInterface().generateCcmIdentifier());
		}
		if(supportsList.size() > 0)
		{
			return "," + NL + TAB + Text.joinList("," + NL + TAB, supportsList);
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
		
		SourceFile applicationInterface = new SourceFile(localPackageName, 
				generateCcmIdentifier() + ".java", generateApplicationInterface());
		sourceFileList.add(applicationInterface);
		
		SourceFile contextInterface = new SourceFile(localPackageName, 
				generateCcmIdentifier() + "_Context.java", generateContextInterface());
		sourceFileList.add(contextInterface);

		SourceFile contextClass = new SourceFile(localPackageName, 
				generateCcmIdentifier() + "_ContextImpl.java", generateContextClass());
		sourceFileList.add(contextClass);
		
		SourceFile adapterLocal = new SourceFile(localPackageName, 
				getIdentifier() + "Adapter.java", generateAdapterLocal());
		sourceFileList.add(adapterLocal);
	
		return sourceFileList;
	}
	
	
	
	/*************************************************************************
	 * Application Generator Methods
	 * 
	 *************************************************************************/
	
	public String generateApplicationClass()
	{
		return new ComponentDefApplicationClassTemplate().generate(this);
	}	
	
	
	// Generate SourceFile objects --------------------------------------------
	
	public List generateApplicationSourceFiles()
	{
		List sourceFileList = new ArrayList();
		String localPackageName = Text.joinList(File.separator, getJavaNamespaceList());
		
		SourceFile applicationClass = new SourceFile(localPackageName, getIdentifier() + 
				"Impl.java", generateApplicationClass());		
		sourceFileList.add(applicationClass);
		
		return sourceFileList;
	}
	
	
	
	/*************************************************************************
	 * Client Library Component Generator Methods
	 * 
	 *************************************************************************/
	
	public String generateAdapterToCorba()
	{
		return new ComponentDefAdapterToCorbaTemplate().generate(this);
	}
	
	
	// Generate SourceFile objects --------------------------------------------
	
	public List generateClientLibComponentSourceFiles()
	{
		List sourceFileList = new ArrayList();
		String localPackageName = Text.joinList(File.separator, getJavaNamespaceList());
		
		SourceFile adapterToCorba = new SourceFile(localPackageName, getIdentifier() + 
				"AdapterToCorba.java",generateAdapterToCorba());
		sourceFileList.add(adapterToCorba);
		
		return sourceFileList;
	}
	
	
	/*************************************************************************
	 * CORBA Component Generator Methods
	 * 
	 *************************************************************************/
	
	public String generateAdapterFromCorba()
	{
		return new ComponentDefAdapterFromCorbaTemplate().generate(this);
	}
	
	
	// Generate SourceFile objects --------------------------------------------
	
	public List generateCorbaComponentSourceFiles()
	{
		List sourceFileList = new ArrayList();
		String remotePackageName = Text.joinList(File.separator, getJavaRemoteNamespaceList());
		
		SourceFile adapterToCorba = new SourceFile(remotePackageName, getIdentifier() + 
				"AdapterFromCorba.java",generateAdapterFromCorba());
		sourceFileList.add(adapterToCorba);
		
		return sourceFileList;
	}
}