package ccmtools.generator.java.metamodel;

import java.util.Set;
import java.util.TreeSet;

public class FixedType
	implements Type
{
	public Set<String> getJavaImportStatements()
	{
		Set<String> importStatements = new TreeSet<String>();
		importStatements.add(generateJavaMapping());
		return importStatements;
	}

	
	/*************************************************************************
	 * Local Interface Generator Methods
	 *************************************************************************/
	
	public String generateJavaConstant(Object value)
	{
		return value.toString();
	}
	
	public String generateJavaMapping()
	{
		return "java.math.BigDecimal";
	}		
	
	public String generateJavaMapping(PassingDirection direction)
	{
		if (direction == PassingDirection.IN
			|| direction == PassingDirection.RESULT)
		{
			return generateJavaMapping();
		}
		else // INOUT, OUT
		{
			return generateJavaHolderType();
		}
	}
	
	public String generateJavaMappingObject()
	{
		return generateJavaMapping();
	}
	
	public String generateJavaHolderType()
	{
		return "Holder<" + generateJavaMappingObject() + ">";
	}
	
	
	
	/*************************************************************************
	 * Application Generator Methods
	 *************************************************************************/
	
	public String generateJavaDefaultReturnValue()
	{
		return "null";		
	}
	
	
	/*************************************************************************
	 * CORBA Adapter Generator Methods
	 *************************************************************************/

	public String generateCorbaMapping()
	{
		return "java.math.BigDecimal";
	}
	
	public String generateCorbaMapping(PassingDirection direction)
	{
		if (direction == PassingDirection.IN
			|| direction == PassingDirection.RESULT)
		{
			return generateCorbaMapping();
		}
		else // INOUT, OUT
		{
			return generateCorbaHolderType();
		}
	}		
	
	public String generateCorbaHolderType()
	{
		return "org.omg.CORBA.FixedHolder";
	}
	
	public String generateCorbaConverterType()
	{
		return "";
	}
}
