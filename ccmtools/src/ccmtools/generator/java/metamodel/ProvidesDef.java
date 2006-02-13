package ccmtools.generator.java.metamodel;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import ccmtools.generator.java.templates.CcmFacetImplementationTemplate;
import ccmtools.generator.java.templates.CcmProvidesGetImplementationTemplate;
import ccmtools.generator.java.templates.ProvidesAdapterNavigationMethodImplementationTemplate;
import ccmtools.generator.java.templates.ProvidesEquivalentMethodAdapterTemplate;
import ccmtools.generator.java.templates.ProvidesEquivalentMethodImplementationTemplate;
import ccmtools.generator.java.templates.ProvidesNavigationMethodImplementationTemplate;
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
	
	
	/**
	 * Java Local Interface Generator
	 * 
	 */
	

	
	
	/**
	 * Java Local Component Generator
	 * 
	 */
		
	public String generateFacetAdapterReference()
	{
		return TAB + "private " + getInterface().getAbsoluteJavaName() + 
				" " + getIdentifier() + "FacetAdapter;";
	}
	
	public String generateProvidesEquivalentMethodAdapter()
	{
		return new ProvidesEquivalentMethodAdapterTemplate().generate(this);
	}
	
	public String generateProvidesAdapterNavigationMethodImplementation()
	{		
		return new ProvidesAdapterNavigationMethodImplementationTemplate().generate(this);
	}
	
	
	
	
	/**
	 * Java Local Implementation Generator
	 * 
	 */
	
	// Code generator methods -------------------------------------------------

	public String generateCcmProvidesEquivalentMethodDeclaration()
	{
		return TAB + getInterface().getAbsoluteJavaCcmName() + " get_" + getIdentifier() + "();\n";
	}
	
	public String generateCcmProvidesGetImplementation()
	{
		return new CcmProvidesGetImplementationTemplate().generate(this);
	}
	
	public String generateCcmFacetImplementation()
	{
		return new CcmFacetImplementationTemplate().generate(this);
	}
	
	
	// Generate SourceFile objects --------------------------------------------
	
	public List generateLocalImplementationSourceFiles()
	{
		List sourceFileList = new ArrayList();
		String localPackageName = Text.joinList(File.separator, getJavaNamespaceList());
		
		SourceFile ccmInterfaceImplementation = 
			new SourceFile(localPackageName, getIdentifier() + "Impl.java", generateCcmFacetImplementation());
					
		sourceFileList.add(ccmInterfaceImplementation);
		return sourceFileList;
	}	
	
	
	
	/**
	 * Java Client Library Generator
	 * 
	 */
	
	// Code generator methods -------------------------------------------------	
	
	public String generateProvidesEquivalentMethodDeclaration()
	{
		return TAB + getInterface().getAbsoluteJavaName() + " provide_" + getIdentifier() + "();\n";
	}
		
	public String generateProvidesEquivalentMethodImplementation()
	{
		return new ProvidesEquivalentMethodImplementationTemplate().generate(this);
	}
		
	public String generateProvidesNavigationMethodImplementation()
	{		
		return new ProvidesNavigationMethodImplementationTemplate().generate(this);
	}
	
	public String generateLocalFacetAdapterDeclaration()
	{
		return TAB + "private " + getInterface().getAbsoluteJavaName() + " " + getIdentifier() + ";\n";
	}
}
