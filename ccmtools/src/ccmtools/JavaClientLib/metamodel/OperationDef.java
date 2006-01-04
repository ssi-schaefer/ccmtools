package ccmtools.JavaClientLib.metamodel;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import ccmtools.JavaClientLib.templates.OperationAdapterFromCorbaTemplate;
import ccmtools.JavaClientLib.templates.OperationAdapterToCorbaTemplate;
import ccmtools.JavaClientLib.templates.OperationDeclarationTemplate;
import ccmtools.utils.Text;

public class OperationDef
	extends ModelElement
{
	private Type type;
	private List parameter = new ArrayList();
	private List exception = new ArrayList();
	
	
	public OperationDef(String identifier, Type type)
	{
		setIdentifier(identifier);
		setType(type);
	}
	
	
	public Type getType()
	{
		return type;
	}
	
	public void setType(Type type)
	{
		this.type = type;
	}
	
	
	public List getException()
	{
		return exception;
	}

	
	public List getParameter()
	{
		return parameter;
	}

	
	// Generator methods ------------------------------------------------------
	
	public String generateOperationDeclaration()
	{
		return new OperationDeclarationTemplate().generate(this);
	}
	
	public String generateOperationAdapterFromCorba()
	{
		return new OperationAdapterFromCorbaTemplate().generate(this);
	}
	
	public String generateOperationAdapterToCorba()
	{
		return new OperationAdapterToCorbaTemplate().generate(this);
	}
	
	public String generateOperationReturnType()
	{		
		return getType().generateJavaMapping(PassingDirection.RESULT);
	}
	
	public String generateOperationParameterDeclarationList()
	{
		List parameterList = new ArrayList();
		for(Iterator i=getParameter().iterator(); i.hasNext();)
		{
			ParameterDef p = (ParameterDef)i.next();
			parameterList.add(p.generateParameter());
		}
		return Text.joinList(", ", parameterList);
	}
	
	public String generateOperationParameterList()
	{
		List parameterList = new ArrayList();
		for(Iterator i=getParameter().iterator(); i.hasNext();)
		{
			ParameterDef p = (ParameterDef)i.next();
			parameterList.add(p.getIdentifier());
		}
		return Text.joinList(", ", parameterList);
	}
	
	public String generateOperationExceptionList()
	{
		StringBuffer code = new StringBuffer();
		if(getException().size() != 0)
		{
			code.append(", ");
			List exceptionList = new ArrayList();
			for (Iterator i = getException().iterator(); i.hasNext();)
			{
				ExceptionDef e = (ExceptionDef) i.next();
				exceptionList.add(e.getIdentifier());
			}
			code.append(Text.joinList(", ", exceptionList));
		}
		return code.toString();
	}
}
