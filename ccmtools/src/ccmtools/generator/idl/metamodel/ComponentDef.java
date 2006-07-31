package ccmtools.generator.idl.metamodel;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import ccmtools.generator.idl.templates.ComponentDefMirrorTemplate;
import ccmtools.generator.idl.templates.ComponentDefTemplate;
import ccmtools.utils.SourceFile;
import ccmtools.utils.Text;
import ccmtools.utils.Utility;

public class ComponentDef
	extends InterfaceDef
	implements Type, Idl3MirrorGenerator
{
	/*************************************************************************
	 * IDL Model Implementation
	 *************************************************************************/
	
	private ComponentDef baseComponent;
	private List<FacetDef> facet = new ArrayList<FacetDef>();
	private List<ReceptacleDef> receptacles = new ArrayList<ReceptacleDef>();
	private List<InterfaceDef> supports = new ArrayList<InterfaceDef>();
	private List<HomeDef> homes = new ArrayList<HomeDef>();
	
	
	public ComponentDef(String identifier, List<String> namespace)
	{
		super(identifier, namespace);
	}
	

	public ComponentDef getBaseComponent()
	{
		return baseComponent;
	}

	public void setBaseComponent(ComponentDef value)
	{
		this.baseComponent = value;
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
     * Type Interface Implementation
     *************************************************************************/
    
    // Use ModelElement default implementations

    
    
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
		if(getBaseComponent() != null)
		{
			includePaths.add(getBaseComponent().generateIncludePath());
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
		if(getBaseComponent() != null)
		{
			code.append(indent()).append(TAB).append(": ").append(getBaseComponent().generateIdlMapping());
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


    
    /*************************************************************************
     * IDL3 Mirror Generator Methods Implementation
     *************************************************************************/   
        
    public String generateIdl3Mirror()
    {        
        return new ComponentDefMirrorTemplate().generate(this); 
    }

    public String generateIdl3MirrorIncludePath()
    {
        if(getIdlNamespaceList().size() == 0)
        {
            return getIdentifier() + "Mirror";
        }
        else
        {
            return Text.joinList("/", getIdlNamespaceList()) + "/" + getIdentifier() + "Mirror";
        }
    }
    
    public String generateIdl3MirrorProvidedInterfaces()
    {
        StringBuilder code = new StringBuilder();
        for(FacetDef facet : getFacets())
        {
            code.append(indent()).append(TAB).append(facet.generateIdl3Mirror());
        }
        return code.toString();
    }
    
    public String generateIdl3MirrorUsedInterfaces()
    {
        StringBuilder code = new StringBuilder();
        for(ReceptacleDef receptacle : getReceptacles())
        {
            code.append(receptacle.generateIdl3Mirror(indent()));
        }
        return code.toString();
    }

    
    public String generateIdl3MirrorCppGeneratorHack()
    {
        StringBuilder code = new StringBuilder();
        if(getHomes() != null)
        {
            if(getHomes().size() > 0)
            {
                HomeDef home = getHomes().get(0); // take the first you can find...
                code.append(generateIncludeStatement(home.generateIncludePath() + "Mirror"));
            }
        }
        return code.toString();
    }
    
    public List<SourceFile> generateIdl3MirrorSourceFiles()
    {
        List<SourceFile> sourceFileList = new ArrayList<SourceFile>();
        String packageName;
        packageName = COMPONENT_PREFIX 
                + File.separator + Text.joinList(File.separator, getIdlNamespaceList());
        String sourceCode = Utility.removeEmptyLines(generateIdl3Mirror());
        SourceFile sourceFile = new SourceFile(packageName, getIdentifier() + "Mirror.idl", sourceCode);
        sourceFileList.add(sourceFile);     
        return sourceFileList;    
    }    
}