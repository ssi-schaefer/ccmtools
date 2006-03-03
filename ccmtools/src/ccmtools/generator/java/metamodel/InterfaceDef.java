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
	
	public List getAllConstants()
	{
		List allConstants = new ArrayList();
		for(Iterator i = getBaseInterfaces().iterator(); i.hasNext();)
		{
			InterfaceDef iface = (InterfaceDef)i.next();
			allConstants.addAll(iface.getAllConstants());			
		}
		allConstants.addAll(getConstants());
		return allConstants;
	}
	
	
	public List getAttributes()
	{
		return attribute;
	}

	public List getAllAttributes()
	{
		List allAttributes = new ArrayList();
		for(Iterator i = getBaseInterfaces().iterator(); i.hasNext();)
		{
			InterfaceDef iface = (InterfaceDef)i.next();
			allAttributes.addAll(iface.getAllAttributes());
		}
		allAttributes.addAll(getAttributes());
		return allAttributes;
	}
	
	
	public List getOperations()
	{
		return operation;
	}	
	
	public List getAllOperations()
	{
		List allOperations = new ArrayList();
		for(Iterator i = getBaseInterfaces().iterator(); i.hasNext();)
		{
			InterfaceDef iface = (InterfaceDef)i.next();
			allOperations.addAll(iface.getAllOperations());
		}		
		allOperations.addAll(getOperations());
		return allOperations;
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
	
	public String generateInterface()
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
		
		SourceFile iface = 
			new SourceFile(localPackageName, getIdentifier() + ".java", generateInterface());
		sourceFileList.add(iface);
		
		return sourceFileList;
	}
	
	
	
	/*************************************************************************
	 * Local Component Generator Methods
	 * 
	 *************************************************************************/
	
	// Code generator methods -------------------------------------------------
	
	public String generateApplicationInterface()
	{
		return new InterfaceDefApplicationInterfaceTemplate().generate(this);
	}
	
	public String generateAdapterLocal()
	{              
		return new InterfaceDefAdapterLocalTemplate().generate(this);
	}
	
	
	// Generate SourceFile objects --------------------------------------------
	
	public List generateLocalComponentSourceFiles()
	{
		List sourceFileList = new ArrayList();
		String localPackageName = Text.joinList(File.separator, getJavaNamespaceList());
		
		SourceFile applicationInterface = 
			new SourceFile(localPackageName, generateCcmIdentifier() + ".java", generateApplicationInterface());
		sourceFileList.add(applicationInterface);
		
		SourceFile applicationLocal = 
			new SourceFile(localPackageName, getIdentifier() + "Adapter.java", generateAdapterLocal());
		sourceFileList.add(applicationLocal);
		
		return sourceFileList;
	}
	
	
	
	/*************************************************************************
	 * Client Library Generator Methods
	 * 
	 *************************************************************************/
	
	// Code generator methods -------------------------------------------------
	
	public String generateAdapterFromCorba()
	{
		return new InterfaceDefAdapterFromCorbaTemplate().generate(this);
	}
	
	public String generateAdapterToCorba()
	{
		return new InterfaceDefAdapterToCorbaTemplate().generate(this);
	}

	
	// Generate SourceFile objects --------------------------------------------
	
	public List generateClientLibSourceFiles()
	{
		List sourceFileList = new ArrayList();
		String localPackageName = Text.joinList(File.separator, getJavaNamespaceList());
		String remotePackageName = Text.joinList(File.separator, getJavaRemoteNamespaceList());
		
		SourceFile adapterToCorba = 
			new SourceFile(localPackageName, getIdentifier() + "AdapterToCorba.java",generateAdapterToCorba());		
		sourceFileList.add(adapterToCorba);

		SourceFile adapterFromCorba = 
			new SourceFile(remotePackageName, getIdentifier() + "AdapterFromCorba.java",generateAdapterFromCorba());
		sourceFileList.add(adapterFromCorba);
		
		return sourceFileList;
	}
}
