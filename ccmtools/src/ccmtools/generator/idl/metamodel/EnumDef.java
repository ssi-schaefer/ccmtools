package ccmtools.generator.idl.metamodel;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import ccmtools.generator.idl.templates.EnumDefTemplate;
import ccmtools.utils.SourceFile;
import ccmtools.utils.Text;

public class EnumDef
	extends ModelElement
	implements Type
{
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
	 * IDL3 generator methods
	 *************************************************************************/
	
	public String generateIdlMapping()
	{
		return getIdentifier();
	}
				
	public String generateIdl3Code()
	{
		return new EnumDefTemplate().generate(this); 
	}
	
	public String generateMemberList()
	{
		return TAB + Text.joinList(","+ NL + indent() + TAB, getMembers());
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
