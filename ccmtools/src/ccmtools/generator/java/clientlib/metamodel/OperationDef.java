package ccmtools.generator.java.clientlib.metamodel;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import ccmtools.generator.java.clientlib.templates.CatchStatementFromCorbaTemplate;
import ccmtools.generator.java.clientlib.templates.CatchStatementToCorbaTemplate;
import ccmtools.generator.java.clientlib.templates.OperationAdapterFromCorbaTemplate;
import ccmtools.generator.java.clientlib.templates.OperationAdapterToCorbaTemplate;
import ccmtools.generator.java.clientlib.templates.OperationDeclarationTemplate;
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
	
	public String generateOperationReturnStatement()
	{
		if(getType() instanceof VoidType)
		{
			return "";
		}
		else
		{
			return "return ";
		}
	}
	
	/**
	 * Generate a parameter list (with types and names) and handle the commas
	 * in a propper way.
	 * e.g. "int p1, org.omg.CORBA.IntHolder p2, org.omg.CORBA.IntHolder p3"
	 * 
	 * @return Generated code artifact.
	 */
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
		
	/**
	 * Generate a parameter list (names only), and handle the commas in
	 * a propper way.
	 * e.g. "p1, p2, p3"
	 * 
	 * @return Generated code artifact.
	 */
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
	
	/**
	 * Generate an exception list (names only), and handle the commas in
	 * a propper way. 
	 * e.g. ", InvalidName, NoConnection"
	 * 
	 * @return Generated code artifact.
	 */
	public String generateThrowsToCorba()
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
				exceptionList.add(e.generateJavaMapping(PassingDirection.IN));
			}
			code.append(Text.joinList(", ", exceptionList));
		}
		return code.toString();
	}

	
	public String generateThrowsFromCorba()
	{
		StringBuffer code = new StringBuffer();
		if(getException().size() != 0)
		{
			code.append("throws ");
			List exceptionList = new ArrayList();
			for (Iterator i = getException().iterator(); i.hasNext();)
			{
				ExceptionDef e = (ExceptionDef) i.next();
				exceptionList.add(e.generateJavaMapping(PassingDirection.IN));
			}
			code.append(Text.joinList(", ", exceptionList));
		}
		return code.toString();
	}
	
	public String generateCatchStatementToCorba()
	{
		return new CatchStatementToCorbaTemplate().generate(this);
	}
	
	public String generateCatchStatementFromCorba()
	{
		return new CatchStatementFromCorbaTemplate().generate(this);
	}
	
	
}
