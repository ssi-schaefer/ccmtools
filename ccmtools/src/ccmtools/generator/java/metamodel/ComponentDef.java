package ccmtools.generator.java.metamodel;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import ccmtools.generator.java.templates.CcmComponentAdapterTemplate;
import ccmtools.generator.java.templates.CcmComponentContextImplementationTemplate;
import ccmtools.generator.java.templates.CcmComponentContextInterfaceTemplate;
import ccmtools.generator.java.templates.CcmComponentDeclarationTemplate;
import ccmtools.generator.java.templates.CcmComponentImplementationTemplate;
import ccmtools.generator.java.templates.ComponentAdapterToCorbaTemplate;
import ccmtools.generator.java.templates.ComponentDeclarationTemplate;
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
	
	
	
	/**
	 * Java Local Interface Generator
	 * 
	 */
	
	// Code generator methods -------------------------------------------------

	public String generateComponentDeclaration()
	{
		return new ComponentDeclarationTemplate().generate(this);
	}
	
	public String generateSupportsDeclarations()
	{
		List supportsList = new ArrayList();
		for(Iterator i=getSupports().iterator(); i.hasNext();)
		{
			SupportsDef s = (SupportsDef)i.next();
			supportsList.add(s.getInterface().getAbsoluteJavaName());
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
			new SourceFile(localPackageName, getIdentifier() + ".java", generateComponentDeclaration());
		
		sourceFileList.add(componentDeclaration);
		return sourceFileList;
	}
	
	
	
	
	/**
	 * Java Local Component Generator
	 * 
	 */
	
	// Code generator methods -------------------------------------------------
	
	public String generateCcmComponentDeclaration()
	{
		return new CcmComponentDeclarationTemplate().generate(this);
	}	
		
	public String generateCcmComponentContextInterface()
	{
		return new CcmComponentContextInterfaceTemplate().generate(this);
	}
	
	public String generateCcmComponentContextImplementation()
	{
		return new CcmComponentContextImplementationTemplate().generate(this);
	}
	
	public String generateCcmComponentAdapter()
	{              
		return new CcmComponentAdapterTemplate().generate(this);
	}
	
	
	// Generate SourceFile objects --------------------------------------------
	
	public List generateLocalComponentSourceFiles()
	{
		List sourceFileList = new ArrayList();

		String localPackageName = Text.joinList(File.separator, getJavaNamespaceList());
		SourceFile ccmComponentDeclaration = 
			new SourceFile(localPackageName, getCcmIdentifier() + ".java", generateCcmComponentDeclaration());
		
		SourceFile ccmComponentContextInterface = 
			new SourceFile(localPackageName, getCcmIdentifier() + "_Context.java", 
					generateCcmComponentContextInterface());
		SourceFile ccmComponentContextImpl = 
			new SourceFile(localPackageName, getCcmIdentifier() + "_ContextImpl.java", 
					generateCcmComponentContextImplementation());
		
		SourceFile ccmComponentAdapter = 
			new SourceFile(localPackageName, getIdentifier() + "Adapter.java", generateCcmComponentAdapter());
	
		sourceFileList.add(ccmComponentDeclaration);
		sourceFileList.add(ccmComponentContextInterface);
		sourceFileList.add(ccmComponentContextImpl);
		sourceFileList.add(ccmComponentAdapter);
		return sourceFileList;
	}
	
	
	
	/**
	 * Java Local Implementation Generator
	 * 
	 */
	
	// Code generator methods -------------------------------------------------
	
	public String generateCcmComponentImplementation()
	{
		return new CcmComponentImplementationTemplate().generate(this);
	}	
	
	
	// Generate SourceFile objects --------------------------------------------
	
	public List generateLocalImplementationSourceFiles()
	{
		List sourceFileList = new ArrayList();
		String localPackageName = Text.joinList(File.separator, getJavaNamespaceList());
		
		SourceFile ccmComponentImplementation = 
			new SourceFile(localPackageName, getIdentifier() + "Impl.java", generateCcmComponentImplementation());
		
		sourceFileList.add(ccmComponentImplementation);
		return sourceFileList;
	}
	
	
	
	/**
	 * Java Client Library Generator
	 * 
	 */
	
	// Code generator methods -------------------------------------------------	

	public String generateComponentAdapterToCorba()
	{
		return new ComponentAdapterToCorbaTemplate().generate(this);
	}
	
	
	// Generate SourceFile objects --------------------------------------------
	
	public List generateClientLibSourceFiles()
	{
		List sourceFileList = new ArrayList();

		String remotePackageName = Text.joinList(File.separator, getJavaRemoteNamespaceList()); 
		SourceFile componentAdapterToCorba = 
			new SourceFile(remotePackageName, getIdentifier() + "AdapterToCorba.java",generateComponentAdapterToCorba());
		
		sourceFileList.add(componentAdapterToCorba);
		return sourceFileList;
	}
}