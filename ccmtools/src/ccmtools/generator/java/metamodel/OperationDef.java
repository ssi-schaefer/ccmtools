package ccmtools.generator.java.metamodel;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import ccmtools.generator.java.templates.OperationDefAdapterFromCorbaTemplate;
import ccmtools.generator.java.templates.OperationDefAdapterLocalTemplate;
import ccmtools.generator.java.templates.OperationDefAdapterToCorbaTemplate;
import ccmtools.generator.java.templates.OperationDefApplicationImplementationTemplate;
import ccmtools.generator.java.templates.OperationDefAssemblyImplementationTemplate;
import ccmtools.generator.java.templates.OperationDefCatchStatementConverterFromCorbaTemplate;
import ccmtools.generator.java.templates.OperationDefCatchStatementConverterToCorbaTemplate;
import ccmtools.generator.java.templates.OperationDefDeclarationTemplate;
import ccmtools.utils.Text;

public class OperationDef
	extends ModelElement
{
	private Type type;
	private List<ParameterDef> parameter = new ArrayList<ParameterDef>();
	private List<ExceptionDef> exception = new ArrayList<ExceptionDef>();
	
	
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
	
	
	public List<ExceptionDef> getException()
	{
		return exception;
	}

	
	public List<ParameterDef> getParameter()
	{
		return parameter;
	}

	
	public Set<String> getJavaImportStatements()
	{
		Set<String> importStatements = getType().getJavaImportStatements();
		for(ExceptionDef ex : getException())
		{
			importStatements.addAll(ex.getJavaImportStatements());
		}
		for(ParameterDef param : getParameter())
		{
			importStatements.addAll(param.getJavaImportStatements());
		}	
		return importStatements;
	}
	
	
	/*************************************************************************
	 * Local Interface Generator Methods
	 *************************************************************************/
	
	public String generateDeclaration()
	{
		return new OperationDefDeclarationTemplate().generate(this);
	}
		
	public String generateReturnType()
	{		
		return getType().generateJavaMapping();
	}
	
	public String generateReturnStatement()
	{
		if(getType() instanceof VoidType)
		{
			return "";
		}
		else
		{
			return "return";
		}
	}
	
	/**
	 * Generate a parameter list (with types and names) and handle the commas
	 * in a propper way.
	 * e.g. "int p1, org.omg.CORBA.IntHolder p2, org.omg.CORBA.IntHolder p3"
	 * 
	 * @return Generated code artifact.
	 */
	public String generateParameterDeclarationList()
	{
		List<String> parameterList = new ArrayList<String>();
		for(ParameterDef p : getParameter())
		{
			parameterList.add(p.generateParameter());
		}
		if(parameterList.size() > 1)
		{
			return NL + TAB3 + Text.joinList("," + NL + TAB3, parameterList);
		}
		else
		{
			return Text.joinList(", ", parameterList);
		}
	}
		
	/**
	 * Generate a parameter list (names only), and handle the commas in
	 * a propper way.
	 * e.g. "p1, p2, p3"
	 * 
	 * @return Generated code artifact.
	 */
	public String generateParameterList()
	{
		return generateParameterList("");
	}
		
	public String generateParameterList(String suffix)
	{
		List<String> parameterList = new ArrayList<String>();
		for(ParameterDef p : getParameter())
		{
			parameterList.add(p.getIdentifier() + suffix);
		}
		return Text.joinList(", ", parameterList);
	}
	

	/**
	 * Generate an exception list (names only), and handle the commas in
	 * a propper way. 
	 * e.g. ", InvalidName, NoConnection"
	 * 
	 * @return Generated code artifact.
	 */
	public String generateThrowsStatementLocal()
	{
		StringBuilder code = new StringBuilder();
		code.append("throws Components.CCMException");
		if(getException().size() != 0)
		{
			code.append(",").append(NL).append(TAB3);
			List<String> exceptionList = new ArrayList<String>();
			for (ExceptionDef e : getException())
			{
				exceptionList.add(e.generateJavaMapping());
			}
			code.append(Text.joinList(", " + NL + TAB3, exceptionList));
		}
		return code.toString();
	}

	
	
	
	/*************************************************************************
	 * Local Adapter Generator Methods
	 *************************************************************************/
	
	// Code generator methods -------------------------------------------------
	
	public String generateAdapterLocal()
	{              
		return new OperationDefAdapterLocalTemplate().generate(this);
	}
	
	
	
	
	/*************************************************************************
	 * Application Generator Methods
	 *************************************************************************/
	
	// Generator methods ------------------------------------------------------
		
	public String generateApplicationImplementation()
	{
		return new OperationDefApplicationImplementationTemplate().generate(this);
	}
	
	public String generateDefaultReturnStatement()
	{
		if(getType() instanceof VoidType)
		{
			return "";
		}
		else
		{
			return "return " + getType().generateJavaDefaultReturnValue() + ";";
		}
	}
    
    public String generateAssemblyImplementation()
    {
        return new OperationDefAssemblyImplementationTemplate().generate(this);
    }
	
    public String generateAssemblyReturnStatement()
    {
        StringBuffer code = new StringBuffer();
        if(!(getType() instanceof VoidType))
            code.append("return ");
        code.append("this.target.");
        code.append(getIdentifier());
        code.append("(");
        code.append(generateParameterList());
        code.append(");");
        return code.toString();
    }
	

	/*************************************************************************
	 * CORBA Adapter Generator Methods
	 *************************************************************************/
	
	public String generateCorbaReturnType()
	{		
		return getType().generateCorbaMapping();
	}
	
	public String generateCorbaParameterDeclarationList()
	{
		List<String> parameterList = new ArrayList<String>();
		for(ParameterDef p : getParameter())
		{
			parameterList.add(p.generateCorbaParameter());
		}
		if(parameterList.size() > 1)
		{
			return NL + TAB3 + Text.joinList(", " + NL + TAB3 , parameterList);
		}
		else
		{
			return Text.joinList(", ", parameterList);
		}
	}
	
	public String generateAdapterFromCorba()
	{
		return new OperationDefAdapterFromCorbaTemplate().generate(this);
	}
	
	public String generateAdapterToCorba()
	{
		return new OperationDefAdapterToCorbaTemplate().generate(this);
	}

	public String generateThrowsStatementFromCorba()
	{
		StringBuilder code = new StringBuilder();
		if(getException().size() != 0)
		{
			code.append("throws ");
			List<String> exceptionList = new ArrayList<String>();
			for (ExceptionDef e : getException())
			{
				exceptionList.add(e.generateCorbaMapping());
			}
			if(exceptionList.size() > 1)
			{
				code.append(Text.joinList(", " + NL + TAB3 , exceptionList));
			}
			else
			{
				code.append(Text.joinList(", ", exceptionList));
			}
		}
		return code.toString();
	}
	
	
	public String generateCatchStatementConverterToCorba()
	{
		return new OperationDefCatchStatementConverterToCorbaTemplate().generate(this);
	}
	
	public String generateCatchStatementConverterFromCorba()
	{
		return new OperationDefCatchStatementConverterFromCorbaTemplate().generate(this);
	}
	
	
	public String generateInParameterConvertersToCorba()
	{
		StringBuilder code = new StringBuilder();
		for(ParameterDef parameter : getParameter())
		{
			code.append(TAB3);
			code.append(parameter.generateInConverterToCorba());
			code.append(NL);
		}		
		return code.toString();
	}
	
	public String generateInParameterConvertersFromCorba()
	{
		StringBuilder code = new StringBuilder();
		for(ParameterDef parameter : getParameter())
		{
			code.append(TAB3);
			code.append(parameter.generateInConverterFromCorba());
			code.append(NL);
		}		
		return code.toString();
	}	
			
	
	public String generateOutParameterConvertersToCorba()
	{
		StringBuilder code = new StringBuilder();
		for(ParameterDef parameter : getParameter())
		{
			code.append(TAB3);
			code.append(parameter.generateOutConverterToCorba());
			code.append(NL);
		}		
		return code.toString();
	}
		
	public String generateOutParameterConvertersFromCorba()
	{
		StringBuilder code = new StringBuilder();
		for(ParameterDef parameter : getParameter())
		{
			code.append(TAB3);
			code.append(parameter.generateOutConverterFromCorba());
			code.append(NL);
		}		
		return code.toString();
	}
		
		
	public String generateMethodConverterToCorba()
	{
		if (!(getType() instanceof VoidType))
		{
			return "resultRemote = remoteInterface." + 
				getIdentifier() + "(" + generateParameterList("Remote") + ");";
		}
		else
		{
			return "remoteInterface." + 
				getIdentifier() + "(" + generateParameterList("Remote") + ");";
		}
	}
	
	public String generateMethodConverterFromCorba()
	{
		if (!(getType() instanceof VoidType))
		{
			return "resultLocal = localInterface." + 
				getIdentifier() + "(" + generateParameterList("Local") + ");";
		}
		else
		{
			return "localInterface." + 
				getIdentifier() + "(" + generateParameterList("Local") + ");";
		}
	}

	
	public String generateResultDeclaration()
	{
		if (!(getType() instanceof VoidType))
		{
			return TAB3 + getType().generateJavaMapping() + " resultLocal;";
		}
		else
		{
			return "";
		}
	}
	
	public String generateCorbaResultDeclaration()
	{
		if (!(getType() instanceof VoidType))
		{
			return TAB3 + getType().generateCorbaMapping() + " resultRemote;";
		}
		else
		{
			return "";
		}
	}
	
	public String generateResultConverterFromCorba()
	{
		StringBuilder code = new StringBuilder();
		if (!(getType() instanceof VoidType))
		{
			code.append(TAB3).append(getType().generateJavaMapping());
			code.append(" result;").append(NL);
			code.append(TAB3);
			code.append("result = " + getType().generateCorbaConverterType() + "(resultRemote);");
			code.append(NL);
			code.append(TAB3).append("return result;").append(NL);
		}
		return code.toString();
	}	
	
	public String generateResultConverterToCorba()
	{
		StringBuilder code = new StringBuilder();
		if (!(getType() instanceof VoidType))
		{
			code.append(TAB3).append(getType().generateCorbaMapping());
			code.append(" result;").append(NL);
			code.append(TAB3);
			code.append("result = " + getType().generateCorbaConverterType() + "(resultLocal);");
			code.append(NL);
			code.append(TAB3).append("return result;").append(NL);
		}
		return code.toString();
	}	
}
