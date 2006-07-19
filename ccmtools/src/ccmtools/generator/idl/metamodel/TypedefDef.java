package ccmtools.generator.idl.metamodel;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import ccmtools.generator.idl.templates.TypedefDefTemplate;
import ccmtools.utils.SourceFile;
import ccmtools.utils.Text;


public class TypedefDef
	extends ModelElement
	implements Type
{
	private Type alias;
	
	public void setAlias(Type alias)
	{
		this.alias = alias;
	}
	
	public Type getAlias()
	{
		return alias;
	}
	
	public TypedefDef(String identifier, List<String> namespace)
	{
		super(identifier, namespace);
	}

	
	public String generateIdlMapping()
	{
		return getIdentifier();
	}
	
	public String generateIdl3Code()
	{
		return new TypedefDefTemplate().generate(this); 
	}
	
	
	// Generate SourceFile objects --------------------------------------------
	
	public List<SourceFile> generateIdl3SourceFiles()
	{
		List<SourceFile> sourceFileList = new ArrayList<SourceFile>();
		String packageName = Text.joinList(File.separator, getIdlNamespaceList());
		
		SourceFile enumeration = new SourceFile(packageName, getIdentifier() + ".idl", generateIdl3Code());
		sourceFileList.add(enumeration);
		
		return sourceFileList;
	}
}
