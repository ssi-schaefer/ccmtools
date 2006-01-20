package ccmtools.generator.java.clientlib.metamodel;

import java.util.ArrayList;
import java.util.List;

public class ExceptionDef
	extends ModelElement
	implements Type
{
	List parameter = new ArrayList();

	public ExceptionDef(String identifier, List ns)
	{
		setIdentifier(identifier);
		setIdlNamespaceList(ns);	
	}
		
	public List getParameter()
	{
		return parameter;
	}
	
	public String generateJavaMapping()
	{
		return generateJavaMapping(PassingDirection.IN);
	}
	
	public String generateJavaMapping(PassingDirection direction)
	{
		if(direction == PassingDirection.IN
			|| direction == PassingDirection.RESULT)
		{
			return getAbsoluteIdlName();
		}
		else
		{
			return getAbsoluteIdlName() + "Holder";
		}	
	}
}
