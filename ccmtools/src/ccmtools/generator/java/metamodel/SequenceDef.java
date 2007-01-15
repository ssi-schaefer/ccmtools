package ccmtools.generator.java.metamodel;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import ccmtools.generator.java.templates.SequenceDefCorbaConverterTemplate;
import ccmtools.utils.SourceFile;
import ccmtools.utils.Text;

public class SequenceDef
	extends 
        ModelElement
	implements 
        Type,
        JavaCorbaAdapterGeneratorElement
{
	private long bound;
	private Type elementType;
	
	
	public SequenceDef(String identifier, List<String> namespace, Type elementType)
	{
		super(identifier, namespace);
		setElementType(elementType);
	}

	public long getBound()
	{
		return bound;
	}

	public void setBound(long bound)
	{
		this.bound = bound;
	}
	

	public Type getElementType()
	{
		return elementType;
	}

	public void setElementType(Type elementType)
	{
		this.elementType = elementType;
	}

	
	public Set<String> getJavaImportStatements()
	{
		Set<String> importStatements = getElementType().getJavaImportStatements();
		importStatements.add("java.util.List");
		return importStatements; 
	}
	
	
	/*************************************************************************
	 * Local Interface Generator Methods
	 * 
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
		return "List<" + getElementType().generateJavaMappingObject() + ">";
	}
	
	public String generateJavaMappingImpl()
	{
		return "java.util.ArrayList<" + getElementType().generateJavaMappingObject() + ">";
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
	
	
	
	/*************************************************************************
	 * Application Generator Methods
	 * 
	 *************************************************************************/
	
	public String generateJavaDefaultReturnValue()
	{
		return "null";		
	}
	
	
	/*************************************************************************
	 * CORBA Component Generator Methods
	 * 
	 *************************************************************************/

	public String generateCorbaMapping()
	{
		return getElementType().generateCorbaMapping() + "[]";
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
		return new SequenceDefCorbaConverterTemplate().generate(this);
	}
	
	
	// Generate SourceFile objects --------------------------------------------
	
	public List<SourceFile> generateCorbaAdapterSourceFiles()
	{
		List<SourceFile> sourceFileList = new ArrayList<SourceFile>();
		String remotePackageName = Text.joinList(File.separator, getJavaRemoteNamespaceList());
		
		SourceFile corbaConverter = 
			new SourceFile(remotePackageName, getIdentifier() + "CorbaConverter.java",generateCorbaConverter());		
		sourceFileList.add(corbaConverter);

		return sourceFileList;
	}
}
