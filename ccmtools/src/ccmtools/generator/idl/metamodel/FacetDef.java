package ccmtools.generator.idl.metamodel;

import java.util.List;

public class FacetDef
	extends ModelElement
{
	/*************************************************************************
	 * IDL Model Implementation
	 *************************************************************************/
	
	private ComponentDef component;
	private InterfaceDef iface;
	
	public FacetDef(String identifier, List<String> ns)
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
	
	public void setInterface(InterfaceDef provides)
	{
		this.iface = provides;
	}
		
	
	/*************************************************************************
	 * IDL3 Generator Methods Implementation
	 *************************************************************************/
	
	public String generateIdl3()
	{
		return "provides " + getInterface().generateIdlMapping() + " " + getIdentifier() + ";" + NL; 
	}
    
    
    /*************************************************************************
     * IDL3 Mirror Generator Methods Implementation
     *************************************************************************/ 

    public String generateIdl3Mirror()
    {
        return "uses " + getInterface().generateIdlMapping() + " " + getIdentifier() + ";" + NL; 
    }


    /*************************************************************************
     * IDL2 Generator Methods Implementation
     *************************************************************************/
    
    public String generateIdl2()
    {
        return getInterface().generateIdlMapping() + " provide_" + getIdentifier() + "();" + NL; 
    }

}
