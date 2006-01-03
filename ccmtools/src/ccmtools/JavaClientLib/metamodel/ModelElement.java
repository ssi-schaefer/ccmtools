package ccmtools.JavaClientLib.metamodel;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class ModelElement
{
	public static final String NL = "\n";
	public static final String TAB = "    ";
	public static final String TAB2 = "        ";
	
	private String identifier;
	private List namespace = new ArrayList();
	private List javaNamespace = new ArrayList();
	
	public ModelElement()
	{
	}

	public String getIdentifier()
	{
		return identifier;
	}

	public void setIdentifier(String identifier)
	{
		this.identifier = identifier;
	}

	public List getNamespace()
	{
		return namespace;
	}

	public void setNamespace(List namespace)
	{
		this.namespace = namespace;
		javaNamespace.addAll(namespace);
		javaNamespace.add("ccm");
		javaNamespace.add("local");
	}	
	
	
	// Generator methods ------------------------------------------------------
	
	public String generateJavaNamespace()
	{
		return joinList(".", javaNamespace);
	}

	
	public String generateCorbaNamespace()
	{
		return joinList(".", getNamespace());
	}
	
	public String generateJavaName()
	{
		return generateJavaNamespace() + "." + getIdentifier();
	}
	
	public String generateCorbaName()
	{
		return generateCorbaNamespace() + "." + getIdentifier();
	}
	
	
	// Helper methods ---------------------------------------------------------
	
	public String joinList(String separator, List list)
	{
		StringBuffer code = new StringBuffer();
		for(Iterator i=list.iterator(); i.hasNext();)
		{
			code.append((String)i.next());
			code.append(separator);
		}
		return code.substring(0, code.lastIndexOf(separator));
	}
}
