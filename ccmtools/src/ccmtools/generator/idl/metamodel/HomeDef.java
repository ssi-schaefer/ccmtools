package ccmtools.generator.idl.metamodel;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import ccmtools.generator.idl.templates.HomeDefTemplate;

public class HomeDef
	extends ModelElement
{
	/*************************************************************************
	 * IDL Model Implementation
	 *************************************************************************/
	
	private ComponentDef component;
	
	public HomeDef(String identifier, List<String> namespace)
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

	
	/*************************************************************************
	 * IDL3 Generator Methods Implementation
	 *************************************************************************/
	
	public String generateIdl3()
	{
		return new HomeDefTemplate().generate(this); 
	}
	
	public String generateIncludeStatements()
	{
		Set<String> includePaths = new TreeSet<String>();
		includePaths.add(getComponent().generateIncludePath());
		// ...
		return generateIncludeStatements(includePaths);
	}
}
