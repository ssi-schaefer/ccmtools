package ccmtools.generator.idl.metamodel;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import ccmtools.generator.idl.templates.HomeDefTemplate;
import ccmtools.utils.Text;

public class HomeDef
	extends InterfaceDef
{
	/*************************************************************************
	 * IDL Model Implementation
	 *************************************************************************/
	
    private HomeDef baseHome; 
	private ComponentDef component;
//    private List<AttributeDef> attributes = new ArrayList<AttributeDef>(); 
    private List<InterfaceDef> supports = new ArrayList<InterfaceDef>();
	private List<FactoryMethodDef> factories = new ArrayList<FactoryMethodDef>();
    
	public HomeDef(String identifier, List<String> namespace)
	{
		super(identifier, namespace);
	}

    
	public HomeDef getBaseHome()
    {
        return baseHome;
    }

    public void setBaseHome(HomeDef base)
    {
        this.baseHome = base;
    }
//
//    
//    public List<AttributeDef> getAttributes()
//    {
//        return attributes;
//    }
    
    
    public List<InterfaceDef> getSupports()
    {
        return supports;
    }


    public ComponentDef getComponent()
	{
		return component;
	}

	public void setComponent(ComponentDef component)
	{
		this.component = component;
	}

    public List<FactoryMethodDef> getFactories()
    {
        return factories;
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
		return new HomeDefTemplate().generate(this); 
	}
	
	public String generateIncludeStatements()
	{
		Set<String> includePaths = new TreeSet<String>();
		includePaths.add(getComponent().generateIncludePath());
        if(getBaseHome() != null)
        {
            includePaths.add(getBaseHome().generateIncludePath());
        }
        for(AttributeDef attr: getAttributes())
        {
            includePaths.addAll(attr.generateIncludePaths());
        }
        for(InterfaceDef iface : getSupports())
        {
            includePaths.add(iface.generateIncludePath());
        }
        for(FactoryMethodDef factory : getFactories())
        {
            includePaths.addAll(factory.generateIncludePaths());
        }
		
		return generateIncludeStatements(includePaths);
	}
    
    public String generateBaseHome()
    {
        StringBuilder code = new StringBuilder();
        if(getBaseHome() != null)
        {
            code.append(indent()).append(TAB).append(": ").append(getBaseHome().generateIdlMapping());
        }
        return code.toString();
    }
    
//    public String generateAttributes()
//    {
//        StringBuilder code = new StringBuilder();
//        for(AttributeDef attribte : getAttributes())
//        {
//            code.append(attribte.generateAttribute(indent() + TAB));
//        }
//        return code.toString();
//    }
    
    public String generateSupportedInterfaces()
    {
        StringBuilder code = new StringBuilder();
        if(getSupports().size() > 0)
        {
            List<String> supportsList = new ArrayList<String>();
            code.append(indent()).append(TAB).append("supports ");
            for(InterfaceDef iface : getSupports())
            {
                supportsList.add(iface.generateAbsoluteIdlName());
            }
            code.append(Text.join(", ", supportsList));
        }
        return code.toString();
    }
    
    public String generateFactoryMethods()
    {
        StringBuilder code = new StringBuilder();
        for(FactoryMethodDef factory : getFactories())
        {
            code.append(indent()).append(TAB);
            code.append(factory.generateIdl3());
        }        
        return code.toString();
    }
}
