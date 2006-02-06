package ccmtools.generator.java.metamodel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ccmtools.Constants;
import ccmtools.utils.Text;

public abstract class ModelElement
{
	public static final String NL = "\n";
	public static final String TAB = "    ";
	public static final String TAB2 = "        ";
	
	private String identifier;
	private List idlNamespace = new ArrayList();
	private List javaLocalNamespace = new ArrayList();
	private List javaRemoteNamespace = new ArrayList();
	
	protected ModelElement()
	{
	}

	protected ModelElement(String identifier, List namespace)
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

	
	public List getIdlNamespaceList()
	{
		return idlNamespace;
	}

	public void setIdlNamespaceList(List namespace)
	{
		idlNamespace.addAll(namespace);
		// javaNamespace directly depends on the idlNamesapce
		setJavaNamespaceList(namespace);
		setJavaRemoteNamespaceList(namespace);
	}	
	
	
	public List getJavaNamespaceList()
	{
		return javaLocalNamespace;
	}
	
	public void setJavaNamespaceList(List namespace)
	{
		javaLocalNamespace.addAll(namespace);		
		// Set implicit Java namespace elements
		getJavaNamespaceList().add("ccm");
		getJavaNamespaceList().add("local");		
	}

	public List getJavaRemoteNamespaceList()
	{
		return javaRemoteNamespace;
	}
	
	public void setJavaRemoteNamespaceList(List namespace)
	{
		javaRemoteNamespace.addAll(namespace);		
		// Set implicit Java namespace elements
		getJavaRemoteNamespaceList().add("ccm");
		getJavaRemoteNamespaceList().add("remote");		
	}
	
	
	public String getIdlNamespace()
	{
		return Text.joinList(".", getIdlNamespaceList());
	}
	
	public String getJavaNamespace()
	{
		return Text.joinList(".", javaLocalNamespace);
	}

	public String getJavaRemoteNamespace()
	{
		return Text.joinList(".", javaRemoteNamespace);
	}
	
	
	public String getAbsoluteIdlName()
	{
		if(getIdlNamespaceList().size() == 0)
		{
			return getIdentifier();
		}
		else
		{
			return getIdlNamespace() + "." + getIdentifier();
		}
	}
	
	public String getAbsoluteJavaName()
	{
		if(getJavaNamespaceList().size() == 0)
		{
			return getIdentifier();
		}
		else 
		{
			return getJavaNamespace() + "." + getIdentifier();
		}
	}

	public String getAbsoluteJavaRemoteName()
	{
		if(getJavaRemoteNamespaceList().size() == 0)
		{
			return getIdentifier();
		}
		else 
		{
			return getJavaRemoteNamespace() + "." + getIdentifier();
		}
	}
	
	
	public String getCcmIdentifier()
	{
		return "CCM_" + getIdentifier();
	}
	
	public String getAbsoluteJavaCcmName()
	{
		if(getJavaNamespaceList().size() == 0)
		{
			return getCcmIdentifier();
		}
		else
		{
			return getJavaNamespace() + "." + getCcmIdentifier();
		}
	}
	
	
	public String getRepositoryId()
	{
		return "IDL:" + Text.joinList("/", getIdlNamespaceList()) + "/" + getIdentifier() + ":1.0";
	}
	
	
	// Generator methods ------------------------------------------------------
	
	public String generateCcmtoolsVersion()
	{
		return "CCM Tools version " + Constants.VERSION;
	}
	
	public String generateTimestamp()
	{
		Date now = new Date();
		return now.toString();
	}
}
