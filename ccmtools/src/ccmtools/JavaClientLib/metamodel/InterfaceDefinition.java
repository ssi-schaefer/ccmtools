package ccmtools.JavaClientLib.metamodel;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import ccmtools.JavaClientLib.templates.InterfaceAdapterFromCorbaTemplate;
import ccmtools.JavaClientLib.templates.InterfaceAdapterToCorbaTemplate;
import ccmtools.JavaClientLib.templates.InterfaceDeclarationTemplate;

public class InterfaceDefinition
	extends ModelElement
{
	private List attribute = new ArrayList();
	private List operation = new ArrayList();

	
	public InterfaceDefinition(String identifier, List namespace)
	{
		setIdentifier(identifier);
		setNamespace(namespace);
	}
	
		
	// Model methods ----------------------------------------------------------
	
	public List getAttribute()
	{
		return attribute;
	}

	public List getOperation()
	{
		return operation;
	}	
	
	
	// Code generator methods -------------------------------------------------
	
	public String generateInterfaceDeclaration()
	{
		return new InterfaceDeclarationTemplate().generate(this);
	}
	
	public String generateInterfaceAdapterFromCorba()
	{
		return new InterfaceAdapterFromCorbaTemplate().generate(this);
	}
	
	public String generateInterfaceAdapterToCorba()
	{
		return new InterfaceAdapterToCorbaTemplate().generate(this);
	}

	
	public String generateConstantDeclarationList()
	{
		StringBuffer code = new StringBuffer();
		// TODO
		return code.toString();
	}
	
	public String generateAttributeDeclarationList()
	{
		StringBuffer code = new StringBuffer();
		// TODO
		return code.toString();
	}
	
	public String generateOperationDeclarationList()
	{
		StringBuffer code = new StringBuffer();
		for(Iterator i=getOperation().iterator(); i.hasNext();)
		{
			OperationDefinition op = (OperationDefinition)i.next();
			code.append(op.generateOperationDeclaration());
		}
		return code.toString();
	}
	
	public String generateOperationAdapterFromCorbaList()
	{
		StringBuffer code = new StringBuffer();
		for(Iterator i=getOperation().iterator(); i.hasNext();)
		{
			OperationDefinition op = (OperationDefinition)i.next();
			code.append(op.generateOperationAdapterFromCorba());
		}
		return code.toString();
	}
	
	public String generateOperationAdapterToCorbaList()
	{
		StringBuffer code = new StringBuffer();
		for(Iterator i=getOperation().iterator(); i.hasNext();)
		{
			OperationDefinition op = (OperationDefinition)i.next();
			code.append(op.generateOperationAdapterToCorba());
		}
		return code.toString();
	}
}
