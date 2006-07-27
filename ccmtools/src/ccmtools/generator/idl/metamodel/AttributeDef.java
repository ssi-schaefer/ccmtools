package ccmtools.generator.idl.metamodel;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;


public class AttributeDef
	extends ModelElement
{
	/*************************************************************************
	 * IDL Model Implementation
	 *************************************************************************/
	
	private Type type;
	private boolean isReadonly;
	private List<ExceptionDef> getterExceptions = new ArrayList<ExceptionDef>();
    private List<ExceptionDef> setterExceptions = new ArrayList<ExceptionDef>();
    
	public AttributeDef(String identifier)
	{
		setIdentifier(identifier);
	}
		
	public Type getType()
	{
		return type;
	}

	public void setType(Type type)
	{
		this.type = type;
	}

    
	public boolean isReadonly()
	{
	    return isReadonly;
	}
	
	public void setReadonly(boolean isReadonly)
	{
	    this.isReadonly = isReadonly;
	}
	
    
    public List<ExceptionDef> getGetterExceptions()
    {
        return getterExceptions;
    }
    
    
    public List<ExceptionDef> getSetterExceptions()
    {
        return setterExceptions;
    }
	
    
    
	/*************************************************************************
	 * IDL3 Generator Methods Implementation
	 *************************************************************************/
	
	public String generateIdl3()
	{
		return generateAttribute(indent());
	}
    
    public Set<String> generateIncludePaths()
    {
        Set<String> includePaths = new TreeSet<String>();
        includePaths.add(getType().generateIncludePath());
        for(ExceptionDef ex : getGetterExceptions())
        {
            includePaths.add(ex.generateIncludePath());
        }       
        for(ExceptionDef ex : getSetterExceptions())
        {
            includePaths.add(ex.generateIncludePath());
        }       
        return includePaths;
    }
    
    public String generateAttribute(String indent)
    {
        StringBuilder code = new StringBuilder();
        code.append(indent);
        if(isReadonly())
        {
            code.append("readonly ");
        }
        code.append("attribute ");
        code.append(getType().generateIdlMapping()).append(" ").append(getIdentifier());
        if(getGetterExceptions().size() > 0)
        {
            code.append(NL).append(indent).append(TAB);
            code.append(" getraises(");
            code.append(Helper.generateExceptionList(getGetterExceptions()));
            code.append(")");
        }
        if(getGetterExceptions().size() > 0)
        {
            code.append(NL).append(indent).append(TAB);
            code.append(" setraises(");
            code.append(Helper.generateExceptionList(getSetterExceptions()));
            code.append(")");
        }
        code.append(";").append(NL);
        return code.toString();
    }
}
