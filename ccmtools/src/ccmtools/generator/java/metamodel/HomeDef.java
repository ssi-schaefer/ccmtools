package ccmtools.generator.java.metamodel;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import ccmtools.generator.java.templates.HomeAdapterLocalTemplate;
import ccmtools.generator.java.templates.HomeAdapterToCorbaTemplate;
import ccmtools.generator.java.templates.HomeApplicationFactoryTemplate;
import ccmtools.generator.java.templates.HomeDefApplicationClassTemplate;
import ccmtools.generator.java.templates.HomeDefApplicationInterfaceTemplate;
import ccmtools.generator.java.templates.HomeDefDeploymentClassTemplate;
import ccmtools.generator.java.templates.HomeDefExplicitApplicationInterfaceTemplate;
import ccmtools.generator.java.templates.HomeDefExplicitInterfaceTemplate;
import ccmtools.generator.java.templates.HomeDefImplicitApplicationInterfaceTemplate;
import ccmtools.generator.java.templates.HomeDefImplicitInterfaceTemplate;
import ccmtools.generator.java.templates.HomeDefInterfaceTemplate;
import ccmtools.generator.java.templates.HomeFactoryToCorbaTemplate;
import ccmtools.utils.SourceFile;
import ccmtools.utils.Text;

public class HomeDef
	extends ModelElement
{
	private ComponentDef component;
	
	public HomeDef(String identifier, List namespace)
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
	
	

	/*************************************************************************
	 * Local Interface Generator Methods
	 * 
	 *************************************************************************/
	
	// Code generator methods -------------------------------------------------
	
	public String generateHomeDefInterface()
	{
		return new HomeDefInterfaceTemplate().generate(this);
	}
		
	public String generateHomeDefImplicitInterface()
	{
		return new HomeDefImplicitInterfaceTemplate().generate(this);
	}
	
	public String generateHomeDefExplicitInterface()
	{
		return new HomeDefExplicitInterfaceTemplate().generate(this);
	}
	
	
	// Generate SourceFile objects --------------------------------------------
	
	public List generateLocalInterfaceSourceFiles()
	{
		List sourceFileList = new ArrayList();
		String localPackageName = Text.joinList(File.separator, getJavaNamespaceList());
		
		SourceFile homeDeclaration = 
			new SourceFile(localPackageName, getIdentifier() + ".java", generateHomeDefInterface());
		
		SourceFile homeImplicitDeclaration = 
			new SourceFile(localPackageName, getIdentifier() + "Implicit.java", generateHomeDefImplicitInterface());
		
		SourceFile homeExplicitDeclaration = 
			new SourceFile(localPackageName, getIdentifier() + "Explicit.java", generateHomeDefExplicitInterface());
				
		sourceFileList.add(homeDeclaration);
		sourceFileList.add(homeImplicitDeclaration);
		sourceFileList.add(homeExplicitDeclaration);
		return sourceFileList;
	}
	
	
	
	/*************************************************************************
	 * Local Component Generator Methods
	 * 
	 *************************************************************************/
	
	// Code generator methods -------------------------------------------------
	
	public String generateHomeDefApplicationInterface()
	{
		return new HomeDefApplicationInterfaceTemplate().generate(this);
	}
		
	public String generateHomeDefImplicitApplicationInterface()
	{
		return new HomeDefImplicitApplicationInterfaceTemplate().generate(this);
	}
	
	public String generateHomeDefExplicitApplicationInterface()
	{
		return new HomeDefExplicitApplicationInterfaceTemplate().generate(this);
	}
	
	public String generateHomeAdapterLocal()
	{              
		return new HomeAdapterLocalTemplate().generate(this);
	}
	
	public String generateHomeDefDeploymentClass()
	{              
		return new HomeDefDeploymentClassTemplate().generate(this);
	}
	
	
	// Generate SourceFile objects --------------------------------------------
	
	public List generateLocalComponentSourceFiles()
	{
		List sourceFileList = new ArrayList();
		String localPackageName = Text.joinList(File.separator, getJavaNamespaceList());
		
		SourceFile ccmHomeDeclaration = 
			new SourceFile(localPackageName, generateCcmIdentifier() + ".java", generateHomeDefApplicationInterface());
		SourceFile ccmHomeImplicitDeclaration = 
			new SourceFile(localPackageName, generateCcmIdentifier() + "Implicit.java", generateHomeDefImplicitApplicationInterface());
		SourceFile ccmHomeExplicitDeclaration = 
			new SourceFile(localPackageName, generateCcmIdentifier() + "Explicit.java", generateHomeDefExplicitApplicationInterface());
		
		SourceFile ccmHomeAdapter = 
			new SourceFile(localPackageName, getIdentifier() + "Adapter.java", generateHomeAdapterLocal());
		
		SourceFile localHomeDeployment = 
			new SourceFile(localPackageName, getIdentifier() + "Deployment.java", generateHomeDefDeploymentClass());
		
		sourceFileList.add(ccmHomeDeclaration);
		sourceFileList.add(ccmHomeImplicitDeclaration);
		sourceFileList.add(ccmHomeExplicitDeclaration);
		sourceFileList.add(ccmHomeAdapter);
		sourceFileList.add(localHomeDeployment);
		return sourceFileList;
	}
	
	
	
	/*************************************************************************
	 * Implementation Generator Methods
	 * 
	 *************************************************************************/
	
	// Code generator methods -------------------------------------------------
	
	public String generateHomeDefApplicationClass()
	{
		return new HomeDefApplicationClassTemplate().generate(this);
	}
	
	public String generateHomeApplicationFactory()
	{
		return new HomeApplicationFactoryTemplate().generate(this);
	}
	
	
	// Generate SourceFile objects --------------------------------------------
	
	public List generateLocalImplementationSourceFiles()
	{
		List sourceFileList = new ArrayList();
		String localPackageName = Text.joinList(File.separator, getJavaNamespaceList());
		
		SourceFile ccmHomeImplementation = 
			new SourceFile(localPackageName, getIdentifier() + "Impl.java", generateHomeDefApplicationClass());
		SourceFile ccmHomeFactory =
			new SourceFile(localPackageName, getIdentifier() + "Factory.java", generateHomeApplicationFactory());
		
		sourceFileList.add(ccmHomeImplementation);
		sourceFileList.add(ccmHomeFactory);
		return sourceFileList;
	}
	
	
	
	/*************************************************************************
	 * Client Library Generator Methods
	 * 
	 *************************************************************************/
	
	// Code generator methods -------------------------------------------------	
	
	public String generateHomeAdapterToCorba()
	{
		return new HomeAdapterToCorbaTemplate().generate(this);
	}
		
	public String generateHomeToCorbaFactory()
	{
		return new HomeFactoryToCorbaTemplate().generate(this);
	}
	

	// Generate SourceFile objects --------------------------------------------
	
	public List generateClientLibSourceFiles()
	{
		List sourceFileList = new ArrayList();		
		String localPackageName = Text.joinList(File.separator, getJavaNamespaceList());
		String remotePackageName = Text.joinList(File.separator, getJavaRemoteNamespaceList());
		SourceFile homeAdapterToCorba = 
			new SourceFile(remotePackageName,getIdentifier() + "AdapterToCorba.java",generateHomeAdapterToCorba());
		SourceFile homeFactory = 
			new SourceFile(localPackageName,getIdentifier() + "Factory.java",generateHomeToCorbaFactory());
		
		sourceFileList.add(homeAdapterToCorba);
		sourceFileList.add(homeFactory);
		return sourceFileList;
	}	
}

