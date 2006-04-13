package ccmtools.generator.java.metamodel;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import ccmtools.generator.java.templates.ExceptionDefConstructorTemplate;
import ccmtools.generator.java.templates.ExceptionDefCorbaConverterTemplate;
import ccmtools.generator.java.templates.ExceptionDefDefaultConstructorTemplate;
import ccmtools.generator.java.templates.ExceptionDefImplementationTemplate;
import ccmtools.utils.SourceFile;
import ccmtools.utils.Text;

public class ExceptionDef
	extends ModelElement
{
	private List fields = new ArrayList();
	
	public ExceptionDef(String identifier, List ns)
	{
		setIdentifier(identifier);
		setIdlNamespaceList(ns);	
	}
		
	public List getFields()
	{
		return fields;
	}

	
	public Set getJavaImportStatements()
	{
		Set importStatements = new TreeSet();
		importStatements.add(generateAbsoluteJavaName());
		for(Iterator i = getFields().iterator(); i.hasNext();)
		{
			FieldDef field = (FieldDef)i.next();
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
			return "";
		}
		
	}
	
	public String generateDefaultConstructor()
	{
		return new ExceptionDefDefaultConstructorTemplate().generate(this);
	}
	
	public String generateConstructorParameterList()
	{
		List parameterList = new ArrayList();
		for(Iterator i=getFields().iterator(); i.hasNext();)
		{
			FieldDef field = (FieldDef)i.next();
			parameterList.add(field.getType().generateJavaMapping() + " " + field.getIdentifier());
		}
		return Text.joinList(", ", parameterList);	
	}
	
	
	
	// Generate SourceFile objects --------------------------------------------
	
	public List generateLocalInterfaceSourceFiles()
	{
		List sourceFileList = new ArrayList();
		String localPackageName = Text.joinList(File.separator, getJavaNamespaceList());
		
		SourceFile exception = 
			new SourceFile(localPackageName, getIdentifier() + ".java", generateImplementation());
		sourceFileList.add(exception);
		
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
	
	public String generateCorbaConverter()
	{
		return new ExceptionDefCorbaConverterTemplate().generate(this);
	}
	
	public String generateCorbaConverterType()
	{
		return generateAbsoluteJavaRemoteName() + "CorbaConverter.convert";
	}
	
	
	// Generate SourceFile objects --------------------------------------------
	
	public List generateCorbaComponentSourceFiles()
	{
		List sourceFileList = new ArrayList();
		String remotePackageName = Text.joinList(File.separator, getJavaRemoteNamespaceList());
		
		SourceFile corbaConverter = 
			new SourceFile(remotePackageName, getIdentifier() + "CorbaConverter.java",generateCorbaConverter());		
		sourceFileList.add(corbaConverter);

		return sourceFileList;
	}	
}
