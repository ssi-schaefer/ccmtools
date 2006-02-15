package ccmtools.generator.java.metamodel;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import ccmtools.generator.java.templates.InterfaceDefAdapterFromCorbaTemplate;
import ccmtools.generator.java.templates.InterfaceDefAdapterLocalTemplate;
import ccmtools.generator.java.templates.InterfaceDefAdapterToCorbaTemplate;
import ccmtools.generator.java.templates.InterfaceDefApplicationInterfaceTemplate;
import ccmtools.generator.java.templates.InterfaceDefInterfaceTemplate;
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
			
	
	
	/*************************************************************************
	 * Local Interface Generator Methods
	 * 
	 *************************************************************************/
	
	// Code generator methods -------------------------------------------------
	
	public String generateInterfaceDefInterface()
	{
		return new InterfaceDefInterfaceTemplate().generate(this);
	}
		
	public String generateBaseInterfaceDeclarations()
	{
		List baseInterfaceList = new ArrayList();
		for(Iterator i=getBaseInterfaces().iterator(); i.hasNext();)
		{
			InterfaceDef iface = (InterfaceDef)i.next();
			baseInterfaceList.add(iface.generateAbsoluteJavaName());
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
		
	
	// Generate SourceFile objects --------------------------------------------
	
	public List generateLocalInterfaceSourceFiles()
	{
		List sourceFileList = new ArrayList();
		String localPackageName = Text.joinList(File.separator, getJavaNamespaceList());
		
		SourceFile interfaceDeclaration = 
			new SourceFile(localPackageName, getIdentifier() + ".java", generateInterfaceDefInterface());
		
		sourceFileList.add(interfaceDeclaration);
		return sourceFileList;
	}
	
	
	
	/*************************************************************************
	 * Local Component Generator Methods
	 * 
	 *************************************************************************/
	
	// Code generator methods -------------------------------------------------
	
	public String generateInterfaceDefApplicationInterface()
	{
		return new InterfaceDefApplicationInterfaceTemplate().generate(this);
	}
	
	public String generateInterfaceDefAdapterLocal()
	{              
		return new InterfaceDefAdapterLocalTemplate().generate(this);
	}
	
	
	// Generate SourceFile objects --------------------------------------------
	
	public List generateLocalComponentSourceFiles()
	{
		List sourceFileList = new ArrayList();
		String localPackageName = Text.joinList(File.separator, getJavaNamespaceList());
		
		SourceFile ccmInterfaceDeclaration = 
			new SourceFile(localPackageName, generateCcmIdentifier() + ".java", generateInterfaceDefApplicationInterface());
		SourceFile ccmInterfaceAdapter = 
			new SourceFile(localPackageName, getIdentifier() + "Adapter.java", generateInterfaceDefAdapterLocal());
		
		sourceFileList.add(ccmInterfaceDeclaration);
		sourceFileList.add(ccmInterfaceAdapter);

		return sourceFileList;
	}
	
	
	
	/*************************************************************************
	 * Client Library Generator Methods
	 * 
	 *************************************************************************/
	
	// Code generator methods -------------------------------------------------
	
	public String generateInterfaceDefAdapterFromCorba()
	{
		return new InterfaceDefAdapterFromCorbaTemplate().generate(this);
	}
	
	public String generateInterfaceDefAdapterToCorba()
	{
		return new InterfaceDefAdapterToCorbaTemplate().generate(this);
	}

	
	// Generate SourceFile objects --------------------------------------------
	
	public List generateClientLibSourceFiles()
	{
		List sourceFileList = new ArrayList();
		
		String remotePackageName = Text.joinList(File.separator, getJavaRemoteNamespaceList());
		SourceFile interfaceAdapterToCorba = 
			new SourceFile(remotePackageName, getIdentifier() + "AdapterToCorba.java",generateInterfaceDefAdapterToCorba());		
		SourceFile interfaceAdapterFromCorba = 
			new SourceFile(remotePackageName, getIdentifier() + "AdapterFromCorba.java",generateInterfaceDefAdapterFromCorba());
		
		sourceFileList.add(interfaceAdapterToCorba);
		sourceFileList.add(interfaceAdapterFromCorba);
		return sourceFileList;
	}
}
