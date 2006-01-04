package ccmtools.JavaClientLib.metamodel;

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
	private List javaNamespace = new ArrayList();

	
	protected ModelElement()
	{
	}

	protected ModelElement(String identifier, List namespace)
	{
		setIdentifier(identifier);
		setIdlNamespace(namespace);
	}
	
	
	public String getIdentifier()
	{
		return identifier;
	}

	public void setIdentifier(String identifier)
	{
		this.identifier = identifier;
	}

	public List getIdlNamespace()
	{
		return idlNamespace;
	}

	public void setIdlNamespace(List namespace)
	{
		this.idlNamespace = namespace;
		
		// javaNamespace directly depends on the idlNamesapce
		javaNamespace.addAll(namespace);
		javaNamespace.add("ccm");
		javaNamespace.add("local");
	}	
	
	public List getJavaNamespace()
	{
		return javaNamespace;
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
	
	public String generateJavaNamespace()
	{
		return Text.joinList(".", javaNamespace);
	}
	
	public String generateIdlNamespace()
	{
		return Text.joinList(".", getIdlNamespace());
	}
	
	public String generateJavaName()
	{
		return generateJavaNamespace() + "." + getIdentifier();
	}
	
	public String generateIdlName()
	{
		return generateIdlNamespace() + "." + getIdentifier();
	}
}
