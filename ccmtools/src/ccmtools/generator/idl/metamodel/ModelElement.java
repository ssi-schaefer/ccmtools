package ccmtools.generator.idl.metamodel;

import java.util.ArrayList;
import java.util.List;

import ccmtools.Constants;
import ccmtools.utils.Text;

public class ModelElement
{
	/** Helper constants for code generation */
	public static final String NL = "\n";
	public static final String TAB = "    ";
	public static final String TAB2 = "        ";
	public static final String TAB3 = "            ";
	public static final String TAB4 = "                 ";
	
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
	 * Utility Methods
	 *************************************************************************/

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

	
	public String generateIdlNamespace()
	{
		return Text.joinList(".", getIdlNamespaceList());
	}
	
	public String generateAbsoluteIdlName()
	{
		if(getIdlNamespaceList().size() == 0)
		{
			return getIdentifier();
		}
		else
		{
			return generateIdlNamespace() + "." + getIdentifier();
		}
	}
		
	public String generateModulesOpen()
	{
		List<String> modules = getIdlNamespaceList();
		StringBuilder code = new StringBuilder();
		for(int i = 0; i<modules.size(); ++i)
		{
			code.append(Text.tab(i)).append("module ").append(modules.get(i)).append(NL);
			code.append(Text.tab(i)).append("{").append(NL);
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
		String mangledName = (Text.joinList("_", getIdlNamespaceList()) + "_" +getIdentifier()).toUpperCase();
		code.append("#ifndef __").append(mangledName).append("__IDL__").append(NL);
		code.append("#define __").append(mangledName).append("__IDL__").append(NL);
		return code.toString();
	}
	
	public String generateIncludeGuardClose()
	{
		StringBuilder code = new StringBuilder();
		String mangledName = (Text.joinList("_", getIdlNamespaceList()) + "_" +getIdentifier()).toUpperCase();		
		code.append("#endif /* __").append(mangledName).append("__IDL__ */").append(NL);		
		return code.toString();		
	}
}
