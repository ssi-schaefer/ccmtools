package ccmtools.generator.java.metamodel;

import java.util.List;
import java.util.Set;

public class SupportsDef
	extends ModelElement
{
	private InterfaceDef iface;
	
	public SupportsDef(String identifier, List ns)
	{
		super(identifier, ns);
	}

	
	public InterfaceDef getInterface()
	{
		return iface;
	}

	public void setInterface(InterfaceDef supports)
	{
		this.iface = supports;
	}

	public Set getJavaImportStatements()
	{
		Set importStatements = getInterface().getJavaImportStatements();
		importStatements.add(getInterface().generateAbsoluteJavaCcmName());
		importStatements.add(getInterface().generateAbsoluteJavaName());
//		importStatements.add("ccm.local.Components.CCMException");
		return importStatements;
	}
	
	
	/*************************************************************************
	 * Local Interface Generator
	 * 
	 *************************************************************************/
	
	
	
	/*************************************************************************
	 * Local Component Generator
	 * 
	 *************************************************************************/
	
}
