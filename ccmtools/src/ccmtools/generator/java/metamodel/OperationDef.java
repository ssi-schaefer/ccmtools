package ccmtools.generator.java.metamodel;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import ccmtools.generator.java.templates.OperationDefAdapterFromCorbaTemplate;
import ccmtools.generator.java.templates.OperationDefAdapterLocalTemplate;
import ccmtools.generator.java.templates.OperationDefAdapterToCorbaTemplate;
import ccmtools.generator.java.templates.OperationDefApplicationImplementationTemplate;
import ccmtools.generator.java.templates.OperationDefCatchStatementConverterFromCorbaTemplate;
import ccmtools.generator.java.templates.OperationDefCatchStatementConverterToCorbaTemplate;
import ccmtools.generator.java.templates.OperationDefDeclarationTemplate;
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

	
	/*************************************************************************
	 * Local Interface Generator Methods
	 * 
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
		List parameterList = new ArrayList();
		for(Iterator i=getParameter().iterator(); i.hasNext();)
		{
			ParameterDef p = (ParameterDef)i.next();
			parameterList.add(p.generateParameter());
		}
		return Text.joinList(", ", parameterList);
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
		List parameterList = new ArrayList();
		for(Iterator i=getParameter().iterator(); i.hasNext();)
		{
			ParameterDef p = (ParameterDef)i.next();
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
		StringBuffer code = new StringBuffer();
		code.append("throws ccm.local.Components.CCMException");
		if(getException().size() != 0)
		{
			code.append(", ");
			List exceptionList = new ArrayList();
			for (Iterator i = getException().iterator(); i.hasNext();)
			{
				ExceptionDef e = (ExceptionDef) i.next();
				exceptionList.add(e.generateJavaMapping());
			}
			code.append(Text.joinList(", ", exceptionList));
		}
		return code.toString();
	}

	
	
	
	/*************************************************************************
	 * Local Component Generator Methods
	 * 
	 *************************************************************************/
	
	// Code generator methods -------------------------------------------------
	
	public String generateAdapterLocal()
	{              
		return new OperationDefAdapterLocalTemplate().generate(this);
	}
	
	
	
	
	/*************************************************************************
	 * Application Generator Methods
	 * 
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
	
	

	/*************************************************************************
	 * Client Library Generator Methods
	 * 
	 *************************************************************************/
	
	public String generateCorbaReturnType()
	{		
		return getType().generateCorbaMapping();
	}
	
	public String generateCorbaParameterDeclarationList()
	{
		List parameterList = new ArrayList();
		for(Iterator i=getParameter().iterator(); i.hasNext();)
		{
			ParameterDef p = (ParameterDef)i.next();
			parameterList.add(p.generateCorbaParameter());
		}
		return Text.joinList(", ", parameterList);
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
		StringBuffer code = new StringBuffer();
		if(getException().size() != 0)
		{
			code.append("throws ");
			List exceptionList = new ArrayList();
			for (Iterator i = getException().iterator(); i.hasNext();)
			{
				ExceptionDef e = (ExceptionDef) i.next();
				exceptionList.add(e.generateJavaMapping());
			}
			code.append(Text.joinList(", ", exceptionList));
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
		StringBuffer code = new StringBuffer();
		for(Iterator i = getParameter().iterator(); i.hasNext();)
		{
			ParameterDef parameter = (ParameterDef)i.next();
			code.append(TAB2);
			code.append(parameter.generateInConverterToCorba());
			code.append(NL);
		}		
		return code.toString();
	}
	
	public String generateInParameterConvertersFromCorba()
	{
		StringBuffer code = new StringBuffer();
		for(Iterator i = getParameter().iterator(); i.hasNext();)
		{
			ParameterDef parameter = (ParameterDef)i.next();
			code.append(TAB2);
			code.append(parameter.generateInConverterFromCorba());
			code.append(NL);
		}		
		return code.toString();
	}	
			
	
	public String generateOutParameterConvertersToCorba()
	{
		StringBuffer code = new StringBuffer();
		for(Iterator i = getParameter().iterator(); i.hasNext();)
		{
			ParameterDef parameter = (ParameterDef)i.next();
			code.append(TAB2);
			code.append(parameter.generateOutConverterToCorba());
			code.append(NL);
		}		
		return code.toString();
	}
		
	public String generateOutParameterConvertersFromCorba()
	{
		StringBuffer code = new StringBuffer();
		for(Iterator i = getParameter().iterator(); i.hasNext();)
		{
			ParameterDef parameter = (ParameterDef)i.next();
			code.append(TAB2);
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
			return TAB2 + getType().generateJavaMapping() + " resultLocal;";
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
			return TAB2 + getType().generateCorbaMapping() + " resultRemote;";
		}
		else
		{
			return "";
		}
	}
	
	public String generateResultConverterFromCorba()
	{
		StringBuffer code = new StringBuffer();
		if (!(getType() instanceof VoidType))
		{
			code.append(TAB2).append(getType().generateJavaMapping());
			code.append(" result;").append(NL);
			code.append(TAB2);
			code.append("result = " + getType().generateCorbaConverterType() + "(resultRemote);");
			code.append(NL);
			code.append(TAB2).append("return result;").append(NL);
		}
		return code.toString();
	}	
	
	public String generateResultConverterToCorba()
	{
		StringBuffer code = new StringBuffer();
		if (!(getType() instanceof VoidType))
		{
			code.append(TAB2).append(getType().generateCorbaMapping());
			code.append(" result;").append(NL);
			code.append(TAB2);
			code.append("result = " + getType().generateCorbaConverterType() + "(resultLocal);");
			code.append(NL);
			code.append(TAB2).append("return result;").append(NL);
		}
		return code.toString();
	}	
}
