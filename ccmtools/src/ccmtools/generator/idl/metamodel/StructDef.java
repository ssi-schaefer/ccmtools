package ccmtools.generator.idl.metamodel;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import ccmtools.generator.idl.templates.StructDefFileTemplate;
import ccmtools.generator.idl.templates.StructDefTemplate;

public class StructDef
	extends ModelElement
	implements Type
{
	/*************************************************************************
	 * IDL Model Implementation
	 *************************************************************************/
	
	private List<FieldDef> fields = new ArrayList<FieldDef>();
		
	public StructDef(String identifier, List<String> namespace)
	{
		super(identifier, namespace);
	}
	
	
	public List<FieldDef> getFields()
	{
		return fields;
	}

	
	/*************************************************************************
	 * Type Interface Implementation
	 *************************************************************************/
	
	// Use ModelElement default implementations
	
	
	/*************************************************************************
	 * IDL3 Generator Methods Implementation
	 *************************************************************************/
	
	public String generateIdl3()
	{
		return new StructDefFileTemplate().generate(this); 
	}
	
	public String generateIncludeStatements()
	{
		Set<String> includePaths = new TreeSet<String>();
		for(FieldDef field: getFields())
		{
			includePaths.add(field.getType().generateIncludePath());
		}
		return generateIncludeStatements(includePaths);
	}
	
	public String generateFieldList()
	{
		StringBuilder code = new StringBuilder();
		for(FieldDef field : getFields())
		{
			code.append(indent()).append(field.generateIdl3());
		}
		return code.toString();
	}
	
	public String generateStructure()
	{
		return new StructDefTemplate().generate(this);
	}
}
