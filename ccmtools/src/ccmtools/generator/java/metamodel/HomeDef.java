package ccmtools.generator.java.metamodel;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import ccmtools.generator.java.templates.CcmHomeDeclarationTemplate;
import ccmtools.generator.java.templates.CcmHomeExplicitDeclarationTemplate;
import ccmtools.generator.java.templates.CcmHomeFactoryTemplate;
import ccmtools.generator.java.templates.CcmHomeImplementationTemplate;
import ccmtools.generator.java.templates.CcmHomeImplicitDeclarationTemplate;
import ccmtools.generator.java.templates.HomeAdapterToCorbaTemplate;
import ccmtools.generator.java.templates.HomeDeclarationTemplate;
import ccmtools.generator.java.templates.HomeExplicitDeclarationTemplate;
import ccmtools.generator.java.templates.HomeFactoryTemplate;
import ccmtools.generator.java.templates.HomeImplicitDeclarationTemplate;
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
	
	
	
	/**
	 * Java Local Interface Generator
	 * 
	 */
	
	// Code generator methods -------------------------------------------------
	
	public String generateHomeDeclaration()
	{
		return new HomeDeclarationTemplate().generate(this);
	}
		
	public String generateHomeImplicitDeclaration()
	{
		return new HomeImplicitDeclarationTemplate().generate(this);
	}
	
	public String generateHomeExplicitDeclaration()
	{
		return new HomeExplicitDeclarationTemplate().generate(this);
	}
	
	
	// Generate SourceFile objects --------------------------------------------
	
	public List generateLocalInterfaceSourceFiles()
	{
		List sourceFileList = new ArrayList();
		String localPackageName = Text.joinList(File.separator, getJavaNamespaceList());
		
		SourceFile homeDeclaration = 
			new SourceFile(localPackageName, getIdentifier() + ".java", generateHomeDeclaration());
		
		SourceFile homeImplicitDeclaration = 
			new SourceFile(localPackageName, getIdentifier() + "Implicit.java", generateHomeImplicitDeclaration());
		
		SourceFile homeExplicitDeclaration = 
			new SourceFile(localPackageName, getIdentifier() + "Explicit.java", generateHomeExplicitDeclaration());
				
		sourceFileList.add(homeDeclaration);
		sourceFileList.add(homeImplicitDeclaration);
		sourceFileList.add(homeExplicitDeclaration);
		return sourceFileList;
	}
	
	
	
	/**
	 * Java Local Component Generator
	 * 
	 */
	
	// Code generator methods -------------------------------------------------
	
	public String generateCcmHomeDeclaration()
	{
		return new CcmHomeDeclarationTemplate().generate(this);
	}
		
	public String generateCcmHomeImplicitDeclaration()
	{
		return new CcmHomeImplicitDeclarationTemplate().generate(this);
	}
	
	public String generateCcmHomeExplicitDeclaration()
	{
		return new CcmHomeExplicitDeclarationTemplate().generate(this);
	}
	
	
	// Generate SourceFile objects --------------------------------------------
	
	public List generateLocalComponentSourceFiles()
	{
		List sourceFileList = new ArrayList();
		String localPackageName = Text.joinList(File.separator, getJavaNamespaceList());
		
		SourceFile ccmHomeDeclaration = 
			new SourceFile(localPackageName, getCcmIdentifier() + ".java", generateCcmHomeDeclaration());
		SourceFile ccmHomeImplicitDeclaration = 
			new SourceFile(localPackageName, getCcmIdentifier() + "Implicit.java", generateCcmHomeImplicitDeclaration());
		SourceFile ccmHomeExplicitDeclaration = 
			new SourceFile(localPackageName, getCcmIdentifier() + "Explicit.java", generateCcmHomeExplicitDeclaration());
		
		sourceFileList.add(ccmHomeDeclaration);
		sourceFileList.add(ccmHomeImplicitDeclaration);
		sourceFileList.add(ccmHomeExplicitDeclaration);
		return sourceFileList;
	}
	
	
	
	/**
	 * Java Local Implementation Generator
	 * 
	 */
	
	// Code generator methods -------------------------------------------------
	
	public String generateCcmHomeImplementation()
	{
		return new CcmHomeImplementationTemplate().generate(this);
	}
	
	public String generateCcmHomeFactory()
	{
		return new CcmHomeFactoryTemplate().generate(this);
	}
	
	// Generate SourceFile objects --------------------------------------------
	
	public List generateLocalImplementationSourceFiles()
	{
		List sourceFileList = new ArrayList();
		String localPackageName = Text.joinList(File.separator, getJavaNamespaceList());
		
		SourceFile ccmHomeImplementation = 
			new SourceFile(localPackageName, getIdentifier() + "Impl.java", generateCcmHomeImplementation());
		SourceFile ccmHomeFactory =
			new SourceFile(localPackageName, getIdentifier() + "Factory.java", generateCcmHomeFactory());
		
		sourceFileList.add(ccmHomeImplementation);
		sourceFileList.add(ccmHomeFactory);
		return sourceFileList;
	}
	
	
	
	
	/**
	 * Java Client Library Generator
	 * 
	 */
	
	// Code generator methods -------------------------------------------------	
	
	public String generateHomeAdapterToCorba()
	{
		return new HomeAdapterToCorbaTemplate().generate(this);
	}
		
	public String generateHomeFactory()
	{
		return new HomeFactoryTemplate().generate(this);
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
			new SourceFile(localPackageName,getIdentifier() + "Factory.java",generateHomeFactory());
		
		sourceFileList.add(homeAdapterToCorba);
		sourceFileList.add(homeFactory);
		return sourceFileList;
	}	
}

