package ccmtools.generator.java.metamodel;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import ccmtools.generator.java.templates.ConstantDefDeclarationTemplate;
import ccmtools.generator.java.templates.ConstantDefInterfaceGlobalTemplate;
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

	public String generateDeclaration()
	{
		return new ConstantDefDeclarationTemplate().generate(this);
	}
	
	public String generateJavaValue()
	{
		return getType().generateJavaConstant(getValue());
	}
	
	public String generateInterfaceGlobal()
	{
		return new ConstantDefInterfaceGlobalTemplate().generate(this);
	}
	
	
	// Generate SourceFile objects --------------------------------------------
	
	public List<SourceFile> generateLocalInterfaceSourceFiles()
	{
		List<SourceFile> sourceFileList = new ArrayList<SourceFile>();
		String packages = Text.joinList(File.separator, getJavaNamespaceList());		
		SourceFile interfaceGlobal = new SourceFile(packages, getIdentifier() + ".java", generateInterfaceGlobal());
		sourceFileList.add(interfaceGlobal);		
		return sourceFileList;
	}	
}
