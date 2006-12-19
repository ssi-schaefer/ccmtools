package ccmtools.generator.idl.metamodel;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import ccmtools.Constants;
import ccmtools.utils.ConfigurationLocator;
import ccmtools.utils.SourceFile;
import ccmtools.utils.SourceFileHelper;
import ccmtools.utils.Text;


public abstract class ModelElement
	implements Idl3GeneratorElement, Idl2GeneratorElement
{
	/*************************************************************************
	 * IDL Model Implementation
	 *************************************************************************/
	
	/** Model element identifier */
	private String identifier;

	/** Namespace lists */
	private List<String> idlNamespaceList;
    
	protected ModelElement()
	{
        idlNamespaceList = new ArrayList<String>();
    }

	protected ModelElement(String identifier, List<String> namespaceList)
	{
	    this();
        setIdentifier(identifier);
		setIdlNamespaceList(namespaceList);
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
	    List<String> idlNamespaceExtension = ConfigurationLocator.getInstance().getIdlNamespaceExtension();
        if(idlNamespaceExtension.size() > 0)
        {
	        List<String> absoluteNamespaceList = new ArrayList<String>();
	        absoluteNamespaceList.addAll(idlNamespaceExtension);
	        absoluteNamespaceList.addAll(idlNamespaceList);
	        return absoluteNamespaceList;
        }
        else
        {
            return idlNamespaceList;
        }
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
	 * Default Implementations for the IDL3 Generator Interface
	 *************************************************************************/
	
	public static final String INTERFACE_PREFIX = "interface";
	public static final String COMPONENT_PREFIX = "component";
	
	public List<SourceFile> generateIdl3SourceFiles()
	{
		List<SourceFile> sourceFileList = new ArrayList<SourceFile>();
		String packageName;
		if(this instanceof ComponentDef || this instanceof HomeDef)
		{
			packageName = COMPONENT_PREFIX 
                + File.separator + Text.joinList(File.separator, getIdlNamespaceList());
		}
		else
		{
			packageName = INTERFACE_PREFIX 
                + File.separator + Text.joinList(File.separator, getIdlNamespaceList());
		}
        String sourceCode = SourceFileHelper.removeEmptyLines(generateIdl3());
		SourceFile sourceFile = new SourceFile(packageName, getIdentifier() + ".idl", sourceCode);
		sourceFileList.add(sourceFile);		
		return sourceFileList;
	}
	
	public String generateIdl3()
	{
		throw new RuntimeException("Not implemented!");
	}
	

    /*************************************************************************
     * Default Implementations for the IDL2 Generator Interface
     *************************************************************************/

	public void setupIdl2Generator()
	{
	    //...
    }
	
    public String runIdl2Generator()
    {
        setupIdl2Generator();
        return generateIdl2();
    }

    /** Template method pattern */
	public abstract String generateIdl2();
    
    public String generateIdl2IncludePath()
    {
        if(getIdlNamespaceList().size() == 0)
        {
            return getIdentifier();
        }
        else
        {
            return Text.joinList("_", getIdlNamespaceList()) + "_" + getIdentifier();
        }
    }

    public List<SourceFile> generateIdl2SourceFiles()
    {
        List<SourceFile> sourceFileList = new ArrayList<SourceFile>();
        String fileName;
        if(getIdlNamespaceList().size() > 0)
        {
            fileName = Text.joinList("_", getIdlNamespaceList()) + "_" + getIdentifier() + ".idl";
        }
        else
        {
            fileName = getIdentifier() + ".idl";
        }
        
        //String sourceCode = generateIdl2();
        String sourceCode = runIdl2Generator();
        if(sourceCode.length() > 0)
        {
            SourceFile sourceFile = new SourceFile("", fileName, SourceFileHelper.removeEmptyLines(sourceCode));
            sourceFileList.add(sourceFile);     
        }
        
        return sourceFileList;
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

    public String generateIdl2ModulesClose()
    {
        StringBuilder code = new StringBuilder();
        List<String> modules = getIdlNamespaceList();
        for(int i = modules.size()-1; i>= 0; --i)
        {
            code.append(Text.tab(i));
            code.append("};").append(NL);
        }
        return code.toString();
    }

    
    
    public String generateIncludeGuardOpen()
    {
        return generateIncludeGuardOpen(generateAbsoluteIdlName("_"));
    }
    
	public String generateIncludeGuardOpen(String mangledName)
	{
		StringBuilder code = new StringBuilder();
		code.append("#ifndef __").append(mangledName.toUpperCase()).append("__IDL__").append(NL);
		code.append("#define __").append(mangledName.toUpperCase()).append("__IDL__").append(NL);
		return code.toString();
	}

    public String generateIncludeGuardClose()
    {
        return generateIncludeGuardClose(generateAbsoluteIdlName("_"));
    }
        
	public String generateIncludeGuardClose(String mangledName)
	{
		StringBuilder code = new StringBuilder();
		code.append("#endif /* __").append(mangledName.toUpperCase()).append("__IDL__ */").append(NL);		
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
