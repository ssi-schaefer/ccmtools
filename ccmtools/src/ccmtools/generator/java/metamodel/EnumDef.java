package ccmtools.generator.java.metamodel;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import ccmtools.generator.java.templates.EnumDefCorbaConverterTemplate;
import ccmtools.generator.java.templates.EnumDefImplementationTemplate;
import ccmtools.utils.SourceFile;
import ccmtools.utils.Text;

public class EnumDef
	extends 
        ModelElement
	implements 
        Type, 
        JavaLocalInterfaceGeneratorElement,
        JavaCorbaAdapterGeneratorElement
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

	
	public Set<String> getJavaImportStatements()
	{
		Set<String> importStatements = new TreeSet<String>();
		importStatements.add(generateAbsoluteJavaName()); 
		return importStatements;
	}

	
	/*************************************************************************
	 * Local Interface Generator Methods
	 *************************************************************************/
	
	public String generateJavaImportStatements()
	{
		return generateJavaImportStatements(getJavaImportStatements());
	}
	
	public String generateJavaImportStatements(String namespace)
	{
		return generateJavaImportStatements(namespace, getJavaImportStatements());
	}
	
	public String generateJavaConstant(Object value)
	{
		return value.toString();
	}
	
	public String generateJavaMapping()
	{
		return getIdentifier();
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
		return "Holder<" + generateJavaMappingObject() + ">";
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
	
	public List<SourceFile> generateLocalInterfaceSourceFiles()
	{
		List<SourceFile> sourceFileList = new ArrayList<SourceFile>();
		String localPackageName = Text.joinList(File.separator, getJavaNamespaceList());		
		SourceFile enumeration = new SourceFile(localPackageName, getIdentifier() + ".java", generateImplementation());
		sourceFileList.add(enumeration);
		return sourceFileList;
	}
	
	
	
	/*************************************************************************
	 * Application Generator Methods
	 *************************************************************************/
	
	public String generateJavaDefaultReturnValue()
	{
		return "null";		
	}
	
	
	/*************************************************************************
	 * CORBA Adapter Generator Methods
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
		return generateAbsoluteJavaRemoteName() + "CorbaConverter.convert";
	}
	
	public String generateCorbaConverter()
	{
		return new EnumDefCorbaConverterTemplate().generate(this);
	}
	
	public String generateCaseConvertersToCorba()
	{
		StringBuffer sb = new StringBuffer();
		for(String member : getMembers())
		{
			sb.append(TAB3).append("case ").append(member).append(":").append(NL);
			sb.append(TAB4).append("out = ").append(generateAbsoluteIdlName()).append(".").append(member);
			sb.append(";").append(NL);
			sb.append(TAB4).append("break;").append(NL);
		}
		return sb.toString();
	}
	
	public String generateCaseConvertersFromCorba()
	{		
		StringBuffer sb = new StringBuffer();
		for(String member : getMembers())
		{
			sb.append(TAB3).append("case ").append(generateAbsoluteIdlName());
			sb.append("._").append(member).append(":").append(NL);
			sb.append(TAB4).append("out = ").append(getIdentifier()).append(".");
			sb.append(member).append(";").append(NL);
			sb.append(TAB4).append("break;").append(NL);
		}
		return sb.toString();
	}
	
	// Generate SourceFile objects --------------------------------------------
	
	public List<SourceFile> generateCorbaAdapterSourceFiles()
	{
		List<SourceFile> sourceFileList = new ArrayList<SourceFile>();
		String remotePackageName = Text.joinList(File.separator, getJavaRemoteNamespaceList());		
		SourceFile corbaConverter = new SourceFile(remotePackageName, getIdentifier() + "CorbaConverter.java",generateCorbaConverter());		
		sourceFileList.add(corbaConverter);
		return sourceFileList;
	}	
}
