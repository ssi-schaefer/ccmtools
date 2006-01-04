package ccmtools.JavaClientLib.metamodel;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import ccmtools.JavaClientLib.templates.HomeAdapterToCorbaTemplate;
import ccmtools.JavaClientLib.templates.HomeDeclarationTemplate;
import ccmtools.JavaClientLib.templates.HomeExplicitDeclarationTemplate;
import ccmtools.JavaClientLib.templates.HomeFactoryTemplate;
import ccmtools.JavaClientLib.templates.HomeImplicitDeclarationTemplate;
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
	
	public List generateSourceFiles()
	{
		List sourceFileList = new ArrayList();
		String packages = Text.joinList(File.separator, getJavaNamespace());
		
		SourceFile homeDeclaration = 
			new SourceFile(packages, getIdentifier() + ".java", generateHomeDeclaration());
		
		SourceFile homeImplicit = 
			new SourceFile(packages, getIdentifier() + "Implicit.java", generateHomeImplicitDeclaration());

		SourceFile homeExplicit = 
			new SourceFile(packages, getIdentifier() + "Explicit.java",generateHomeExplicitDeclaration());
		
		SourceFile homeAdapterToCorba = 
			new SourceFile(packages, getIdentifier() + "AdapterToCorba.java",generateHomeAdapterToCorba());
		
		SourceFile homeFactory = 
			new SourceFile(packages,getIdentifier() + "Factory.java",generateHomeFactory());
		
		sourceFileList.add(homeDeclaration);
		sourceFileList.add(homeImplicit);
		sourceFileList.add(homeExplicit);
		sourceFileList.add(homeAdapterToCorba);
		sourceFileList.add(homeFactory);
		return sourceFileList;
	}
}

