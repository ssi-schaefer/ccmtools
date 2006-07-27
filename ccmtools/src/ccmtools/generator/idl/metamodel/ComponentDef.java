package ccmtools.generator.idl.metamodel;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import ccmtools.generator.idl.templates.ComponentDefTemplate;
import ccmtools.utils.Text;

public class ComponentDef
	extends ModelElement
	implements Type
{
	/*************************************************************************
	 * IDL Model Implementation
	 *************************************************************************/
	
	private ComponentDef base;
	private List<AttributeDef> attributes = new ArrayList<AttributeDef>();
	private List<FacetDef> facet = new ArrayList<FacetDef>();
	private List<ReceptacleDef> receptacles = new ArrayList<ReceptacleDef>();
	private List<InterfaceDef> supports = new ArrayList<InterfaceDef>();
	private List<HomeDef> homes = new ArrayList<HomeDef>();
	
	
	public ComponentDef(String identifier, List<String> namespace)
	{
		super(identifier, namespace);
	}
	

	public ComponentDef getBase()
	{
		return base;
	}

	public void setBase(ComponentDef value)
	{
		this.base = value;
	}
	
	
	public List<AttributeDef> getAttributes()
	{
		return attributes;
	}
	
	public List<FacetDef> getFacets()
	{
		return facet;
	}
	
	public List<ReceptacleDef> getReceptacles()
	{
		return receptacles;
	}
	
	public List<InterfaceDef> getSupports()
	{
		return supports;
	}

	public List<HomeDef> getHomes()
	{
		return homes;
	}
	
	
	/*************************************************************************
	 * IDL3 Generator Methods Implementation
	 *************************************************************************/
	
	public String generateIdl3()
	{
		return new ComponentDefTemplate().generate(this); 
	}
	
	public String generateIncludeStatements()
	{
		Set<String> includePaths = new TreeSet<String>();
		if(getBase() != null)
		{
			includePaths.add(getBase().generateIncludePath());
		}
		for(AttributeDef attr: getAttributes())
		{
			includePaths.addAll(attr.generateIncludePaths());
		}
		for(InterfaceDef supportedIface : getSupports())
		{
			includePaths.add(supportedIface.generateIncludePath());
		}
		for(FacetDef facet : getFacets())
		{
			includePaths.add(facet.getInterface().generateIncludePath());
		}
		for(ReceptacleDef receptacle : getReceptacles())
		{
			includePaths.add(receptacle.getInterface().generateIncludePath());
		}
		// ...
		return generateIncludeStatements(includePaths);
	}
	
    public String generateAttributes()
    {
        StringBuilder code = new StringBuilder();
        for(AttributeDef attribte : getAttributes())
        {
            code.append(attribte.generateAttribute(indent() + TAB));
        }
        return code.toString();
    }
    
	public String generateSupportedInterfaces()
	{
		StringBuilder code = new StringBuilder();
		if(getSupports().size() > 0)
		{
			List<String> supportsList = new ArrayList<String>();
			code.append("supports ");
			for(InterfaceDef iface : getSupports())
			{
				supportsList.add(iface.generateAbsoluteIdlName());
			}
			code.append(Text.join(", ", supportsList));
		}
		return code.toString();
	}
	
	public String generateProvidedInterfaces()
	{
		StringBuilder code = new StringBuilder();
		for(FacetDef facet : getFacets())
		{
			code.append(indent()).append(TAB).append(facet.generateIdl3());
		}
		return code.toString();
	}
	
	public String generateUsedInterfaces()
	{
		StringBuilder code = new StringBuilder();
		for(ReceptacleDef receptacle : getReceptacles())
		{
			code.append(indent()).append(TAB).append(receptacle.generateIdl3());
		}
		return code.toString();
	}
	
	public String generateBaseComponent()
	{
		StringBuilder code = new StringBuilder();
		if(getBase() != null)
		{
			code.append(indent()).append(TAB).append(": ").append(getBase().generateIdlMapping());
		}
		return code.toString();
	}
	
    public String generateCppGeneratorHack()
    {
        StringBuilder code = new StringBuilder();
        if(getHomes() != null)
        {
            if(getHomes().size() > 0)
            {
                HomeDef home = getHomes().get(0); // take the first you can find...
                code.append(generateIncludeStatement(home.generateIncludePath()));
            }
        }
        return code.toString();
    }
    
}