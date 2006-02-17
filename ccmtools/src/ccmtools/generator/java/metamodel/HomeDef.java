package ccmtools.generator.java.metamodel;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import ccmtools.generator.java.templates.HomeDefAdapterLocalTemplate;
import ccmtools.generator.java.templates.HomeDefAdapterToCorbaTemplate;
import ccmtools.generator.java.templates.HomeDefApplicationClassTemplate;
import ccmtools.generator.java.templates.HomeDefApplicationInterfaceTemplate;
import ccmtools.generator.java.templates.HomeDefDeploymentClientLibTemplate;
import ccmtools.generator.java.templates.HomeDefDeploymentLocalTemplate;
import ccmtools.generator.java.templates.HomeDefExplicitApplicationInterfaceTemplate;
import ccmtools.generator.java.templates.HomeDefExplicitInterfaceTemplate;
import ccmtools.generator.java.templates.HomeDefFactoryApplicationTemplate;
import ccmtools.generator.java.templates.HomeDefImplicitApplicationInterfaceTemplate;
import ccmtools.generator.java.templates.HomeDefImplicitInterfaceTemplate;
import ccmtools.generator.java.templates.HomeDefInterfaceTemplate;
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
	
	public String generateInterface()
	{
		return new HomeDefInterfaceTemplate().generate(this);
	}
		
	public String generateImplicitInterface()
	{
		return new HomeDefImplicitInterfaceTemplate().generate(this);
	}
	
	public String generateExplicitInterface()
	{
		return new HomeDefExplicitInterfaceTemplate().generate(this);
	}
	
	
	// Generate SourceFile objects --------------------------------------------
	
	public List generateLocalInterfaceSourceFiles()
	{
		List sourceFileList = new ArrayList();
		String localPackageName = Text.joinList(File.separator, getJavaNamespaceList());
		
		SourceFile iface = 
			new SourceFile(localPackageName, getIdentifier() + ".java", generateInterface());
		sourceFileList.add(iface);
		
		SourceFile implicitInterface = 
			new SourceFile(localPackageName, getIdentifier() + "Implicit.java", generateImplicitInterface());
		sourceFileList.add(implicitInterface);
		
		SourceFile explicitInterface = 
			new SourceFile(localPackageName, getIdentifier() + "Explicit.java", generateExplicitInterface());
		sourceFileList.add(explicitInterface);
				
		return sourceFileList;
	}
	
	
	
	/*************************************************************************
	 * Local Component Generator Methods
	 * 
	 *************************************************************************/
	
	// Code generator methods -------------------------------------------------
	
	public String generateApplicationInterface()
	{
		return new HomeDefApplicationInterfaceTemplate().generate(this);
	}
		
	public String generateImplicitApplicationInterface()
	{
		return new HomeDefImplicitApplicationInterfaceTemplate().generate(this);
	}
	
	public String generateExplicitApplicationInterface()
	{
		return new HomeDefExplicitApplicationInterfaceTemplate().generate(this);
	}
	
	public String generateAdapterLocal()
	{              
		return new HomeDefAdapterLocalTemplate().generate(this);
	}
	
	public String generateDeploymentLocal()
	{              
		return new HomeDefDeploymentLocalTemplate().generate(this);
	}
	
	
	// Generate SourceFile objects --------------------------------------------
	
	public List generateLocalComponentSourceFiles()
	{
		List sourceFileList = new ArrayList();
		String localPackageName = Text.joinList(File.separator, getJavaNamespaceList());
		
		SourceFile applicationInterface = 
			new SourceFile(localPackageName, generateCcmIdentifier() + ".java", generateApplicationInterface());
		sourceFileList.add(applicationInterface);

		SourceFile implicitApplicationIface = 
			new SourceFile(localPackageName, generateCcmIdentifier() + "Implicit.java", generateImplicitApplicationInterface());
		sourceFileList.add(implicitApplicationIface);

		SourceFile explicitApplicationIface = 
			new SourceFile(localPackageName, generateCcmIdentifier() + "Explicit.java", generateExplicitApplicationInterface());
		sourceFileList.add(explicitApplicationIface);
		
		SourceFile adapterLocal = 
			new SourceFile(localPackageName, getIdentifier() + "Adapter.java", generateAdapterLocal());
		sourceFileList.add(adapterLocal);
		
		SourceFile deploymentLocal = 
			new SourceFile(localPackageName, getIdentifier() + "Deployment.java", generateDeploymentLocal());
		sourceFileList.add(deploymentLocal);
		
		return sourceFileList;
	}
	
	
	
	/*************************************************************************
	 * Application Generator Methods
	 * 
	 *************************************************************************/
	
	// Code generator methods -------------------------------------------------
	
	public String generateApplicationClass()
	{
		return new HomeDefApplicationClassTemplate().generate(this);
	}
	
	public String generateFactoryApplication()
	{
		return new HomeDefFactoryApplicationTemplate().generate(this);
	}
	
	
	// Generate SourceFile objects --------------------------------------------
	
	public List generateApplicationSourceFiles()
	{
		List sourceFileList = new ArrayList();
		String localPackageName = Text.joinList(File.separator, getJavaNamespaceList());
		
		SourceFile applicationClass = 
			new SourceFile(localPackageName, getIdentifier() + "Impl.java", generateApplicationClass());
		sourceFileList.add(applicationClass);
		
		SourceFile factoryApplication =
			new SourceFile(localPackageName, getIdentifier() + "Factory.java", generateFactoryApplication());
		sourceFileList.add(factoryApplication);
		
		return sourceFileList;
	}
	
	
	
	/*************************************************************************
	 * Client Library Generator Methods
	 * 
	 *************************************************************************/
	
	// Code generator methods -------------------------------------------------	
	
	public String generateAdapterToCorba()
	{
		return new HomeDefAdapterToCorbaTemplate().generate(this);
	}
			
	public String generateClientLibDeployment()
	{              
		return new HomeDefDeploymentClientLibTemplate().generate(this);
	}

	// Generate SourceFile objects --------------------------------------------
	
	public List generateClientLibSourceFiles()
	{
		List sourceFileList = new ArrayList();		
		String localPackageName = Text.joinList(File.separator, getJavaNamespaceList());
		
		SourceFile adapterToCorba = 
			new SourceFile(localPackageName,getIdentifier() + "AdapterToCorba.java",generateAdapterToCorba());
		sourceFileList.add(adapterToCorba);
		
		SourceFile deploymentClientLib = 
			new SourceFile(localPackageName,getIdentifier() + "ClientLibDeployment.java",generateClientLibDeployment());
		sourceFileList.add(deploymentClientLib);
		
		return sourceFileList;
	}	
}

