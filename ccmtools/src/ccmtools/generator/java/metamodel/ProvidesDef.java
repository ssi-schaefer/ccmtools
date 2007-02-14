package ccmtools.generator.java.metamodel;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import ccmtools.generator.java.templates.ProvidesDefApplicationClassTemplate;
import ccmtools.generator.java.templates.ProvidesDefEquivalentMethodAdapterFromCorbaTemplate;
import ccmtools.generator.java.templates.ProvidesDefEquivalentMethodAdapterLocalTemplate;
import ccmtools.generator.java.templates.ProvidesDefEquivalentMethodAdapterToCorbaTemplate;
import ccmtools.generator.java.templates.ProvidesDefGetMethodImplementationTemplate;
import ccmtools.generator.java.templates.ProvidesDefNavigationMethodAdapterFromCorbaTemplate;
import ccmtools.generator.java.templates.ProvidesDefNavigationMethodAdapterLocalTemplate;
import ccmtools.generator.java.templates.ProvidesDefNavigationMethodAdapterToCorbaTemplate;
import ccmtools.parser.assembly.metamodel.Model;
import ccmtools.utils.SourceFile;
import ccmtools.utils.Text;

public class ProvidesDef
    extends ModelElement
    implements JavaApplicationGeneratorElement
{
	private ComponentDef component;
	private InterfaceDef iface;
	
	public ProvidesDef(String identifier, List<String> namespace)
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


	public InterfaceDef getInterface()
	{
		return iface;
	}

	public void setInterface(InterfaceDef provides)
	{
		this.iface = provides;
	}
		
	public Set<String> getJavaImportStatements()
	{
		Set<String> importStatements = getInterface().getJavaImportStatements();
		importStatements.add(getInterface().generateAbsoluteJavaCcmName());
		importStatements.add(getInterface().generateAbsoluteJavaName());
		importStatements.add(getComponent().generateAbsoluteJavaName() + "Impl");
		importStatements.add("Components.CCMException");
		return importStatements;
	}
	
	
	
	/*************************************************************************
	 * Local Interface Generator
	 *************************************************************************/
	
	public String generateJavaImportStatements()
	{
		return generateJavaImportStatements(getJavaImportStatements());
	}
	
	public String generateJavaImportStatements(String namespace)
	{
		return generateJavaImportStatements(namespace, getJavaImportStatements());
	}
	
	public String generateEquivalentMethodDeclaration()
	{
		return TAB + getInterface().generateAbsoluteJavaName() + " provide_" + getIdentifier() + "();\n";
	}
		
	
	
	/*************************************************************************
	 * Local Adapter Generator
	 *************************************************************************/
		
	public String generateFacetAdapterReference()
	{
		return TAB + "private " + getInterface().generateAbsoluteJavaName() + 
				" " + getIdentifier() + "FacetAdapter;";
	}
	
	public String generateEquivalentMethodAdapterLocal()
	{
		return new ProvidesDefEquivalentMethodAdapterLocalTemplate().generate(this);
	}
	
	public String generateNavigationMethodAdapterLocal()
	{		
		return new ProvidesDefNavigationMethodAdapterLocalTemplate().generate(this);
	}
	
	
	
	
	/*************************************************************************
	 * Application Generator Methods
	 *************************************************************************/
	
	// Code generator methods -------------------------------------------------

	public String generateEquivalentApplicationMethodDeclaration()
	{
		return TAB + getInterface().generateAbsoluteJavaCcmName() + " get_" + getIdentifier() + "();\n";
	}
	
	public String generateGetMethodImplementation()
	{
		return new ProvidesDefGetMethodImplementationTemplate().generate(this);
	}
	
	public String generateApplicationClass()
	{
		return new ProvidesDefApplicationClassTemplate().generate(this);
	}
	
	
	// Generate SourceFile objects --------------------------------------------
	
	public List<SourceFile> generateApplicationSourceFiles()
	{
		List<SourceFile> sourceFileList = new ArrayList<SourceFile>();
		String localPackageName = Text.joinList(File.separator, getJavaNamespaceList());
		String facetName = getComponent().getIdentifier() + getIdentifier();
		
		SourceFile applicationClass = 
			new SourceFile(localPackageName, facetName + "Impl.java", generateApplicationClass());
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
	 * CORBA Adapter Generator Methods
	 *************************************************************************/
	
	public String generateEquivalentMethodAdapterToCorba()
	{
		return new ProvidesDefEquivalentMethodAdapterToCorbaTemplate().generate(this);
	}
		
	public String generateEquivalentMethodAdapterFromCorba()
	{
		return new ProvidesDefEquivalentMethodAdapterFromCorbaTemplate().generate(this);
	}
	
	
	public String generateNavigationMethodAdapterToCorba()
	{		
		return new ProvidesDefNavigationMethodAdapterToCorbaTemplate().generate(this);
	}

	public String generateNavigationMethodAdapterFromCorba()
	{		
		return new ProvidesDefNavigationMethodAdapterFromCorbaTemplate().generate(this);
	}

	
	public String generateFacetAdapterDeclaration()
	{
		return TAB + "private " + getInterface().generateAbsoluteJavaName() + " " + getIdentifier() + ";\n";
	}
	
	public String generateCorbaFacetReferenceDeclaration()
	{
		return TAB + "private " + getInterface().generateAbsoluteIdlName() + " " + getIdentifier() + "Facet;\n";
	}
	
	public String generateCorbaFacetReferenceInit()
	{
		return TAB2 + getIdentifier() + "Facet = null;\n";
	}
}
