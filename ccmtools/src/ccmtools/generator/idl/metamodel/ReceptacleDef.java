package ccmtools.generator.idl.metamodel;

import java.util.List;

import ccmtools.generator.idl.templates.Idl2MultipleConnectionTemplate;
import ccmtools.generator.idl.templates.Idl2SingleConnectionTemplate;

public class ReceptacleDef
	extends ModelElement
{
	/*************************************************************************
	 * IDL Model Implementation
	 *************************************************************************/
	
	private ComponentDef component;
	private InterfaceDef iface;
	private boolean multiple;
	
	public ReceptacleDef(String identifier, List<String> ns)
	{
		super(identifier, ns);
	}

	
	public ComponentDef getComponent()
	{
		return component;
	}

	public void setComponent(ComponentDef component)
	{
		this.component = component;
	}

	
	public InterfaceDef getInterface()
	{
		return iface;
	}

	public void setInterface(InterfaceDef iface)
	{
		this.iface = iface;
	}
	

	public boolean isMultiple()
	{
		return multiple;
	}

	public void setMultiple(boolean multiple)
	{
		this.multiple = multiple;
	}

	
	/*************************************************************************
	 * IDL3 Generator Methods Implementation
	 *************************************************************************/
	
	public String generateIdl3()
	{
		StringBuilder code = new StringBuilder();
		code.append("uses ");
		if(isMultiple())
		{
			code.append("multiple ");
		}
		code.append(getInterface().generateIdlMapping()).append(" ").append(getIdentifier());
		code.append(";").append(NL);
		return code.toString();
	}

    
    
    /*************************************************************************
     * IDL3 Mirror Generator Methods Implementation
     *************************************************************************/ 

    public String generateIdl3Mirror(String indent)
    {
        StringBuilder code = new StringBuilder();
        if(isMultiple())
        {
            for(int i = 0; i < 3; i++)
            {
                code.append(indent).append(TAB);
                code.append(generateIdl3MirrorReceptacle(getIdentifier() + i));
            }
        }
        else
        {
            code.append(indent).append(TAB);
            code.append(generateIdl3MirrorReceptacle(getIdentifier()));
        }
        return code.toString();
    }

    public String generateIdl3MirrorReceptacle(String identifier)
    {
        return "provides " + getInterface().generateIdlMapping() + " " + identifier + ";" + NL;
    }        
    
    
    /*************************************************************************
     * IDL2 Generator Methods Implementation
     *************************************************************************/
    
    public String generateIdl2()
    {
        if(isMultiple())
        {
            return new Idl2MultipleConnectionTemplate().generate(this);
        }
        else
        {
            return new Idl2SingleConnectionTemplate().generate(this);            
        }
    }
}
