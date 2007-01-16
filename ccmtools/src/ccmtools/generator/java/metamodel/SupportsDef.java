package ccmtools.generator.java.metamodel;

import java.util.List;
import java.util.Set;

public class SupportsDef
	extends ModelElement
{
	private InterfaceDef iface;
	
	public SupportsDef(String identifier, List<String> namespace)
	{
		super(identifier, namespace);
	}

	
	public InterfaceDef getInterface()
	{
		return iface;
	}

	public void setInterface(InterfaceDef supports)
	{
		this.iface = supports;
	}

    
	public Set<String> getJavaImportStatements()
	{
		Set<String> importStatements = getInterface().getJavaImportStatements();
		importStatements.add(getInterface().generateAbsoluteJavaCcmName());
		importStatements.add(getInterface().generateAbsoluteJavaName());
		return importStatements;
	}
	
	
	/*************************************************************************
	 * Local Interface Generator
	 *************************************************************************/
	
	
	
	/*************************************************************************
	 * Local Adapter Generator
	 *************************************************************************/
	
}
