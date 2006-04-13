package ccmtools.generator.java.metamodel;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import ccmtools.generator.java.templates.ProvidesDefApplicationClassTemplate;
import ccmtools.generator.java.templates.ProvidesDefEquivalentMethodAdapterLocalTemplate;
import ccmtools.generator.java.templates.ProvidesDefEquivalentMethodAdapterToCorbaTemplate;
import ccmtools.generator.java.templates.ProvidesDefGetMethodImplementationTemplate;
import ccmtools.generator.java.templates.ProvidesDefNavigationMethodAdapterLocalTemplate;
import ccmtools.generator.java.templates.ProvidesDefNavigationMethodAdapterToCorbaTemplate;
import ccmtools.utils.SourceFile;
import ccmtools.utils.Text;

public class ProvidesDef
	extends ModelElement
{
	private ComponentDef component;
	private InterfaceDef iface;
	
	public ProvidesDef(String identifier, List ns)
	{
		super(identifier, ns);
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
		
	public Set getJavaImportStatements()
	{
		Set importStatements = getInterface().getJavaImportStatements();
		importStatements.add(getInterface().generateAbsoluteJavaCcmName());
		importStatements.add(getInterface().generateAbsoluteJavaName());
		importStatements.add(getComponent().generateAbsoluteJavaName() + "Impl");
		importStatements.add("ccm.local.Components.CCMException");
		return importStatements;
	}
	
	
	
	/*************************************************************************
	 * Local Interface Generator
	 * 
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
	 * Local Component Generator
	 * 
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
	 * 
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
	
	public List generateApplicationSourceFiles()
	{
		List sourceFileList = new ArrayList();
		String localPackageName = Text.joinList(File.separator, getJavaNamespaceList());
		String facetName = getComponent().getIdentifier() + getIdentifier();
		
		SourceFile applicationClass = 
			new SourceFile(localPackageName, facetName + "Impl.java", generateApplicationClass());
		sourceFileList.add(applicationClass);
					
		return sourceFileList;
	}	
	
	
	
	/*************************************************************************
	 * CORBA Component Generator Methods
	 * 
	 *************************************************************************/
	
	// Code generator methods -------------------------------------------------	
	
	public String generateEquivalentMethodAdapterToCorba()
	{
		return new ProvidesDefEquivalentMethodAdapterToCorbaTemplate().generate(this);
	}
		
	public String generateNavigationMethodAdapterToCorba()
	{		
		return new ProvidesDefNavigationMethodAdapterToCorbaTemplate().generate(this);
	}
	
	public String generateFacetAdapterDeclaration()
	{
		return TAB + "private " + getInterface().generateAbsoluteJavaName() + " " + getIdentifier() + ";\n";
	}
}
