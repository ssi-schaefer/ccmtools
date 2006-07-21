package ccmtools.generator.idl.metamodel;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import ccmtools.generator.idl.templates.ExceptionDefTemplate;
import ccmtools.utils.SourceFile;
import ccmtools.utils.Text;

public class ExceptionDef
	extends ModelElement
{
	private List<FieldDef> fields = new ArrayList<FieldDef>();
	
	public ExceptionDef(String identifier, List<String> namespace)
	{
		super(identifier, namespace);
	}
		
	public List<FieldDef> getFields()
	{
		return fields;
	}

	
	/*************************************************************************
	 * IDL3 generator methods
	 *************************************************************************/
	
	public String generateIdlMapping()
	{
		return getIdentifier();
	}
	
	public String generateIdlConstant(Object value)
	{
		return ""; // not allowed as a constant
	}
		
	public String generateIncludePath()
	{
		return generateAbsoluteIdlName("/");
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
			code.append(indent()).append(field.generateIdl3Code());
		}
		return code.toString();
	}
	
	public String generateIdl3Code()
	{
		return new ExceptionDefTemplate().generate(this); 
	}
	
	
	// Generate SourceFile objects --------------------------------------------
	
	public List<SourceFile> generateIdl3SourceFiles()
	{
		List<SourceFile> sourceFileList = new ArrayList<SourceFile>();
		String packageName = Text.joinList(File.separator, getIdlNamespaceList());
		
		SourceFile source = new SourceFile(packageName, getIdentifier() + ".idl", generateIdl3Code());
		sourceFileList.add(source);
		
		return sourceFileList;
	}	
}
