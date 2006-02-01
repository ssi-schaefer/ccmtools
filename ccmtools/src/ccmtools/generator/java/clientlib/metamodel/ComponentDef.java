package ccmtools.generator.java.clientlib.metamodel;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import ccmtools.generator.java.clientlib.templates.ComponentAdapterToCorbaTemplate;
import ccmtools.generator.java.clientlib.templates.ComponentDeclarationTemplate;
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
	
	
	// Code generator methods -------------------------------------------------	

	public String generateComponentDeclaration()
	{
		return new ComponentDeclarationTemplate().generate(this);
	}
	
	public String generateComponentAdapterToCorba()
	{
		return new ComponentAdapterToCorbaTemplate().generate(this);
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
	
	public List generateClientLibSourceFiles()
	{
		List sourceFileList = new ArrayList();
		String packages = Text.joinList(File.separator, getJavaNamespaceList());
		
		SourceFile componentDeclaration = 
			new SourceFile(packages, getIdentifier() + ".java", generateComponentDeclaration());
		
		SourceFile componentAdapterToCorba = 
			new SourceFile(packages, getIdentifier() + "AdapterToCorba.java",generateComponentAdapterToCorba());
		
		sourceFileList.add(componentDeclaration);
		sourceFileList.add(componentAdapterToCorba);
		return sourceFileList;
	}	
}
