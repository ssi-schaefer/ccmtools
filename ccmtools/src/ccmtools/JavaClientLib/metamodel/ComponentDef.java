package ccmtools.JavaClientLib.metamodel;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import ccmtools.JavaClientLib.templates.ComponentAdapterToCorbaTemplate;
import ccmtools.JavaClientLib.templates.ComponentDeclarationTemplate;
import ccmtools.utils.Text;

public class ComponentDef
	extends ModelElement
{
	public ComponentDef(String identifier, List namespace)
	{
		super(identifier, namespace);
	}
	
	
	// Code generator methods -------------------------------------------------	
	
	public String generateComponentDeclaration()
	{
		return new ComponentDeclarationTemplate().generate(this);
	}
	
	public String generateComponentAdapterToCorba()
	{
		return new ComponentAdapterToCorbaTemplate().generate(this);
	}
	
	
	// Generate SourceFile objects --------------------------------------------
	
	public List generateSourceFiles()
	{
		List sourceFileList = new ArrayList();
		String packages = Text.joinList(File.separator, getJavaNamespace());
		
		SourceFile componentDeclaration = 
			new SourceFile(packages, getIdentifier() + ".java", generateComponentDeclaration());
		
		SourceFile componentAdapterToCorba = 
			new SourceFile(packages, getIdentifier() + "AdapterToCorba.java",generateComponentAdapterToCorba());
		
		sourceFileList.add(componentDeclaration);
		sourceFileList.add(componentAdapterToCorba);
		return sourceFileList;
	}	
}
