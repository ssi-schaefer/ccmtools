package ccmtools.generator.java.metamodel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

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
	
	/** Namespace lists */
	private String identifier;
	private List idlNamespaceList = new ArrayList();
	private List javaNamespaceList = new ArrayList();
	private List javaRemoteNamespaceList = new ArrayList();
	
	
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
		return idlNamespaceList;
	}

	public void setIdlNamespaceList(List namespace)
	{
		idlNamespaceList.addAll(namespace);
		// javaNamespace directly depends on the idlNamesapce
		setJavaNamespaceList(namespace);
		setJavaRemoteNamespaceList(namespace);
	}	
	
	
	public List getJavaNamespaceList()
	{
		return javaNamespaceList;
	}
	
	public void setJavaNamespaceList(List namespace)
	{
		javaNamespaceList.addAll(namespace);		
		// Set implicit Java namespace elements
		getJavaNamespaceList().add("ccm");
		getJavaNamespaceList().add("local");		
	}

	
	public List getJavaRemoteNamespaceList()
	{
		return javaRemoteNamespaceList;
	}
	
	public void setJavaRemoteNamespaceList(List namespace)
	{
		javaRemoteNamespaceList.addAll(namespace);		
		// Set implicit Java namespace elements
		getJavaRemoteNamespaceList().add("ccm");
		getJavaRemoteNamespaceList().add("remote");		
	}
	
	
	
	/*************************************************************************
	 * Utility Methods
	 * 
	 *************************************************************************/
		
	public boolean isPrimitiveType(Type t)
	{
		if(t instanceof AnyType
			|| t instanceof BooleanType
			|| t instanceof ByteType
			|| t instanceof CharType
			|| t instanceof DoubleType
			|| t instanceof FixedType
			|| t instanceof FloatType
			|| t instanceof IntegerType
			|| t instanceof LongType
			|| t instanceof ShortType
			|| t instanceof StringType
			|| t instanceof VoidType)
		{
			return true;
		}
		else
		{
			return false;
		}
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
		
	public String generateRepositoryId()
	{
		return "IDL:" + Text.joinList("/", getIdlNamespaceList()) + "/" + getIdentifier() + ":1.0";
	}
	
	
	public String generateJavaNamespace()
	{
		return Text.joinList(".", javaNamespaceList);
	}

	public String generateAbsoluteJavaName()
	{
		if(getJavaNamespaceList().size() == 0)
		{
			return getIdentifier();
		}
		else 
		{
			return generateJavaNamespace() + "." + getIdentifier();
		}
	}	

	public String generateCcmIdentifier()
	{
		return "CCM_" + getIdentifier();
	}
	
	public String generateAbsoluteJavaCcmName()
	{
		if(getJavaNamespaceList().size() == 0)
		{
			return generateCcmIdentifier();
		}
		else
		{
			return generateJavaNamespace() + "." + generateCcmIdentifier();
		}
	}
		
	
	public String generateJavaRemoteNamespace()
	{
		return Text.joinList(".", javaRemoteNamespaceList);
	}
	
	public String generateAbsoluteJavaRemoteName()
	{
		if(getJavaRemoteNamespaceList().size() == 0)
		{
			return getIdentifier();
		}
		else 
		{
			return generateJavaRemoteNamespace() + "." + getIdentifier();
		}
	}
	
		
	public String generateCcmtoolsVersion()
	{
		return "CCM Tools version " + Constants.VERSION;
	}
	
	public String generateTimestamp()
	{
		Date now = new Date();
		return now.toString();
	}
	
	public String generateUUID()
	{
		return UUID.randomUUID().toString(); 
	}
	
	public String generateSerialVersionUID()
	{
		return Long.toString(UUID.randomUUID().getLeastSignificantBits());
	}
}
