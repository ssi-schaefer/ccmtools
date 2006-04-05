package ccmtools.generator.java.metamodel;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import ccmtools.generator.java.templates.ArrayDefCorbaConverterTemplate;
import ccmtools.utils.SourceFile;
import ccmtools.utils.Text;

public class ArrayDef
	extends ModelElement
	implements Type
{
	/** Array type which is the same for all elements staored in an array. */
	private Type type;

	/** Stores the bound of every array dimension */
	private List bounds = new ArrayList();
	
	public ArrayDef(String identifier, List namespace)
	{
		super(identifier, namespace);
	}

	
	public Type getType()
	{
		return type;
	}

	public void setType(Type type)
	{
		this.type = type;
	}

	
	public List getBounds()
	{
		return bounds;
	}

	
	public Set getJavaImportStatements()
	{
		return getType().getJavaImportStatements();
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
		return getType().generateJavaMapping() + generateDimensions();
	}
	
	public String generateJavaMappingImpl()
	{
		return getType().generateJavaMapping() + generateDimensionsImpl();
	}
	
	public String generateDimensions()
	{
		StringBuffer sb = new StringBuffer();
		for(int i = 0; i < getBounds().size(); i++)
		{
			sb.append("[]");
		}		
		return  sb.toString();
	}
	
	public String generateDimensionsImpl()
	{
		StringBuffer sb = new StringBuffer();
		for(int i = 0; i < getBounds().size(); i++)
		{
			sb.append("[").append(getBounds().get(i)).append("]");
		}		
		return  sb.toString();
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
//		return "ccm.local.Holder<" + generateJavaMappingObject() + ">";
		return "Holder<" + generateJavaMappingObject() + ">";
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
		return getType().generateCorbaMapping() + generateDimensions();
	}
	
	public String generateCorbaMappingImpl()
	{
		return getType().generateCorbaMapping() + generateDimensionsImpl();
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
		if(isPrimitiveType(getType()))
		{	
			return ""; // There is no converter for primitive types
		}
		else
		{
			return generateAbsoluteJavaRemoteName() + "CorbaConverter.convert";
		}
	}
	
	public String generateCorbaConverter()
	{
		if(isPrimitiveType(getType()))
		{
			return ""; // There is no converter for primitive types
		}
		else
		{
			// TODO: implement a converter for multidimensional arrays
			return new ArrayDefCorbaConverterTemplate().generate(this);
		}
	}

	
	// Generate SourceFile objects --------------------------------------------
	
	public List generateClientLibSourceFiles()
	{
		List sourceFileList = new ArrayList();
		String remotePackageName = Text.joinList(File.separator, getJavaRemoteNamespaceList());
		if(!isPrimitiveType(getType()))
		{
			SourceFile corbaConverter = 
				new SourceFile(remotePackageName, getIdentifier() + "CorbaConverter.java",generateCorbaConverter());		
			sourceFileList.add(corbaConverter);
		}
		return sourceFileList;
	}	
}
