package ccmtools.generator.java.metamodel;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import ccmtools.generator.java.templates.ComponentDefAdapterFromCorbaTemplate;
import ccmtools.generator.java.templates.ComponentDefAdapterLocalTemplate;
import ccmtools.generator.java.templates.ComponentDefAdapterToCorbaTemplate;
import ccmtools.generator.java.templates.ComponentDefApplicationClassTemplate;
import ccmtools.generator.java.templates.ComponentDefApplicationInterfaceTemplate;
import ccmtools.generator.java.templates.ComponentDefContextClassTemplate;
import ccmtools.generator.java.templates.ComponentDefContextInterfaceTemplate;
import ccmtools.generator.java.templates.ComponentDefInterfaceTemplate;
import ccmtools.parser.assembly.metamodel.Model;
import ccmtools.utils.SourceFile;
import ccmtools.utils.Text;

public class ComponentDef
	extends 
        ModelElement
    implements 
        JavaLocalInterfaceGeneratorElement, 
        JavaLocalAdapterGeneratorElement,
        JavaClientLibGeneratorElement,
        JavaCorbaAdapterGeneratorElement,
        JavaApplicationGeneratorElement
{
	private List<AttributeDef> attributes = new ArrayList<AttributeDef>();
	private List<ProvidesDef> facet = new ArrayList<ProvidesDef>();
	private List<UsesDef> receptacle = new ArrayList<UsesDef>();
	private List<SupportsDef> supports = new ArrayList<SupportsDef>();
	private JavaApplicationGeneratorElement home;
	
	
	public ComponentDef(String identifier, List<String> namespace)
	{
		super(identifier, namespace);
	}
	
	
	public List<AttributeDef> getAttributes()
	{
		return attributes;
	}
	
	public List<ProvidesDef> getFacet()
	{
		return facet;
	}
	
	public List<UsesDef> getReceptacle()
	{
		return receptacle;
	}
	
	public List<SupportsDef> getSupports()
	{
		return supports;
	}


	public JavaApplicationGeneratorElement getHome()
	{
		return home;
	}
	
	public void setHome(JavaApplicationGeneratorElement home)
	{
		this.home = home;
	}
	
	public Set<String> getJavaImportStatements()
	{
		Set<String> importStatements = new TreeSet<String>();
		// Each component class refers to its context object
		importStatements.add(generateAbsoluteJavaCcmName() + "_Context");
		for(AttributeDef a : getAttributes())
		{
			importStatements.addAll(a.getType().getJavaImportStatements());
		}
		for(SupportsDef s : getSupports())
		{
			importStatements.addAll(s.getJavaImportStatements());
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
		return new ComponentDefInterfaceTemplate().generate(this);
	}
	
	public String generateSupportsDeclarations()
	{
		List<String> supportsList = new ArrayList<String>();
		for(SupportsDef s : getSupports())
		{
			supportsList.add(s.getInterface().getIdentifier());
		}
		if(supportsList.size() > 0)
		{
			return "," + NL + TAB + Text.joinList( "," + NL + TAB, supportsList);
		}
		else
		{
			return ""; // no supported interfaces
		}
	}
	
	
	public String generateSupportsCcmDeclarations()
	{
		List<String> supportsList = new ArrayList<String>();
		for(SupportsDef s : getSupports())
		{
			supportsList.add(s.getInterface().generateCcmIdentifier());
		}
		if(supportsList.size() > 0)
		{
			return "," + NL + TAB + Text.joinList("," + NL + TAB, supportsList);
		}
		else
		{
			return ""; // no supported interfaces
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
	
	public String generateApplicationInterface()
	{
		return new ComponentDefApplicationInterfaceTemplate().generate(this);
	}	
		
	public String generateContextInterface()
	{
		return new ComponentDefContextInterfaceTemplate().generate(this);
	}
	
	public String generateContextClass()
	{
		return new ComponentDefContextClassTemplate().generate(this);
	}
	
	public String generateAdapterLocal()
	{              
		return new ComponentDefAdapterLocalTemplate().generate(this);
	}
	
	
	// Generate SourceFile objects --------------------------------------------
	
	public List<SourceFile> generateLocalAdapterSourceFiles()
	{
		List<SourceFile> sourceFileList = new ArrayList<SourceFile>();
		String localPackageName = Text.joinList(File.separator, getJavaNamespaceList());
		
		SourceFile applicationInterface = new SourceFile(localPackageName, 
				generateCcmIdentifier() + ".java", generateApplicationInterface());
		sourceFileList.add(applicationInterface);
		
		SourceFile contextInterface = new SourceFile(localPackageName, 
				generateCcmIdentifier() + "_Context.java", generateContextInterface());
		sourceFileList.add(contextInterface);

		SourceFile contextClass = new SourceFile(localPackageName, 
				generateCcmIdentifier() + "_ContextImpl.java", generateContextClass());
		sourceFileList.add(contextClass);
		
		SourceFile adapterLocal = new SourceFile(localPackageName, 
				getIdentifier() + "Adapter.java", generateAdapterLocal());
		sourceFileList.add(adapterLocal);
	
		return sourceFileList;
	}
	
	
	
	/*************************************************************************
	 * Application Generator Methods
	 *************************************************************************/
	
	public String generateApplicationClass()
	{
		return new ComponentDefApplicationClassTemplate().generate(this);
	}	
	
	
	// Generate SourceFile objects --------------------------------------------
	
	public List<SourceFile> generateApplicationSourceFiles()
	{
		List<SourceFile> sourceFileList = new ArrayList<SourceFile>();
		String localPackageName = Text.joinList(File.separator, getJavaNamespaceList());
		
		SourceFile applicationClass = new SourceFile(localPackageName, getIdentifier() + 
				"Impl.java", generateApplicationClass());		
		sourceFileList.add(applicationClass);
		
		return sourceFileList;
	}
    
    public List<SourceFile> generateAssemblySourceFiles(Model assemblies)
    {
        List<SourceFile> sourceFileList = new ArrayList<SourceFile>();
        // TODO
        return sourceFileList;
    }
	
	
	
	/*************************************************************************
	 * Client Library Generator Methods
	 *************************************************************************/
	
	public String generateAdapterToCorba()
	{
		return new ComponentDefAdapterToCorbaTemplate().generate(this);
	}
	
	
	// Generate SourceFile objects --------------------------------------------
	
	public List<SourceFile> generateClientLibSourceFiles()
	{
		List<SourceFile> sourceFileList = new ArrayList<SourceFile>();
		String localPackageName = Text.joinList(File.separator, getJavaNamespaceList());
		
		SourceFile adapterToCorba = new SourceFile(localPackageName, getIdentifier() + 
				"AdapterToCorba.java",generateAdapterToCorba());
		sourceFileList.add(adapterToCorba);
		
		return sourceFileList;
	}
	
	
	/*************************************************************************
	 * CORBA Adapter Generator Methods
	 *************************************************************************/
	
	public String generateAdapterFromCorba()
	{
		return new ComponentDefAdapterFromCorbaTemplate().generate(this);
	}
	
	
	// Generate SourceFile objects --------------------------------------------
	
	public List<SourceFile> generateCorbaAdapterSourceFiles()
	{
		List<SourceFile> sourceFileList = new ArrayList<SourceFile>();
		String remotePackageName = Text.joinList(File.separator, getJavaRemoteNamespaceList());
		
		SourceFile adapterToCorba = new SourceFile(remotePackageName, getIdentifier() + 
				"AdapterFromCorba.java",generateAdapterFromCorba());
		sourceFileList.add(adapterToCorba);
		
		return sourceFileList;
	}
}