package ccmtools.generator.idl.metamodel;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import ccmtools.generator.idl.templates.ArrayDefTemplate;
import ccmtools.generator.idl.templates.TypedefDefFileTemplate;
import ccmtools.generator.idl.templates.TypedefDefTemplate;
import ccmtools.utils.SourceFile;
import ccmtools.utils.Text;


public class TypedefDef
	extends ModelElement
	implements Type
{
	private Type alias;
	
	public TypedefDef(String identifier, List<String> namespace)
	{
		super(identifier, namespace);
	}
	
	public void setAlias(Type alias)
	{
		this.alias = alias;
	}
	
	public Type getAlias()
	{
		return alias;
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
		return generateIncludeStatement(getAlias().generateIncludePath());
	}
	
	public String generateIdl3Code()
	{
		return new TypedefDefFileTemplate().generate(this);
//		if(getAlias() instanceof ArrayDef)
//		{
//			// The specific structure of an array definition (e.g. typedef long LongArray[10];
//			// forces this special case...
//			return new ArrayDefFileTemplate().generate(this);
//		}
//		else
//		{
//			return new TypedefDefFileTemplate().generate(this);
//		}
	}
	
	public String generateTypedef()
	{
		if(getAlias() instanceof ArrayDef)
		{
			// The specific structure of an array definition (e.g. typedef long LongArray[10];
			// forces this special case...
			return new ArrayDefTemplate().generate(this);
		}
		else
		{
			return new TypedefDefTemplate().generate(this);
		}
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
