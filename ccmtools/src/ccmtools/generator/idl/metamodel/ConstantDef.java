package ccmtools.generator.idl.metamodel;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import ccmtools.generator.idl.templates.ConstantDefTemplate;
import ccmtools.utils.SourceFile;
import ccmtools.utils.Text;

public class ConstantDef
	extends ModelElement
{
	private Type type;
	private Object constValue;
		
	public ConstantDef(String identifier, List<String> namespace)
	{
		super(identifier, namespace);
	}
			
	public Type getType()
	{
		return type;
	}

	public void setType(Type type)
	{
		this.type = type;
	}
	
	
	public Object getConstValue()
	{
		return constValue;
	}	
	
	public void setConstValue(Object value)
	{
		this.constValue = value;
	}
	
	
	/*************************************************************************
	 * IDL3 generator methods
	 *************************************************************************/
	
	public String generateIdlMapping()
	{
		return getIdentifier();
	}
				
	public String generateIdl3Code()
	{
		return new ConstantDefTemplate().generate(this); 
	}
	
	public String generateConstantValue()
	{
		return getType().generateIdlConstant(getConstValue());
	}
	
	public String generateInterfaceConstant()
	{
		return "const " + getType().generateIdlMapping() + " " + getIdentifier() + " = " 
			+ generateConstantValue() + ";" + NL;
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
