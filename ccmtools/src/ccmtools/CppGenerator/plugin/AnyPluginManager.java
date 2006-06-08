package ccmtools.CppGenerator.plugin;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import ccmtools.CppGenerator.CppLocalGenerator;
import ccmtools.Metamodel.BaseIDL.MAliasDef;
import ccmtools.Metamodel.BaseIDL.MIDLType;
import ccmtools.Metamodel.BaseIDL.MPrimitiveDef;
import ccmtools.Metamodel.BaseIDL.MPrimitiveKind;
import ccmtools.Metamodel.BaseIDL.MTypedefDef;

/**
 * This manager class hides all details of the any plugin mechanism from  
 * code generators.
 * 
 * 
 */
public class AnyPluginManager
{
    protected CppLocalGenerator generator = null;
    protected Map anyMappings = new HashMap();

    
    public AnyPluginManager(CppLocalGenerator generator)
    {
        this.generator = generator;

        registerMapping(new DefaultAnyMapping(generator));

        // TODO: AnyMapping registrations should be triggert by config files !!!!!!!!!!        
        AnyPlugin plugin = new PdlAnyPlugin();
        plugin.load();
        registerMappings(plugin.getAnyMappings());
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
    
    /**
     * These following methods represents the structure used by the 
     * CppLocalTemplates/MAliasDef template to generate a typedef
     * definition.
     * Note that by using the findMapping() method, we can plug in 
     * new mappings pretty easyly.
     */
    
    public String getTypedefInclude(MAliasDef alias)
    {
        String identifier = ((MTypedefDef)alias).getIdentifier();
        return findMapping(identifier).getIncludeCode(alias);
    }
    
    public String getTypedefDefinition(MAliasDef alias) 
    {
        String identifier = ((MTypedefDef)alias).getIdentifier();
        return findMapping(identifier).getDefinitionCode(alias);
    }
    
    public String getTypedefDebug(MAliasDef alias)
    {
        String identifier = ((MTypedefDef)alias).getIdentifier();
        return findMapping(identifier).getDebugCode(alias);
    }
    
    // Helper methods to manage mapping instances

    
    public void registerMapping(AnyMapping mapping)
    {
    		if(mapping.getIdlTypeName() != null
    				&& mapping.getIdlTypeName().length() > 0)
    		{
    			System.out.println("    register any to " + mapping.getIdlTypeName() + " mapping");
    			anyMappings.put(mapping.getIdlTypeName(), mapping);
    		}
    }
    
    public void registerMappings(List mappingList)
    {
    	  for(Iterator i = mappingList.iterator(); i.hasNext();) 
      {
    		  registerMapping((AnyMapping)i.next());
      }
    }
    
    protected AnyMapping findMapping(String identifier)
    {
        if(anyMappings.containsKey(identifier)) 
        {
            return (AnyMapping)anyMappings.get(identifier);
        }
        else 
        {
            // Use default mapping
            return (AnyMapping)anyMappings.get(DefaultAnyMapping.DEFAULT_ANY_MAPPING);
        }
    }
}
