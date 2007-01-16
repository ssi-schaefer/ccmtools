package ccmtools.CppGenerator.plugin;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import ccmtools.metamodel.BaseIDL.MAliasDef;
import ccmtools.metamodel.BaseIDL.MTypedefDef;
import ccmtools.utils.ConfigurationLocator;

public class PdlAnyPlugin
	implements AnyPlugin
{
	private AnyMapping defaultMapping = new DefaultAnyMapping();	

	private Map anyMappings = new HashMap();
	
	
	public void load()
	{
		createAnyMappings();
	}

	public void unload()
	{
	}
	
	
	public String generateCode(MAliasDef alias, String tag)
	{
		String identifier = ((MTypedefDef)alias).getIdentifier();
		String code;
		if(tag == null || tag.length() == 0)
		{
			code = "";
		}
		else if(tag.equals("TypedefInclude"))
		{
			code = findMapping(identifier).getIncludeCode(alias);
		}
		else if(tag.equals("TypedefDefinition"))
		{
			code = findMapping(identifier).getDefinitionCode(alias);
		}
		else
		{
			throw new RuntimeException("Unknown Tag: " + tag + " !");
		}		
		return code;
	}
	
	
	// Utility methods -------------------------------------------------------
	
    private AnyMapping findMapping(String identifier)
    {
        if(anyMappings.containsKey(identifier)) 
        {
            return (AnyMapping)anyMappings.get(identifier);
        }
        else 
        {            
            return defaultMapping;
        }
    }

	private void registerMapping(AnyMapping mapping)
	{
		if (mapping.getIdlTypeName() != null && mapping.getIdlTypeName().length() > 0)
		{
			System.out.println("    register any to " + mapping.getIdlTypeName() + " mapping");
			anyMappings.put(mapping.getIdlTypeName(), mapping);
		}
	}

	/**
     * Load a list of PDL types from a file and instantiate a PdlAnyMapping object
     * for each type.
     */ 
    private void createAnyMappings()
    {
   		File file = new File(ConfigurationLocator.getInstance().get("ccmtools.dir.plugin.any.types"));
        List pdlTypeList = loadPdlTypes(file);
        for(Iterator i = pdlTypeList.iterator(); i.hasNext();) 
        {
            PdlType type = (PdlType)i.next();
            registerMapping(new PdlAnyMapping(type));            
        }	
    }
    
    private List loadPdlTypes(File file)
    {
        System.out.println("> load any mappings from: " + file);     

    		List list = new ArrayList();
    		try 
    		{
    			if(file.exists()) 
    			{
                BufferedReader in = new BufferedReader(new FileReader(file));
                String line;
                while((line = in.readLine()) != null) 
                {
                    String typeNameLine = line.trim();
                    if(typeNameLine.startsWith("#"))
                    {
                    		// ignore comments
                    }
                    else if(typeNameLine.length() > 0) 
                    {
                    		PdlType type = new PdlType(typeNameLine);
                        list.add(type);
                    }
                }
                in.close();
    			}
    		}
        catch(Exception e) 
        {
        		e.printStackTrace();
        }
        return list;
    }
}