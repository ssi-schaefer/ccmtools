package ccmtools.generator.java.metamodel;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import ccmtools.generator.java.templates.OperationDefAdapterFromCorbaTemplate;
import ccmtools.generator.java.templates.OperationDefAdapterLocalTemplate;
import ccmtools.generator.java.templates.OperationDefAdapterToCorbaTemplate;
import ccmtools.generator.java.templates.OperationDefApplicationImplementationTemplate;
import ccmtools.generator.java.templates.OperationDefCatchStatementAdapterFromCorbaTemplate;
import ccmtools.generator.java.templates.OperationDefCatchStatementAdapterToCorbaTemplate;
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
	
	// Code generator methods -------------------------------------------------

	public String generateDeclaration()
	{
		return new OperationDefDeclarationTemplate().generate(this);
	}
		
	public String generateReturnType()
	{		
		return getType().generateJavaMapping(PassingDirection.RESULT);
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
		List parameterList = new ArrayList();
		for(Iterator i=getParameter().iterator(); i.hasNext();)
		{
			ParameterDef p = (ParameterDef)i.next();
			parameterList.add(p.getIdentifier());
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
	
	// Code generator methods -------------------------------------------------

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
		
	public String generateCatchStatementAdapterToCorba()
	{
		return new OperationDefCatchStatementAdapterToCorbaTemplate().generate(this);
	}
	
	public String generateCatchStatementAdapterFromCorba()
	{
		return new OperationDefCatchStatementAdapterFromCorbaTemplate().generate(this);
	}
}
