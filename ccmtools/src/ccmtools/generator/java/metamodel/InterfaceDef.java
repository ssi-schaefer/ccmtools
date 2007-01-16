package ccmtools.generator.java.metamodel;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import ccmtools.generator.java.templates.InterfaceDefAdapterFromCorbaTemplate;
import ccmtools.generator.java.templates.InterfaceDefAdapterLocalTemplate;
import ccmtools.generator.java.templates.InterfaceDefAdapterToCorbaTemplate;
import ccmtools.generator.java.templates.InterfaceDefApplicationInterfaceTemplate;
import ccmtools.generator.java.templates.InterfaceDefInterfaceTemplate;
import ccmtools.utils.SourceFile;
import ccmtools.utils.Text;

public class InterfaceDef
	extends 
        ModelElement
    implements 
        JavaLocalInterfaceGeneratorElement, 
        JavaLocalAdapterGeneratorElement,
        JavaCorbaAdapterGeneratorElement
{
	private List<InterfaceDef> baseInterfaces = new ArrayList<InterfaceDef>(); 
	private List<ConstantDef> constants = new ArrayList<ConstantDef>();
	private List<AttributeDef> attribute = new ArrayList<AttributeDef>();
	private List<OperationDef> operation = new ArrayList<OperationDef>();
	
	
	public InterfaceDef(String identifier, List<String> namespace)
	{
		super(identifier, namespace);
	}
	
	
	public List<ConstantDef> getConstants()
	{
		return constants;
	}
	
	public List<ConstantDef> getAllConstants()
	{
		List<ConstantDef> allConstants = new ArrayList<ConstantDef>();
		for(InterfaceDef iface : getBaseInterfaces())
		{
			allConstants.addAll(iface.getAllConstants());			
		}
		allConstants.addAll(getConstants());
		return allConstants;
	}
	
	
	public List<AttributeDef> getAttributes()
	{
		return attribute;
	}

	public List<AttributeDef> getAllAttributes()
	{
		List<AttributeDef> allAttributes = new ArrayList<AttributeDef>();
		for(InterfaceDef iface : getBaseInterfaces())
		{
			allAttributes.addAll(iface.getAllAttributes());
		}
		allAttributes.addAll(getAttributes());
		return allAttributes;
	}
	
	
	public List<OperationDef> getOperations()
	{
		return operation;
	}	
	
	public List<OperationDef> getAllOperations()
	{
		List<OperationDef> allOperations = new ArrayList<OperationDef>();
		for(InterfaceDef iface : getBaseInterfaces())
		{
			allOperations.addAll(iface.getAllOperations());
		}		
		allOperations.addAll(getOperations());
		return allOperations;
	}
	
	
	public List<InterfaceDef> getBaseInterfaces()
	{
		return baseInterfaces;
	}
			
	public Set<String> getJavaImportStatements()
	{
		Set<String> importStatements = new TreeSet<String>();
		for(AttributeDef attr : getAttributes())
		{
			importStatements.addAll(attr.getJavaImportStatements());
		}
		for(OperationDef op : getOperations())
		{
			importStatements.addAll(op.getJavaImportStatements());
		}		
		importStatements.add(generateAbsoluteJavaName());
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
		return new InterfaceDefInterfaceTemplate().generate(this);
	}
		
	public String generateBaseInterfaceDeclarations()
	{
		List<String> baseInterfaceList = new ArrayList<String>();
		for(InterfaceDef iface : getBaseInterfaces())
		{
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
	
	public List<SourceFile> generateLocalInterfaceSourceFiles()
	{
		List<SourceFile> sourceFileList = new ArrayList<SourceFile>();
		String localPackageName = Text.joinList(File.separator, getJavaNamespaceList());		
		SourceFile iface = new SourceFile(localPackageName, getIdentifier() + ".java", generateInterface());
		sourceFileList.add(iface);		
		return sourceFileList;
	}
	
	
	
	/*************************************************************************
	 * Local Adapter Generator Methods
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
	
	public List<SourceFile> generateLocalAdapterSourceFiles()
	{
		List<SourceFile> sourceFileList = new ArrayList<SourceFile>();
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
	 * CORBA Adapter Generator Methods
	 *************************************************************************/
		
	public String generateAdapterFromCorba()
	{
		return new InterfaceDefAdapterFromCorbaTemplate().generate(this);
	}
	
	public String generateAdapterToCorba()
	{
		return new InterfaceDefAdapterToCorbaTemplate().generate(this);
	}

	
	// Generate SourceFile objects --------------------------------------------
	
	public List<SourceFile> generateCorbaAdapterSourceFiles()
	{
		List<SourceFile> sourceFileList = new ArrayList<SourceFile>();
		String localPackageName = Text.joinList(File.separator, getJavaNamespaceList());
		String remotePackageName = Text.joinList(File.separator, getJavaRemoteNamespaceList());
		
		SourceFile adapterToCorba = new SourceFile(localPackageName, getIdentifier() + 
				"AdapterToCorba.java",generateAdapterToCorba());		
		sourceFileList.add(adapterToCorba);

		SourceFile adapterFromCorba = new SourceFile(remotePackageName, getIdentifier() + 
				"AdapterFromCorba.java",generateAdapterFromCorba());
		sourceFileList.add(adapterFromCorba);
		
		return sourceFileList;
	}
}
