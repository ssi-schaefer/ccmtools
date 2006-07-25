package ccmtools.generator.idl.metamodel;

import java.util.ArrayList;
import java.util.List;

import ccmtools.generator.idl.templates.EnumDefFileTemplate;
import ccmtools.generator.idl.templates.EnumDefTemplate;
import ccmtools.utils.Text;

public class EnumDef
	extends ModelElement
	implements Type
{
	/*************************************************************************
	 * IDL Model Implementation
	 *************************************************************************/
	
	private List<String> members = new ArrayList<String>();
	
	public EnumDef(String identifier, List<String> namespace)
	{
		super(identifier, namespace);
	}
	
	public List<String> getMembers()
	{
		return members;
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
		return new EnumDefFileTemplate().generate(this); 
	}
	
	public String generateMemberList()
	{
		return TAB + Text.joinList(","+ NL + indent() + TAB, getMembers());
	}
	
	public String generateEnumeration()
	{
		return new EnumDefTemplate().generate(this); 
	}
	
	// Generate SourceFile objects --------------------------------------------
	
//	public List<SourceFile> generateIdl3SourceFiles()
//	{
//		List<SourceFile> sourceFileList = new ArrayList<SourceFile>();
//		String packageName = Text.joinList(File.separator, getIdlNamespaceList());
//		
//		SourceFile enumeration = new SourceFile(packageName, getIdentifier() + ".idl", generateIdl3Code());
//		sourceFileList.add(enumeration);
//		
//		return sourceFileList;
//	}
}
