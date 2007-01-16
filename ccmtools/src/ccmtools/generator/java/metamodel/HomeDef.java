package ccmtools.generator.java.metamodel;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import ccmtools.generator.java.templates.HomeDefAdapterFromCorbaTemplate;
import ccmtools.generator.java.templates.HomeDefAdapterLocalTemplate;
import ccmtools.generator.java.templates.HomeDefAdapterToCorbaTemplate;
import ccmtools.generator.java.templates.HomeDefApplicationClassTemplate;
import ccmtools.generator.java.templates.HomeDefApplicationInterfaceTemplate;
import ccmtools.generator.java.templates.HomeDefDeploymentClientLibTemplate;
import ccmtools.generator.java.templates.HomeDefDeploymentCorbaComponentTemplate;
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
	extends 
        ModelElement 
    implements 
        JavaLocalInterfaceGeneratorElement, 
        JavaLocalAdapterGeneratorElement,
        JavaClientLibGeneratorElement,
        JavaCorbaAdapterGeneratorElement,
        JavaApplicationGeneratorElement
{
	private ComponentDef component;
	
	public HomeDef(String identifier, List<String> namespace)
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
		
	public Set<String> getJavaImportStatements()
	{
		Set<String> importStatements = new TreeSet<String>();
		// Some component management methods can throw this exception type
		importStatements.add(generateAbsoluteJavaName());
		importStatements.add(generateAbsoluteJavaCcmName());
		importStatements.add(getComponent().generateAbsoluteJavaName());
		return importStatements;
	}
	
	
	/*************************************************************************
	 * Local Interface Generator Methods
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
	
	public List<SourceFile> generateLocalInterfaceSourceFiles()
	{
		List<SourceFile> sourceFileList = new ArrayList<SourceFile>();
		String localPackageName = Text.joinList(File.separator, getJavaNamespaceList());
		
		SourceFile iface = new SourceFile(localPackageName, getIdentifier() + ".java", generateInterface());
		sourceFileList.add(iface);
		
		SourceFile implicitInterface = new SourceFile(localPackageName, getIdentifier() + "Implicit.java", 
                generateImplicitInterface());
		sourceFileList.add(implicitInterface);
		
		SourceFile explicitInterface = new SourceFile(localPackageName, getIdentifier() + "Explicit.java", 
                generateExplicitInterface());
		sourceFileList.add(explicitInterface);
				
		return sourceFileList;
	}
	
	
	
	/*************************************************************************
	 * Local Adapter Generator Methods
	 *************************************************************************/
	
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
	
	public List<SourceFile> generateLocalAdapterSourceFiles()
	{
		List<SourceFile> sourceFileList = new ArrayList<SourceFile>();
		String localPackageName = Text.joinList(File.separator, getJavaNamespaceList());
		
		SourceFile applicationInterface = new SourceFile(localPackageName, 
				generateCcmIdentifier() + ".java", generateApplicationInterface());
		sourceFileList.add(applicationInterface);

		SourceFile implicitApplicationIface = new SourceFile(localPackageName, 
				generateCcmIdentifier() + "Implicit.java", generateImplicitApplicationInterface());
		sourceFileList.add(implicitApplicationIface);

		SourceFile explicitApplicationIface = new SourceFile(localPackageName, 
				generateCcmIdentifier() + "Explicit.java", generateExplicitApplicationInterface());
		sourceFileList.add(explicitApplicationIface);
		
		SourceFile adapterLocal = new SourceFile(localPackageName, 
				getIdentifier() + "Adapter.java", generateAdapterLocal());
		sourceFileList.add(adapterLocal);
		
		SourceFile deploymentLocal = new SourceFile(localPackageName, 
				getIdentifier() + "Deployment.java", generateDeploymentLocal());
		sourceFileList.add(deploymentLocal);
		
		return sourceFileList;
	}
	
	
	
	/*************************************************************************
	 * Application Generator Methods
	 *************************************************************************/
	
	public String generateApplicationClass()
	{
		return new HomeDefApplicationClassTemplate().generate(this);
	}
	
	public String generateFactoryApplication()
	{
		return new HomeDefFactoryApplicationTemplate().generate(this);
	}
	
	
	// Generate SourceFile objects --------------------------------------------
	
	public List<SourceFile> generateApplicationSourceFiles()
	{
		List<SourceFile> sourceFileList = new ArrayList<SourceFile>();
		String localPackageName = Text.joinList(File.separator, getJavaNamespaceList());
		
		SourceFile applicationClass = new SourceFile(localPackageName, getIdentifier() + 
				"Impl.java", generateApplicationClass());
		sourceFileList.add(applicationClass);
		
		SourceFile factoryApplication = new SourceFile(localPackageName, getIdentifier() + 
				"Factory.java", generateFactoryApplication());
		sourceFileList.add(factoryApplication);
		
		return sourceFileList;
	}
	
	
	
	/*************************************************************************
	 * Client Library Generator Methods
	 *************************************************************************/
		
	public String generateAdapterToCorba()
	{
		return new HomeDefAdapterToCorbaTemplate().generate(this);
	}
			
	public String generateClientLibDeployment()
	{              
		return new HomeDefDeploymentClientLibTemplate().generate(this);
	}

	// Generate SourceFile objects --------------------------------------------
	
	public List<SourceFile> generateClientLibSourceFiles()
	{
		List<SourceFile> sourceFileList = new ArrayList<SourceFile>();		
		String localPackageName = Text.joinList(File.separator, getJavaNamespaceList());
		
		SourceFile adapterToCorba = new SourceFile(localPackageName, getIdentifier() + 
				"AdapterToCorba.java",generateAdapterToCorba());
		sourceFileList.add(adapterToCorba);
		
		SourceFile deploymentClientLib = new SourceFile(localPackageName, getIdentifier() + 
				"ClientLibDeployment.java",generateClientLibDeployment());
		sourceFileList.add(deploymentClientLib);
		
		return sourceFileList;
	}	

	

	/*************************************************************************
	 * CORBA Adapter Generator Methods
	 *************************************************************************/
		
	public String generateAdapterFromCorba()
	{
		return new HomeDefAdapterFromCorbaTemplate().generate(this);
	}
			
	public String generateCorbaComponentDeployment()
	{              
		return new HomeDefDeploymentCorbaComponentTemplate().generate(this);
	}
	
	// Generate SourceFile objects --------------------------------------------
	
	public List<SourceFile> generateCorbaAdapterSourceFiles()
	{
		List<SourceFile> sourceFileList = new ArrayList<SourceFile>();		
		String remotePackageName = Text.joinList(File.separator, getJavaRemoteNamespaceList());
		
		SourceFile adapterFromCorba = new SourceFile(remotePackageName, getIdentifier() + 
				"AdapterFromCorba.java",generateAdapterFromCorba());
		sourceFileList.add(adapterFromCorba);
		
		SourceFile deploymentCorbaComponent = new SourceFile(remotePackageName, getIdentifier() + 
				"Deployment.java",generateCorbaComponentDeployment());
		sourceFileList.add(deploymentCorbaComponent);
		
		return sourceFileList;
	}		
}

