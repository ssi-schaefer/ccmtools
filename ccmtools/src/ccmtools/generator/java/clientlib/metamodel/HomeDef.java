package ccmtools.generator.java.clientlib.metamodel;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import ccmtools.generator.java.clientlib.templates.HomeAdapterToCorbaTemplate;
import ccmtools.generator.java.clientlib.templates.HomeDeclarationTemplate;
import ccmtools.generator.java.clientlib.templates.HomeExplicitDeclarationTemplate;
import ccmtools.generator.java.clientlib.templates.HomeFactoryTemplate;
import ccmtools.generator.java.clientlib.templates.HomeImplicitDeclarationTemplate;
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
		
		SourceFile homeDeclaration = 
			new SourceFile(Text.joinList(File.separator, getJavaNamespaceList()), 
					getIdentifier() + ".java", generateHomeDeclaration());
		
		SourceFile homeImplicit = 
			new SourceFile(Text.joinList(File.separator, getJavaNamespaceList()), 
					getIdentifier() + "Implicit.java", generateHomeImplicitDeclaration());

		SourceFile homeExplicit = 
			new SourceFile(Text.joinList(File.separator, getJavaNamespaceList()), 
					getIdentifier() + "Explicit.java",generateHomeExplicitDeclaration());
		
		SourceFile homeAdapterToCorba = 
			new SourceFile(Text.joinList(File.separator, getJavaRemoteNamespaceList()), 
					getIdentifier() + "AdapterToCorba.java",generateHomeAdapterToCorba());
		
		SourceFile homeFactory = 
			new SourceFile(Text.joinList(File.separator, getJavaNamespaceList()),
					getIdentifier() + "Factory.java",generateHomeFactory());
		
		sourceFileList.add(homeDeclaration);
		sourceFileList.add(homeImplicit);
		sourceFileList.add(homeExplicit);
		sourceFileList.add(homeAdapterToCorba);
		sourceFileList.add(homeFactory);
		return sourceFileList;
	}
}

