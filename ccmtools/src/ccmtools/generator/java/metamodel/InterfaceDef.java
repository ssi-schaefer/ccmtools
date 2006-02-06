package ccmtools.generator.java.metamodel;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import ccmtools.generator.java.templates.CcmInterfaceDeclarationTemplate;
import ccmtools.generator.java.templates.InterfaceAdapterFromCorbaTemplate;
import ccmtools.generator.java.templates.InterfaceAdapterToCorbaTemplate;
import ccmtools.generator.java.templates.InterfaceDeclarationTemplate;
import ccmtools.utils.SourceFile;
import ccmtools.utils.Text;

public class InterfaceDef
	extends ModelElement
{
	private List baseInterfaces = new ArrayList(); 
	private List constants = new ArrayList();
	private List attribute = new ArrayList();
	private List operation = new ArrayList();
	
	
	public InterfaceDef(String identifier, List namespace)
	{
		super(identifier, namespace);
	}
	
	
	public List getConstants()
	{
		return constants;
	}
			
	public List getAttributes()
	{
		return attribute;
	}

	public List getOperation()
	{
		return operation;
	}	
	
	public List getBaseInterfaces()
	{
		return baseInterfaces;
	}
			
	
	/**
	 * Java Client Library Generator
	 * 
	 */
	
	// Code generator methods -------------------------------------------------

	public String generateInterfaceDeclaration()
	{
		return new InterfaceDeclarationTemplate().generate(this);
	}
		
	public String generateBaseInterfaceDeclarations()
	{
		List baseInterfaceList = new ArrayList();
		for(Iterator i=getBaseInterfaces().iterator(); i.hasNext();)
		{
			InterfaceDef iface = (InterfaceDef)i.next();
			baseInterfaceList.add(iface.getAbsoluteJavaName());
		}
		if(baseInterfaceList.size() > 0)
		{
			return "extends " + Text.joinList(", ", baseInterfaceList);
		}
		else
		{
			return ""; // no base interfaces
		}
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
	
	public List generateClientLibSourceFiles()
	{
		List sourceFileList = new ArrayList();
		
		SourceFile interfaceDeclaration = 
			new SourceFile(Text.joinList(File.separator, getJavaNamespaceList()), 
					getIdentifier() + ".java", generateInterfaceDeclaration());
		
		SourceFile interfaceAdapterToCorba = 
			new SourceFile(Text.joinList(File.separator, getJavaRemoteNamespaceList()), 
					getIdentifier() + "AdapterToCorba.java",generateInterfaceAdapterToCorba());
		
		SourceFile interfaceAdapterFromCorba = 
			new SourceFile(Text.joinList(File.separator, getJavaRemoteNamespaceList()), 
					getIdentifier() + "AdapterFromCorba.java",generateInterfaceAdapterFromCorba());
		
		sourceFileList.add(interfaceDeclaration);
		sourceFileList.add(interfaceAdapterToCorba);
		sourceFileList.add(interfaceAdapterFromCorba);
		return sourceFileList;
	}
	
	
	/**
	 * Java Local Component Generator
	 * 
	 */
	
	// Code generator methods -------------------------------------------------
	
	public String generateCcmInterfaceDeclaration()
	{
		return new CcmInterfaceDeclarationTemplate().generate(this);
	}
	
	
	// Generate SourceFile objects --------------------------------------------
	
	public List generateLocalSourceFiles()
	{
		List sourceFileList = new ArrayList();
		String localPackageName = Text.joinList(File.separator, getJavaNamespaceList());
		
		SourceFile ccmInterfaceDeclaration = 
			new SourceFile(localPackageName, getCcmIdentifier() + ".java", generateCcmInterfaceDeclaration());
	
		
		
		sourceFileList.add(ccmInterfaceDeclaration);
		return sourceFileList;
	}
}
