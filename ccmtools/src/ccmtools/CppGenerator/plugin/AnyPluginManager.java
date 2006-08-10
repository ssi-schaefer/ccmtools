package ccmtools.CppGenerator.plugin;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import ccmtools.CcmtoolsException;
import ccmtools.metamodel.BaseIDL.MAliasDef;
import ccmtools.metamodel.BaseIDL.MIDLType;
import ccmtools.metamodel.BaseIDL.MPrimitiveDef;
import ccmtools.metamodel.BaseIDL.MPrimitiveKind;

/**
 * This manager class hides all details of the any plugin mechanism from  
 * code generators.
 * 
 * 
 */
public class AnyPluginManager
{
    /** List of AnyPlugin classes */
    private String[] AnyPluginList = 
    {
    		"ccmtools.CppGenerator.plugin.PdlAnyPlugin" 
    	};
    // TODO: AnyPluginList should be read from a config file and all
    // plugin classes should be loaded with a sepearte ClassLoader.
    

	private List anyPlugins = new ArrayList();

	private List getPlugins()
    {
    		return anyPlugins;
    }
        
	
    public AnyPluginManager()
    		throws CcmtoolsException
    {
        for(int i = 0; i < AnyPluginList.length; i++)
        {
        		loadPlugin(AnyPluginList[i]);
        }
    }

    
    /**
     * Check if a typedef (given as an IDLType model element)
     * represents a mapping to a any primitive type.
     *  
     * @param idlType
     * @return If the given typedef represents a mapping to any
     *         this method returns true.
     */
    public boolean isTypedefToAny(MIDLType idlType)
    {
        if(idlType instanceof MPrimitiveDef) 
        {
            MPrimitiveDef primitive = (MPrimitiveDef)idlType;
            if(primitive.getKind() == MPrimitiveKind.PK_ANY) 
            {
                return true;
            }
        }  
        return false;
    }
    
    
    	public String generateCode(MAliasDef alias, String tag)
    	{
    		String code = "";
    		for(Iterator i = getPlugins().iterator(); i.hasNext();)
    		{
    			AnyPlugin plugin = (AnyPlugin)i.next();
    			code = plugin.generateCode(alias, tag);
    		}
    		// Note that, if there are more than one plugin for a particular IDL type, 
    		// the last registered plugin wins.
    		return code;
    	}    

    	
    	// Utility Methods ---------------------------------------------------
    
    private AnyPlugin loadPlugin(String absoluteClassName)
    		throws CcmtoolsException
    {
    		try
    		{
    			System.out.println("> load AnyPlugin " + PdlAnyPlugin.class);		
    			AnyPlugin plugin = (AnyPlugin)Class.forName(absoluteClassName).newInstance();
    			plugin.load();
    			getPlugins().add(plugin);
    			return plugin;
    		}
    		catch(Exception e)
    		{
    			e.printStackTrace();
    			throw new CcmtoolsException(e.getMessage());
    		}
    }    
}
