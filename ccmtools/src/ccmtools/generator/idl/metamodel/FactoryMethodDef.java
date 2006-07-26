package ccmtools.generator.idl.metamodel;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import ccmtools.utils.Text;


public class FactoryMethodDef
	extends ModelElement
{
	/*************************************************************************
	 * IDL Model Implementation
	 *************************************************************************/
	
	private List<ParameterDef> parameter = new ArrayList<ParameterDef>();
	private List<ExceptionDef> exception = new ArrayList<ExceptionDef>();
	
	public FactoryMethodDef(String identifier)
	{
		setIdentifier(identifier);
	}
	
	
	public List<ExceptionDef> getExceptions()
	{
		return exception;
	}
	
	public List<ParameterDef> getParameters()
	{
		return parameter;
	}

	
	/*************************************************************************
	 * IDL3 Generator Methods Implementation
	 *************************************************************************/
	
	public String generateIdl3()
	{
		StringBuilder code = new StringBuilder();
		code.append("factory ").append(getIdentifier());
		code.append("(").append(generateParameterList()).append(")");
		if(getExceptions().size() > 0)
		{
			code.append(NL).append(TAB);
			code.append(" raises(").append(generateExceptionList()).append(")");
		}
		code.append(";").append(NL);
		return code.toString(); 
	}
	
	public Set<String> generateIncludePaths()
	{
		Set<String> includePaths = new TreeSet<String>();
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
		List<String> exceptionList = new ArrayList<String>();
		for(ExceptionDef ex : getExceptions())
		{
			exceptionList.add(ex.generateIdlMapping());	
		}
		return Text.join(", ", exceptionList);
	}
}
