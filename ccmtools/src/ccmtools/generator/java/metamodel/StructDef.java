package ccmtools.generator.java.metamodel;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
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
	private List fields = new ArrayList();
		
	public StructDef(String identifier, List namespace)
	{
		super(identifier, namespace);
	}
	
	
	public List getFields()
	{
		return fields;
	}

	
	public Set getJavaImportStatements()
	{
		// We put all import statements into s set to eliminate doubles
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
	
	public String generateJavaConstant(Object value)
	{
		return value.toString();
	}
	
	public String generateJavaMapping()
	{
//		return generateAbsoluteJavaName();
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
//		return "ccm.local.Holder<" + generateJavaMappingObject() + ">";
		return "Holder<" + generateJavaMappingObject() + ">";
	}	
		
	
	public String generateImplementation()
	{
		return new StructDefImplementationTemplate().generate(this);
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
	
	public String generateConstructor()
	{
		return new StructDefConstructorTemplate().generate(this);
	}
	
	public String generateDefaultConstructor()
	{
		return new StructDefDefaultConstructorTemplate().generate(this);
	}
	
	
	// Generate SourceFile objects --------------------------------------------
	
	public List generateLocalInterfaceSourceFiles()
	{
		List sourceFileList = new ArrayList();
		String localPackageName = Text.joinList(File.separator, getJavaNamespaceList());
		
		SourceFile struct = 
			new SourceFile(localPackageName, getIdentifier() + ".java", generateImplementation());
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
	 * Client Library Generator Methods
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
	
	public List generateClientLibSourceFiles()
	{
		List sourceFileList = new ArrayList();
		String remotePackageName = Text.joinList(File.separator, getJavaRemoteNamespaceList());
		
		SourceFile corbaConverter = 
			new SourceFile(remotePackageName, getIdentifier() + "CorbaConverter.java",generateCorbaConverter());		
		sourceFileList.add(corbaConverter);

		return sourceFileList;
	}
}
