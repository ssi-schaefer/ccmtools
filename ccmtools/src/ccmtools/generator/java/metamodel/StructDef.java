package ccmtools.generator.java.metamodel;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import ccmtools.generator.java.templates.StructDefConstructorTemplate;
import ccmtools.generator.java.templates.StructDefCorbaConverterTemplate;
import ccmtools.generator.java.templates.StructDefDefaultConstructorTemplate;
import ccmtools.generator.java.templates.StructDefImplementationTemplate;
import ccmtools.utils.SourceFile;
import ccmtools.utils.Text;

public class StructDef
	extends ModelElement
	implements Type
{
	private List<FieldDef> fields = new ArrayList<FieldDef>();
		
	public StructDef(String identifier, List<String> namespace)
	{
		super(identifier, namespace);
	}
	
	
	public List<FieldDef> getFields()
	{
		return fields;
	}

	
	public Set<String> getJavaImportStatements()
	{
		// We put all import statements into s set to eliminate doubles
		Set<String> importStatements = new TreeSet<String>();
		importStatements.add(generateAbsoluteJavaName());
		for(FieldDef field : getFields())
		{
			importStatements.addAll(field.getType().getJavaImportStatements());
		}
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
		return getIdentifier();
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
		
	
	public String generateImplementation()
	{
		return new StructDefImplementationTemplate().generate(this);
	}
	
	public String generateConstructorParameterList()
	{
		List<String> parameterList = new ArrayList<String>();
		for(FieldDef field : getFields())
		{
			parameterList.add(field.getType().generateJavaMapping() + " " + field.getIdentifier());
		}
		return Text.joinList(", ", parameterList);	
	}
	
	public String generateConstructor()
	{
		return new StructDefConstructorTemplate().generate(this);
	}
	
	public String generateDefaultConstructor()
	{
		return new StructDefDefaultConstructorTemplate().generate(this);
	}
	
	
	// Generate SourceFile objects --------------------------------------------
	
	public List<SourceFile> generateLocalInterfaceSourceFiles()
	{
		List<SourceFile> sourceFileList = new ArrayList<SourceFile>();
		String localPackageName = Text.joinList(File.separator, getJavaNamespaceList());
		
		SourceFile struct = new SourceFile(localPackageName, getIdentifier() + ".java", generateImplementation());
		sourceFileList.add(struct);
		
		return sourceFileList;
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
		return generateAbsoluteIdlName();
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
		return new StructDefCorbaConverterTemplate().generate(this);
	}
	
	
	// Generate SourceFile objects --------------------------------------------
	
	public List<SourceFile> generateCorbaComponentSourceFiles()
	{
		List<SourceFile> sourceFileList = new ArrayList<SourceFile>();
		String remotePackageName = Text.joinList(File.separator, getJavaRemoteNamespaceList());
		
		SourceFile corbaConverter = 
			new SourceFile(remotePackageName, getIdentifier() + "CorbaConverter.java",generateCorbaConverter());		
		sourceFileList.add(corbaConverter);

		return sourceFileList;
	}
}
