package ccmtools.generator.idl.metamodel;

import java.util.List;

import ccmtools.generator.idl.templates.ArrayDefTemplate;
import ccmtools.generator.idl.templates.TypedefDefFileTemplate;
import ccmtools.generator.idl.templates.TypedefDefTemplate;


public class TypedefDef
	extends ModelElement
	implements Type
{
	/*************************************************************************
	 * IDL Model Implementation
	 *************************************************************************/
	
	private Type alias;
	
	public TypedefDef(String identifier, List<String> namespace)
	{
		super(identifier, namespace);
	}
	
	public void setAlias(Type alias)
	{
		this.alias = alias;
	}
	
	public Type getAlias()
	{
		return alias;
	}

	
	/*************************************************************************
	 * Type Interface Implementation
	 *************************************************************************/
	
	// Use ModelElement default implementations
	
	
	/*************************************************************************
	 * IDL3 Generator Methods Implementation
	 *************************************************************************/

	public String generateIdl3()
	{
		return new TypedefDefFileTemplate().generate(this);
	}
	
	public String generateIncludeStatements()
	{
		return generateIncludeStatement(getAlias().generateIncludePath());
	}
	
	public String generateTypedef()
	{
		if(getAlias() instanceof ArrayDef)
		{
			// The specific structure of an array definition (e.g. typedef long LongArray[10];
			// forces this special case...
			return new ArrayDefTemplate().generate(this);
		}
		else
		{
			return new TypedefDefTemplate().generate(this);
		}
	}
}
