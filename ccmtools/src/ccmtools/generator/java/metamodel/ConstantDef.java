package ccmtools.generator.java.metamodel;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import ccmtools.generator.java.templates.ConstantImplementationGlobalTemplate;
import ccmtools.generator.java.templates.ConstantImplementationTemplate;
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
	
	
	
	/*************************************************************************
	 * Local Interface Generator Methods
	 * 
	 *************************************************************************/
	
	// Generator methods ------------------------------------------------------

	public String generateConstantImplementation()
	{
		return new ConstantImplementationTemplate().generate(this);
	}
	
	public String generateConstantValue()
	{
		return getType().generateJavaConstant(getValue());
	}
	
	public String generateConstantImplementationGlobal()
	{
		return new ConstantImplementationGlobalTemplate().generate(this);
	}
	
	
	// Generate SourceFile objects --------------------------------------------
	
	public List generateLocalInterfaceSourceFiles()
	{
		List sourceFileList = new ArrayList();
		String packages = Text.joinList(File.separator, getJavaNamespaceList());
		
		SourceFile constantDeclaration = 
			new SourceFile(packages, getIdentifier() + ".java", generateConstantImplementationGlobal());
		
		sourceFileList.add(constantDeclaration);
		return sourceFileList;
	}	
}
