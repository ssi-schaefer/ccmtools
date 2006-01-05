package ccmtools.JavaClientLib.metamodel;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import ccmtools.JavaClientLib.templates.InterfaceAdapterFromCorbaTemplate;
import ccmtools.JavaClientLib.templates.InterfaceAdapterToCorbaTemplate;
import ccmtools.JavaClientLib.templates.InterfaceDeclarationTemplate;
import ccmtools.utils.Text;

public class InterfaceDef
	extends ModelElement
{
	private List attribute = new ArrayList();
	private List operation = new ArrayList();

	
	public InterfaceDef(String identifier, List namespace)
	{
		super(identifier, namespace);
	}
	
		
	public List getAttribute()
	{
		return attribute;
	}

	public List getOperation()
	{
		return operation;
	}	
	
	
	// Code generator methods -------------------------------------------------
	
	public String generateInterfaceDeclaration()
	{
		return new InterfaceDeclarationTemplate().generate(this);
	}
	
	public String generateInterfaceAdapterFromCorba()
	{
		return new InterfaceAdapterFromCorbaTemplate().generate(this);
	}
	
	public String generateInterfaceAdapterToCorba()
	{
		return new InterfaceAdapterToCorbaTemplate().generate(this);
	}

	
	// Generate SourceFile objects --------------------------------------------
	
	public List generateSourceFiles()
	{
		List sourceFileList = new ArrayList();
		String packages = Text.joinList(File.separator, getJavaNamespaceList());
		
		SourceFile interfaceDeclaration = 
			new SourceFile(packages, getIdentifier() + ".java", generateInterfaceDeclaration());
		
		SourceFile interfaceAdapterToCorba = 
			new SourceFile(packages, getIdentifier() + "AdapterToCorba.java",generateInterfaceAdapterToCorba());
		
		SourceFile interfaceAdapterFromCorba = 
			new SourceFile(packages, getIdentifier() + "AdapterFromCorba.java",generateInterfaceAdapterFromCorba());
		
		sourceFileList.add(interfaceDeclaration);
		sourceFileList.add(interfaceAdapterToCorba);
		sourceFileList.add(interfaceAdapterFromCorba);
		return sourceFileList;
	}	
}
