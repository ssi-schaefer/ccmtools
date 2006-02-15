package ccmtools.generator.java.metamodel;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

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
	
	
	/*************************************************************************
	 * Local Interface Generator
	 * 
	 *************************************************************************/
	
	public String generateProvidesDefEquivalentMethodDeclaration()
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
	
	public String generateProvidesDefEquivalentMethodAdapterLocal()
	{
		return new ProvidesDefEquivalentMethodAdapterLocalTemplate().generate(this);
	}
	
	public String generateProvidesDefNavigationMethodAdapterLocal()
	{		
		return new ProvidesDefNavigationMethodAdapterLocalTemplate().generate(this);
	}
	
	
	
	
	/*************************************************************************
	 * Local Implementation Generator
	 * 
	 *************************************************************************/
	
	// Code generator methods -------------------------------------------------

	public String generateCcmProvidesEquivalentMethodDeclaration()
	{
		return TAB + getInterface().generateAbsoluteJavaCcmName() + " get_" + getIdentifier() + "();\n";
	}
	
	public String generateProvidesDefGetMethodImplementation()
	{
		return new ProvidesDefGetMethodImplementationTemplate().generate(this);
	}
	
	public String generateProvidesDefApplicationClass()
	{
		return new ProvidesDefApplicationClassTemplate().generate(this);
	}
	
	
	// Generate SourceFile objects --------------------------------------------
	
	public List generateLocalImplementationSourceFiles()
	{
		List sourceFileList = new ArrayList();
		String localPackageName = Text.joinList(File.separator, getJavaNamespaceList());
		
		String facetName = getComponent().getIdentifier() + getIdentifier();
		SourceFile ccmFacetImplementation = 
			new SourceFile(localPackageName, facetName + "Impl.java", generateProvidesDefApplicationClass());
					
		sourceFileList.add(ccmFacetImplementation);
		return sourceFileList;
	}	
	
	
	
	/*************************************************************************
	 * Client Library Generator
	 * 
	 *************************************************************************/
	
	// Code generator methods -------------------------------------------------	
	
	public String generateProvidesDefEquivalentMethodAdapterToCorba()
	{
		return new ProvidesDefEquivalentMethodAdapterToCorbaTemplate().generate(this);
	}
		
	public String generateProvidesDefNavigationMethodAdapterToCorba()
	{		
		return new ProvidesDefNavigationMethodAdapterToCorbaTemplate().generate(this);
	}
	
	public String generateLocalFacetAdapterDeclaration()
	{
		return TAB + "private " + getInterface().generateAbsoluteJavaName() + " " + getIdentifier() + ";\n";
	}
}
