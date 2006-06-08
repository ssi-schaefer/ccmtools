package ccmtools.CppGenerator.plugin;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import ccmtools.utils.CcmtoolsProperties;

public class PdlAnyPlugin
	implements AnyPlugin
{
	public void load()
	{				
	}

	public void unload()
	{
	}
	
	
	/**
     * Load a list of PDL types from a file and instantiate a PdlAnyMapping object
     * for each type.
     */ 
    public List getAnyMappings()
    {
   		File file = new File(CcmtoolsProperties.Instance().get("ccmtools.dir.plugin.any.types"));
        System.out.println("> load any mappings from: " + file);     
        List mappingList = new ArrayList();
        List pdlTypeList = loadPdlTypes(file);
        for(Iterator i = pdlTypeList.iterator(); i.hasNext();) 
        {
            PdlType type = (PdlType)i.next();
            mappingList.add(new PdlAnyMapping(type));            
        }	
        return mappingList;
    }
    
    protected List loadPdlTypes(File file)
    {
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