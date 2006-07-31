package ccmtools.generator.idl.metamodel;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import ccmtools.utils.Text;


public class OperationDef
	extends ModelElement
{
	/*************************************************************************
	 * IDL Model Implementation
	 *************************************************************************/
	
	private Type type;
	private List<ParameterDef> parameters = new ArrayList<ParameterDef>();
	private List<ExceptionDef> exceptions = new ArrayList<ExceptionDef>();
	private boolean oneway;
	private List<String> contexts;
	
	public OperationDef(String identifier)
	{
		setIdentifier(identifier);
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
		return exceptions;
	}
	
	public List<ParameterDef> getParameters()
	{
		return parameters;
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
	 * IDL3 Generator Methods Implementation
	 *************************************************************************/
	
	public String generateIdl3()
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
	
	public Set<String> generateIncludePaths()
	{
		Set<String> includePaths = new TreeSet<String>();
        if(getType() != null)
        {
            includePaths.add(getType().generateIncludePath());
        }
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
	
	public String generateParameterList()
	{
		List<String> parameterList = new ArrayList<String>();
		for(ParameterDef param : getParameters())
		{
			parameterList.add(param.generateIdl3());	
		}
		return Text.join(", ", parameterList);
	}
	
	public String generateExceptionList()
	{        
        return Helper.generateExceptionList(exceptions);
	}
}
