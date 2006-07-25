package ccmtools.generator.idl.metamodel;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import com.sun.org.apache.bcel.internal.generic.GETSTATIC;

import ccmtools.generator.idl.templates.InterfaceDefTemplate;
import ccmtools.utils.SourceFile;
import ccmtools.utils.Text;


public class InterfaceDef
	extends ModelElement
	implements Type
{
	private List<InterfaceDef> baseInterfaces = new ArrayList<InterfaceDef>(); 
	private List<ConstantDef> constants = new ArrayList<ConstantDef>();
	private List<AttributeDef> attributes = new ArrayList<AttributeDef>();
	private List<OperationDef> operations = new ArrayList<OperationDef>();
	
	private List<ExceptionDef> exceptions = new ArrayList<ExceptionDef>();
	private List<EnumDef> enumerations = new ArrayList<EnumDef>();
	private List<StructDef> structures = new ArrayList<StructDef>();
	private List<TypedefDef> typedefs = new ArrayList<TypedefDef>();
	
	
	public InterfaceDef(String identifier, List<String> namespace)
	{
		super(identifier, namespace);
	}
	
	
	public List<InterfaceDef> getBaseInterfaces()
	{
		return baseInterfaces;
	}
	
	public List<ConstantDef> getConstants()
	{
		return constants;
	}
			
	public List<AttributeDef> getAttributes()
	{
		return attributes;
	}
		
	public List<OperationDef> getOperations()
	{
		return operations;
	}	
	
	public List<ExceptionDef> getExceptions()
	{
		return exceptions;
	}

	public List<EnumDef> getEnumerations()
	{
		return enumerations;
	}
	
	public List<StructDef> getStructures()
	{
		return structures;
	}
	
	public List<TypedefDef> getTypedefs()
	{
		return typedefs;
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
		for(InterfaceDef iface : getBaseInterfaces())
		{
			includePaths.add(iface.generateIncludePath());
		}	
		for(ConstantDef constant : getConstants())
		{
			includePaths.add(constant.getType().generateIncludePath());
		}		
		for(AttributeDef attr: getAttributes())
		{
			includePaths.add(attr.getType().generateIncludePath());
		}
		for(OperationDef op: getOperations())
		{
			// TODO: remove includes form typed defined within the same interface
			includePaths.addAll(op.generateIncludePaths());			
		}
		return generateIncludeStatements(includePaths);
	}
	
	public String generateBaseInterfaces()
	{
		StringBuilder code = new StringBuilder();
		if(getBaseInterfaces().size() > 0)
		{
			List<String> baseList = new ArrayList<String>();
			code.append(indent()).append(TAB).append(": ");
			for(InterfaceDef iface : getBaseInterfaces())
			{
				baseList.add(iface.generateAbsoluteIdlName());
			}
			code.append(Text.join(", ", baseList));
		}
		return code.toString();
	}
	
	public String generateConstants()
	{
		StringBuilder code = new StringBuilder();
		for(ConstantDef constant : getConstants())
		{
			code.append(indent()).append(TAB).append(constant.generateInterfaceConstant());
		}
		return code.toString();
	}
	
	public String generateEnumerations()
	{
		StringBuilder code = new StringBuilder();
		for(EnumDef enumeration : getEnumerations())
		{
			code.append(enumeration.generateEnumeration());
		}
		return code.toString();
	}
	
	public String generateStructures()
	{
		StringBuilder code = new StringBuilder();
		for(StructDef structure : getStructures())
		{
			code.append(structure.generateStructure());
		}
		return code.toString();
	}
	
	public String generateTypedefs()
	{
		StringBuilder code = new StringBuilder();
		for(TypedefDef typedef : getTypedefs())
		{
			code.append(typedef.generateTypedef());
		}
		return code.toString();
	}
	
	public String generateAttributes()
	{
		StringBuilder code = new StringBuilder();
		for(AttributeDef attr : getAttributes())
		{
			code.append(indent()).append(TAB).append(attr.generateIdl3Code());
		}
		return code.toString();
	}
	
	public String generateOperations()
	{
		StringBuilder code = new StringBuilder();
		for(OperationDef op : getOperations())
		{
			code.append(indent()).append(TAB).append(op.generateIdl3Code());
		}
		return code.toString();
	}
	
	public String generateExceptions()
	{
		StringBuilder code = new StringBuilder();
		for(ExceptionDef ex : getExceptions())
		{
			code.append(ex.generateException());
		}
		return code.toString();
	}
	
	public String generateIdl3Code()
	{
		return new InterfaceDefTemplate().generate(this); 
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
