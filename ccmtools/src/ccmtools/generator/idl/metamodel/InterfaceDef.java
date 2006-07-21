package ccmtools.generator.idl.metamodel;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import ccmtools.generator.idl.templates.InterfaceDefTemplate;
import ccmtools.utils.SourceFile;
import ccmtools.utils.Text;


public class InterfaceDef
	extends ModelElement
{
	private List<InterfaceDef> baseInterfaces = new ArrayList<InterfaceDef>(); 
	private List<ConstantDef> constants = new ArrayList<ConstantDef>();
//	private List<AttributeDef> attribute = new ArrayList<AttributeDef>();
//	private List<OperationDef> operation = new ArrayList<OperationDef>();
	
	
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
	
	public List<ConstantDef> getAllConstants()
	{
		List<ConstantDef> allConstants = new ArrayList<ConstantDef>();
		for(InterfaceDef iface : getBaseInterfaces())
		{
			allConstants.addAll(iface.getAllConstants());			
		}
		allConstants.addAll(getConstants());
		return allConstants;
	}
	
	
//	public List getAttributes()
//	{
//		return attribute;
//	}
//
//	public List getAllAttributes()
//	{
//		List allAttributes = new ArrayList();
//		for(Iterator i = getBaseInterfaces().iterator(); i.hasNext();)
//		{
//			InterfaceDef iface = (InterfaceDef)i.next();
//			allAttributes.addAll(iface.getAllAttributes());
//		}
//		allAttributes.addAll(getAttributes());
//		return allAttributes;
//	}
//	
//	
//	public List getOperations()
//	{
//		return operation;
//	}	
//	
//	public List getAllOperations()
//	{
//		List allOperations = new ArrayList();
//		for(Iterator i = getBaseInterfaces().iterator(); i.hasNext();)
//		{
//			InterfaceDef iface = (InterfaceDef)i.next();
//			allOperations.addAll(iface.getAllOperations());
//		}		
//		allOperations.addAll(getOperations());
//		return allOperations;
//	}
	

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
//		for(FieldDef field: getFields())
//		{
//			includePaths.add(field.getType().generateIncludePath());
//		}
		return generateIncludeStatements(includePaths);
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
