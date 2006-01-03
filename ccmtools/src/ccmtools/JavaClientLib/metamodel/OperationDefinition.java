package ccmtools.JavaClientLib.metamodel;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import ccmtools.JavaClientLib.templates.OperationAdapterFromCorbaTemplate;
import ccmtools.JavaClientLib.templates.OperationAdapterToCorbaTemplate;
import ccmtools.JavaClientLib.templates.OperationDeclarationTemplate;

public class OperationDefinition
	extends ModelElement
{
	private Type type;
	private List parameter = new ArrayList();
	private List exception = new ArrayList();
	
	
	public OperationDefinition(String identifier, Type type)
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
		if (getType() instanceof LongType)
		{
			LongType t = (LongType)getType();
			return t.generateJavaMapping(PassingDirection.RESULT);
		}
		else if (getType() instanceof StringType)
		{
			StringType t = (StringType)getType();
			return t.generateJavaMapping(PassingDirection.RESULT);
		}
		// TODO...
		else
		{
			throw new RuntimeException("generateOperationReturnType() - Unhandled type!");
		}
	}
	
	public String generateOperationParameterDeclarationList()
	{
		List parameterList = new ArrayList();
		for(Iterator i=getParameter().iterator(); i.hasNext();)
		{
			ParameterDefinition p = (ParameterDefinition)i.next();
			parameterList.add(p.generateParameter());
		}
		return joinList(", ", parameterList);
	}
	
	public String generateOperationParameterList()
	{
		List parameterList = new ArrayList();
		for(Iterator i=getParameter().iterator(); i.hasNext();)
		{
			ParameterDefinition p = (ParameterDefinition)i.next();
			parameterList.add(p.getIdentifier());
		}
		return joinList(", ", parameterList);
	}
	
	public String generateOperationExceptionList()
	{
		if(getException().size() == 0)
			return "";
		
		List exceptionList = new ArrayList();
		for (Iterator i = getException().iterator(); i.hasNext();)
		{
			ExceptionDefinition e = (ExceptionDefinition) i.next();
			exceptionList.add(e.getIdentifier());
		}
		return ", " + joinList(", ", exceptionList);
	}
}
