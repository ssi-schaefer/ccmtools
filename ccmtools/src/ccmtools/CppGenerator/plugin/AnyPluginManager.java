package ccmtools.CppGenerator.plugin;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
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
import ccmtools.utils.CcmtoolsProperties;

/**
 * This manager class hides all details of the any plugin mechanism from  
 * code generators.
 * 
 * 
 */
public class AnyPluginManager
{
    protected CppLocalGenerator generator = null;
    protected Map anyMappings = null;
    
    public AnyPluginManager(CppLocalGenerator generator)
    {
        this.generator = generator;
        anyMappings = new HashMap();       
                       
        /**
         *  We fill all typedef to any mappings into this map from where
         *  the code generator can find (and use) such a predefined mapping.
         **/
        registerSimpleMappings("SimpleTypes");
        registerTemplateMappings();
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

    protected AnyMapping findMapping(String identifier)
    {
        if(anyMappings.containsKey(identifier)) {
            return (AnyMapping) anyMappings.get(identifier);
        }
        else {
            // Use default mapping
            return new DefaultAnyMapping(generator);
        }
    }
    
    
    protected void registerMapping(AnyMapping mapping)
    {
        anyMappings.put(mapping.getTypeName(), mapping);
    }
        
    
    /**
     * Load a list of simple type names from a file, instantiate a SimpleAnyMapping object
     * for each type and register each of these mapping objects.
     * 
     * @param templateName Name of the file containing the simple type names.
     */
    protected void registerSimpleMappings(String templateName) 
    {
        List list = loadSimpleTypes();
        for(Iterator i = list.iterator(); i.hasNext();) {
            String typeName = (String) i.next();
            registerMapping(new SimpleAnyMapping(typeName));
            System.out.println("   - register any to " + typeName + " mapping");
        }
    }

    /**
     * Load all any mapping templates of the given directory, instantiate a 
     * SimpleAnyMapping object for each template and egister each of these mapping objects.
     */
    protected void registerTemplateMappings()
    {
        File dir = new File(CcmtoolsProperties.Instance().get("ccmtools.dir.plugin.any.templates"));
        System.out.println("> load any template plugins from: " + dir);     
        
        // TODO
        
    }
    
    protected List loadSimpleTypes()
    {
        File file = new File(CcmtoolsProperties.Instance().get("ccmtools.dir.plugin.any.types"));
        System.out.println("> load any type plugins from: " + file);     
        List list = new ArrayList();
        
        if(file.exists()) {
            try {
                BufferedReader in = new BufferedReader(new FileReader(file));
                String line;
                while((line = in.readLine()) != null) {
                    String typeName = line.trim();
                    if(typeName.length() > 0) {
                        list.add(typeName.trim());
                    }
                }
                in.close();
            }
            catch(FileNotFoundException e) {
                // TODO: log e.printStackTrace();
            }
            catch(IOException e) {
                // TODO: log e.printStackTrace();
            }
        }
        return list;
    }
}
