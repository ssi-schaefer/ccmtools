package ccmtools.generator.java.clientlib.metamodel;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import ccmtools.generator.java.clientlib.templates.ConstantDeclarationTemplate;
import ccmtools.generator.java.clientlib.templates.GlobalConstantDeclarationTemplate;
import ccmtools.utils.SourceFile;
import ccmtools.utils.Text;

public class ConstantDef
	extends ModelElement
{
	private Type type;
	private Object value;
	
	public ConstantDef(String identifier, Type type, Object value)
	{
		setIdentifier(identifier);
		setType(type);	
		setValue(value);
	}
			
	public Type getType()
	{
		return type;
	}
	public void setType(Type type)
	{
		this.type = type;
	}
		
	public Object getValue()
	{
		return value;
	}	
	public void setValue(Object value)
	{
		this.value = value;
	}
	
	
	// Generator methods ------------------------------------------------------
	public String generateConstantDeclaration()
	{
		return new ConstantDeclarationTemplate().generate(this);
	}
	
	public String generateConstantValue()
	{
		return getType().generateJavaConstant(getValue());
	}
	
	public String generateGlobalConstantDeclaration()
	{
		return new GlobalConstantDeclarationTemplate().generate(this);
	}
	
	
	// Generate SourceFile objects --------------------------------------------
	
	public List generateClientLibSourceFiles()
	{
		List sourceFileList = new ArrayList();
		String packages = Text.joinList(File.separator, getJavaNamespaceList());
		
		SourceFile constantDeclaration = 
			new SourceFile(packages, getIdentifier() + ".java", generateGlobalConstantDeclaration());
		
		sourceFileList.add(constantDeclaration);
		return sourceFileList;
	}	
}
