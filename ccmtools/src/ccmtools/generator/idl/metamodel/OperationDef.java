package ccmtools.generator.idl.metamodel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import ccmtools.utils.Text;


public class OperationDef
	extends ModelElement
{
	private Type type;
	private List<ParameterDef> parameter = new ArrayList<ParameterDef>();
	private List<ExceptionDef> exception = new ArrayList<ExceptionDef>();
	private boolean oneway;
	private List<String> contexts;
	
	public OperationDef(String identifier)
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
		
	public List<ExceptionDef> getExceptions()
	{
		return exception;
	}
	
	public List<ParameterDef> getParameters()
	{
		return parameter;
	}

	public void setOneway(boolean value)
	{
		this.oneway = value;
	}
	
	public boolean isOneway()
	{
		return oneway;
	}
	
	public void setContext(String value)
	{
		if(value != null)
		{
			String[] list = value.split(",");
			contexts = new ArrayList<String>();
			for(int i=0; i< list.length; i++)
			{
				contexts.add("\"" + list[i] + "\"");
			}
		}
	}
	
	public List<String> getContexts()
	{
		return contexts;
	}
	
	
	/*************************************************************************
	 * IDL3 generator methods
	 *************************************************************************/
	
	public Set<String> generateIncludePaths()
	{
		Set<String> includePaths = new TreeSet<String>();
		includePaths.add(getType().generateIncludePath());
		for(ParameterDef parameter : getParameters())
		{
			includePaths.add(parameter.generateIncludePath());
		}
		for(ExceptionDef ex : getExceptions())
		{
			includePaths.add(ex.generateIncludePath());
		}		
		return includePaths;
	}
	
	public String generateIdl3Code()
	{
		StringBuilder code = new StringBuilder();
		if(isOneway())
		{
			code.append("oneway ");
		}
		code.append(getType().generateIdlMapping()).append(" ").append(getIdentifier());
		code.append("(").append(generateParameterList()).append(")");
		if(getExceptions().size() > 0)
		{
			code.append(NL).append(TAB);
			code.append(" raises(").append(generateExceptionList()).append(")");
		}
		if(getContexts() != null && getContexts().size() > 0)
		{
			code.append(NL).append(TAB);
			code.append("context(").append(Text.joinList(", ", getContexts())).append(")");
		}
		code.append(";").append(NL);
		return code.toString(); 
	}
	
	public String generateParameterList()
	{
		List<String> parameterList = new ArrayList<String>();
		for(ParameterDef param : getParameters())
		{
			parameterList.add(param.generateIdl3Code());	
		}
		return Text.join(", ", parameterList);
	}
	
	public String generateExceptionList()
	{
		List<String> exceptionList = new ArrayList<String>();
		for(ExceptionDef ex : getExceptions())
		{
			exceptionList.add(ex.generateIdlMapping());	
		}
		return Text.join(", ", exceptionList);
	}
}
