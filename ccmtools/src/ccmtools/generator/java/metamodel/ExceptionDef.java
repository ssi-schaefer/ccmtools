package ccmtools.generator.java.metamodel;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import ccmtools.generator.java.templates.ExceptionDefConstructorTemplate;
import ccmtools.generator.java.templates.ExceptionDefCorbaConverterTemplate;
import ccmtools.generator.java.templates.ExceptionDefDefaultConstructorTemplate;
import ccmtools.generator.java.templates.ExceptionDefImplementationTemplate;
import ccmtools.generator.java.templates.ExceptionDefReasonConstructorTemplate;
import ccmtools.utils.SourceFile;
import ccmtools.utils.Text;

public class ExceptionDef
	extends 
        ModelElement
    implements 
        JavaLocalInterfaceGeneratorElement,
        JavaCorbaAdapterGeneratorElement
{
	private List<FieldDef> fields = new ArrayList<FieldDef>();
	
	public ExceptionDef(String identifier, List<String> namespace)
	{
		setIdentifier(identifier);
		setIdlNamespaceList(namespace);	
	}
		
	public List<FieldDef> getFields()
	{
		return fields;
	}

	
	public Set<String> getJavaImportStatements()
	{
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
	 *************************************************************************/

	public String generateJavaImportStatements()
	{
		return generateJavaImportStatements(getJavaImportStatements());
	}

	public String generateJavaImportStatements(String namespace)
	{
		return generateJavaImportStatements(namespace, getJavaImportStatements());
	}
	
	
	public String generateJavaMapping()
	{
		return getIdentifier();
	}
	
	public String generateImplementation()
	{
		return new ExceptionDefImplementationTemplate().generate(this);
	}
	
	public String generateConstructor()
	{
		if(getFields().size() > 0)
		{
			return new ExceptionDefConstructorTemplate().generate(this);
		}
		else
		{
			return new ExceptionDefReasonConstructorTemplate().generate(this);
		}
		
	}
	
	public String generateDefaultConstructor()
	{
		return new ExceptionDefDefaultConstructorTemplate().generate(this);
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
	
	
	
	// Generate SourceFile objects --------------------------------------------
	
	public List<SourceFile> generateLocalInterfaceSourceFiles()
	{
		List<SourceFile> sourceFileList = new ArrayList<SourceFile>();
		String localPackageName = Text.joinList(File.separator, getJavaNamespaceList());		
		SourceFile exception = new SourceFile(localPackageName, getIdentifier() + ".java", generateImplementation());
		sourceFileList.add(exception);		
		return sourceFileList;
	}
	
	
	
	/*************************************************************************
	 * Application Generator Methods
	 *************************************************************************/
	
	public String generateJavaDefaultReturnValue()
	{
		return "null";		
	}
	
	
	/*************************************************************************
	 * CORBA Adapter Generator Methods
	 *************************************************************************/
	
	public String generateCorbaMapping()
	{
		return generateAbsoluteIdlName();
	}
	
	public String generateCorbaConverter()
	{
		return new ExceptionDefCorbaConverterTemplate().generate(this);
	}
	
	public String generateCorbaConverterType()
	{
		return generateAbsoluteJavaRemoteName() + "CorbaConverter.convert";
	}
	
	
	// Generate SourceFile objects --------------------------------------------
	
	public List<SourceFile> generateCorbaAdapterSourceFiles()
	{
		List<SourceFile> sourceFileList = new ArrayList<SourceFile>();
		String remotePackageName = Text.joinList(File.separator, getJavaRemoteNamespaceList());		
		SourceFile corbaConverter = new SourceFile(remotePackageName, getIdentifier() + "CorbaConverter.java",generateCorbaConverter());		
		sourceFileList.add(corbaConverter);
		return sourceFileList;
	}	
}
