package ccmtools.generator.java.metamodel;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import ccmtools.generator.java.templates.EnumDefImplementationTemplate;
import ccmtools.utils.SourceFile;
import ccmtools.utils.Text;

public class EnumDef
	extends ModelElement
	implements Type
{
	private List members = new ArrayList();
	
	public EnumDef(String identifier, List namespace)
	{
		super(identifier, namespace);
	}

	
	public List getMembers()
	{
		return members;
	}

	
	/*************************************************************************
	 * Local Interface Generator Methods
	 * 
	 *************************************************************************/
	
	public String generateJavaConstant(Object value)
	{
		return value.toString();
	}
	
	public String generateJavaMapping()
	{
		return generateAbsoluteJavaName();
	}
		
	public String generateJavaMapping(PassingDirection direction)
	{
		if(direction == PassingDirection.IN
			|| direction == PassingDirection.RESULT)
		{
			return generateJavaMapping();
		}
		else
		{
			return generateJavaHolderType();
		}	
	}
	
	public String generateJavaMappingObject()
	{
		return generateJavaMapping();
	}
	
	public String generateJavaHolderType()
	{
		return "ccm.local.Holder<" + generateJavaMappingObject() + ">";
	}

	public String generateImplementation()
	{
		return new EnumDefImplementationTemplate().generate(this);
	}
	
	public String generateMemberList()
	{
		return Text.joinList(","+NL+TAB, getMembers());
	}
	
	// Generate SourceFile objects --------------------------------------------
	
	public List generateLocalInterfaceSourceFiles()
	{
		List sourceFileList = new ArrayList();
		String localPackageName = Text.joinList(File.separator, getJavaNamespaceList());
		
		SourceFile enumeration = 
			new SourceFile(localPackageName, getIdentifier() + ".java", generateImplementation());
		sourceFileList.add(enumeration);
		
		return sourceFileList;
	}
	
	
	
	/*************************************************************************
	 * Application Generator Methods
	 * 
	 *************************************************************************/
	
	public String generateJavaDefaultReturnValue()
	{
		return "null";		
	}
	
	
	/*************************************************************************
	 * Client Library Generator Methods
	 * 
	 *************************************************************************/
	
	public String generateCorbaMapping()
	{
		return generateAbsoluteIdlName();
	}
	
	public String generateCorbaMapping(PassingDirection direction)
	{
		if (direction == PassingDirection.IN
			|| direction == PassingDirection.RESULT)
		{
			return generateCorbaMapping();
		}
		else // INOUT, OUT
		{
			return generateCorbaHolderType();
		}
	}		
	
	public String generateCorbaHolderType()
	{
		return generateAbsoluteIdlName() + "Holder";
	}
	
	public String generateCorbaConverterType()
	{
		return "";
	}
}
