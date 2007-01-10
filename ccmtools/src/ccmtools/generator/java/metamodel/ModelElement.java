package ccmtools.generator.java.metamodel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import ccmtools.Constants;
import ccmtools.utils.ConfigurationLocator;
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
	private List<String> idlNamespaceList = new ArrayList<String>();
	private List<String> javaNamespaceList = new ArrayList<String>();
	private List<String> javaRemoteNamespaceList = new ArrayList<String>();
	
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
	    setJavaNamespaceList(namespace);
	    setJavaRemoteNamespaceList(namespace);
        idlNamespaceList.addAll(ConfigurationLocator.getInstance().getIdl2NamespaceExtension());
		idlNamespaceList.addAll(namespace);
	}	
	
	
	public List<String> getJavaNamespaceList()
	{
		return javaNamespaceList;
	}
	
	public void setJavaNamespaceList(List<String> namespace)
	{
        javaNamespaceList = new ArrayList<String>();
		javaNamespaceList.addAll(namespace);		
//		getJavaNamespaceList().add("ccm");
//		getJavaNamespaceList().add("local");		
    }

    public List<String> getJavaGenNamespaceList()
    {        
        List<String> namespaces = new ArrayList<String>();
        namespaces.addAll(ConfigurationLocator.getInstance().getJavaLocalNamespaceExtension());
        namespaces.addAll(getJavaNamespaceList());
        return namespaces;
    }
    
    	
	public List<String> getJavaRemoteNamespaceList()
	{
		return javaRemoteNamespaceList;
	}
	
	public void setJavaRemoteNamespaceList(List<String> namespace)
	{
        javaRemoteNamespaceList = new ArrayList<String>();
        javaRemoteNamespaceList.addAll(ConfigurationLocator.getInstance().getJavaRemoteNamespaceExtension());
        javaRemoteNamespaceList.addAll(namespace);		
//		getJavaRemoteNamespaceList().add("ccm");
//		getJavaRemoteNamespaceList().add("remote");		
	}
	
	
	
	/*************************************************************************
	 * Utility Methods
	 * 
	 *************************************************************************/
		
	/**
	 * An import statement is needed if the imported model element has been
	 * defined in a different package than the current model element.
	 * E.g. package world.Address;
	 *      import  world.Person; // not needed
	 *      import  world.europe.Color; // is needed
	 */
	public boolean isNeededJavaImportStatement(String namespace, String statement)
	{
		String packages = statement.substring(0, statement.lastIndexOf("."));
		if(packages.equals(namespace))
			return false;
		else
			return true;
	}

	public String generateJavaImportStatements(Set<String> importStatements)
	{
		return generateJavaImportStatements(generateJavaNamespace(), importStatements);
	}

	public String generateJavaImportStatements(String namespace, Set<String> importStatements)
	{
		StringBuffer sb = new StringBuffer();	
		for(String statement : importStatements)
		{
			if(isNeededJavaImportStatement(namespace, statement))
			{
				sb.append("import ").append(statement).append(";").append(NL);
			}
		}
		return sb.toString();
	}

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
		return Text.joinList(".", getJavaNamespaceList());
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

    
    public String generateJavaGenNamespace()
    {
        return Text.joinList(".", getJavaGenNamespaceList());
    }

    public String generateAbsoluteJavaGenName()
    {
        if(getJavaGenNamespaceList().size() == 0)
        {
            return getIdentifier();
        }
        else 
        {
            return generateJavaGenNamespace() + "." + getIdentifier();
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
