package ccmtools.JavaClientLib.metamodel;

import java.util.ArrayList;
import java.util.List;

public class ExceptionDefinition
	extends ModelElement
{
	List parameter = new ArrayList();

	public ExceptionDefinition(String identifier)
	{
		setIdentifier(identifier);
	}
	
	public List getParameter()
	{
		return parameter;
	}
}
