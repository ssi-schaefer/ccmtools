package ccmtools.CppGenerator.plugin;

import java.util.HashMap;
import java.util.Map;

import ccmtools.CppGenerator.CppLocalGenerator;
import ccmtools.Metamodel.BaseIDL.MAliasDef;
import ccmtools.Metamodel.BaseIDL.MIDLType;
import ccmtools.Metamodel.BaseIDL.MPrimitiveDef;
import ccmtools.Metamodel.BaseIDL.MPrimitiveKind;
import ccmtools.Metamodel.BaseIDL.MTypedefDef;

/**
 * This manager class hides all details of the any plugin mechanism from  
 * the code generator.
 * 
 * 
 */
public class AnyPluginManager
{
    protected CppLocalGenerator generator = null;
    protected Map mappings = null;
    
    public AnyPluginManager(CppLocalGenerator generator)
    {
        this.generator = generator;
        mappings = new HashMap();       
        
//        String dir = System.getProperty("ccmtools.templates") 
//                        + Text.FILE_SEPARATOR 
//                        + CcmtoolsProperties.Instance().get("ccmtools.dir.plugin.any");
//        System.out.println("!!!!!!!! " + dir);
        
        /**
         *  We fill all typedef to any mappings into this map from where
         *  the code generator can find (and use) such a predefined mapping.
         **/
        mappings.put("Person", new AnyToPersonMapping(generator));
        mappings.put("DTO", new AnyToDTOMapping(generator));

        mappings.put("StlMap", new AnyToStlMapMapping(generator));
        // ...
        
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
        if(idlType instanceof MPrimitiveDef) {
            MPrimitiveDef primitive = (MPrimitiveDef)idlType;
            if(primitive.getKind() == MPrimitiveKind.PK_ANY) {
                return true;
            }
        }  
        return false;
    }
        
    protected AnyMapping findMapping(String identifier)
    {
        if(mappings.containsKey(identifier)) {
            return (AnyMapping) mappings.get(identifier);
        }
        else {
            // Use default mapping
            return new AnyDefaultMapping(generator);
        }
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
}
