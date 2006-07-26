package ccmtools.generator.idl.metamodel;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import ccmtools.Constants;
import ccmtools.utils.Code;
import ccmtools.utils.SourceFile;
import ccmtools.utils.Text;

public class ModelElement
	implements Idl3Generator
{
	/*************************************************************************
	 * IDL Model Implementation
	 *************************************************************************/
	
	/** Model element identifier */
	private String identifier;

	/** Namespace lists */
	private List<String> idlNamespaceList = new ArrayList<String>();
	
	
	protected ModelElement()
	{
	}

	protected ModelElement(String identifier, List<String> namespace)
	{
		setIdentifier(identifier);
		setIdlNamespaceList(namespace);
	}
	
	
	public String getIdentifier()
	{
		return identifier;
	}

	public void setIdentifier(String identifier)
	{
		this.identifier = identifier;
	}

	
	public List<String> getIdlNamespaceList()
	{
		return idlNamespaceList;
	}

	public void setIdlNamespaceList(List<String> namespace)
	{
		idlNamespaceList.addAll(namespace);
	}	
	

	
	/*************************************************************************
	 * Default Implementations for the Type Interface
	 *************************************************************************/

	/**
	 * We use the absolute name, that is the namespace and the name, of
	 * a user defined type for the IDL mapping (e.g. "::world::europe::Color")
	 */
	public String generateIdlMapping()
	{
		return generateAbsoluteIdlName();
	}
	
	/**
	 * Note that constants are not complex types (except enum).
	 */
	public String generateIdlConstant(Object value)
	{
		return ""; // not allowed as a constant
	}
	
	/**
	 * Generate include path (namespace and name) for the current model element
	 * (e.g. "world/europe/Color")
	 * Note that this is different to an absolute IDL name which starts with a
	 * separator (e.g. "::world::europe::Color")
	 */
	public String generateIncludePath()
	{
		if(getIdlNamespaceList().size() == 0)
		{
			return getIdentifier();
		}
		else
		{
			return Text.joinList("/", getIdlNamespaceList()) + "/" + getIdentifier();
		}
	}

	
	/*************************************************************************
	 * Default Implementations for the Idl3Generator Interface
	 *************************************************************************/
	
	public static final String INTERFACE_PREFIX = "interface";
	public static final String COMPONENT_PREFIX = "component";
	
	public List<SourceFile> generateIdl3SourceFiles()
	{
		List<SourceFile> sourceFileList = new ArrayList<SourceFile>();
		String packageName;
		if(this instanceof ComponentDef || this instanceof HomeDef)
		{
			packageName = COMPONENT_PREFIX + File.separator + Text.joinList(File.separator, getIdlNamespaceList());
		}
		else
		{
			packageName = INTERFACE_PREFIX + File.separator + Text.joinList(File.separator, getIdlNamespaceList());
		}
        String sourceCode = Code.removeEmptyLines(generateIdl3());
		SourceFile sourceFile = new SourceFile(packageName, getIdentifier() + ".idl", sourceCode);
		sourceFileList.add(sourceFile);
		
		return sourceFileList;
	}
	
	public String generateIdl3()
	{
		throw new RuntimeException("Not implemented!");
	}
	
	
	/*************************************************************************
	 * Code Generator Utilities 
	 *************************************************************************/

	/** Helper constants for code generation */
	public static final String NL = "\n";
	public static final String TAB = "    ";
	public static final String TAB2 = "        ";
	public static final String TAB3 = "            ";
	public static final String TAB4 = "                 ";
	

	public String indent()
	{
		return Text.tab(getIdlNamespaceList().size());
	}
	
	public String generateCcmtoolsVersion()
	{
		return "CCM Tools version " + Constants.VERSION;
	}

	public String generateRepositoryId()
	{
		return "IDL:" + Text.joinList("/", getIdlNamespaceList()) + "/" + getIdentifier() + ":1.0";
	}

	public String generateIdlNamespace(String separator)
	{
		return separator + Text.joinList(separator, getIdlNamespaceList());
	}
	
	public String generateIdlNamespace()
	{
		return generateIdlNamespace("::");
	}
	
	public String generateAbsoluteIdlName(String separator)
	{
		if(getIdlNamespaceList().size() == 0)
		{
			return separator + getIdentifier();
		}
		else
		{
			return generateIdlNamespace(separator) + separator + getIdentifier();
		}
	}
	
	public String generateAbsoluteIdlName()
	{
		return generateAbsoluteIdlName("::");
	}
		
	public String generateModulesOpen()
	{
		List<String> modules = getIdlNamespaceList();
		StringBuilder code = new StringBuilder();
		for(int i = 0; i<modules.size(); ++i)
		{
			code.append(Text.tab(i)).append("module ").append(modules.get(i));
			// Note that confix forces the generator to put the brace on the same
            // line as the keyword module (e.g. "module world {").
            // code.append(NL).append(Text.tab(i)); 
			code.append(" {").append(NL);
		}		
		return code.toString();
	}
		
	public String generateModulesClose()
	{
		StringBuilder code = new StringBuilder();
		List<String> modules = getIdlNamespaceList();
		for(int i = modules.size()-1; i>= 0; --i)
		{
			code.append(Text.tab(i));
			code.append("}; // /module ").append(modules.get(i)).append(NL);
		}
		return code.toString();
	}
	
	public String generateIncludeGuardOpen()
	{
		StringBuilder code = new StringBuilder();
		String mangledName = (generateAbsoluteIdlName("_")).toUpperCase();
		code.append("#ifndef __").append(mangledName).append("__IDL__").append(NL);
		code.append("#define __").append(mangledName).append("__IDL__").append(NL);
		return code.toString();
	}
	
	public String generateIncludeGuardClose()
	{
		StringBuilder code = new StringBuilder();
		String mangledName = (generateAbsoluteIdlName("_")).toUpperCase();		
		code.append("#endif /* __").append(mangledName).append("__IDL__ */").append(NL);		
		return code.toString();		
	}
			
	public String generateIncludeStatement(String includePath)
	{
		if(includePath == null || includePath.length() == 0)
		{
			// primitive typed don't need include statements
			return "";
		}
		else
		{
			return "#include <" + includePath + ".idl>" + NL;
		}
	}
	
	public String generateIncludeStatements(Set<String> includePaths)
	{
		StringBuilder code = new StringBuilder();	
		for(String path : includePaths)
		{
			code.append(generateIncludeStatement(path));
		}
		return code.toString();
	}
}
