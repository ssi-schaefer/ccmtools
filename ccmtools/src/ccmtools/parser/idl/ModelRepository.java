package ccmtools.parser.idl;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import ccmtools.metamodel.BaseIDL.MExceptionDef;
import ccmtools.metamodel.BaseIDL.MIDLType;

public class ModelRepository
{
    private Logger logger;
    private Map<ScopedName, MIDLType> idlType = new HashMap<ScopedName, MIDLType>();
    private Map<ScopedName, MExceptionDef> exceptions = new HashMap<ScopedName, MExceptionDef>();
    
    private IdentifierTable forwardDclTable = new IdentifierTable();

    public ModelRepository()
    {
        logger = Logger.getLogger("ccm.parser.idl");
    }
    
    public Logger getLogger()
    {
        return logger;
    }
    
    public void registerForwardDeclaration(ScopedName scopedName)
    {
        forwardDclTable.register(scopedName);
    }

    public boolean isForwardDeclaration(ScopedName scopedName)
    {        
        return forwardDclTable.contains(scopedName);
    }
    

    
    public void registerIdlType(ScopedName scopedName, MIDLType element)
    {
        String id = scopedName.toString();        
        if(!(id.startsWith("::")))
        {
            scopedName = new ScopedName("::" + id);
        }
        
        if(idlType.containsKey(scopedName))
        {
            getLogger().fine("ModelRepository.contains: [" + scopedName + "]");
        }
        else
        {
            getLogger().fine("ModelRepository.register: [" + scopedName + ", " + element + "]");
            idlType.put(scopedName, element);
        }
    }
    
    
    public MIDLType findIdlType(ScopedName scopedName)
    {
        getLogger().fine("ModelRepository.find: [" + scopedName + "]");
        return findIdlType(new Scope(), scopedName);
    }


    /*
     * Try to find an IDL type descriped by the current scope and a scoped name
     * (explore all combinations of the current scope). 
     */
    public MIDLType findIdlType(Scope currentScope, ScopedName scopedName)
    {
        getLogger().fine("ModelRepository.find: [" + currentScope + ", " + scopedName + "]");
        MIDLType type; 
        if(scopedName.toString().startsWith("::")) // Absolute scoped name
        {            
            type = idlType.get(scopedName);
        }
        else 
        {
            type = exploreModulesForIdlType(currentScope.toString(), scopedName.toString());
        }
        
        if(type == null)
            throw new RuntimeException("Unknown type " + scopedName + "!");
        else
            return type;
    }
    
    
    

    public void registerException(ScopedName scopedName, MExceptionDef element)
    {
        String id = scopedName.toString();        
        if(!(id.startsWith("::")))
        {
            scopedName = new ScopedName("::" + id);
        }
        
        if(exceptions.containsKey(scopedName))
        {
            getLogger().fine("ModelRepository.contains: [" + scopedName + "]");
        }
        else
        {
            getLogger().fine("ModelRepository.register: [" + scopedName + ", " + element + "]");
            exceptions.put(scopedName, element);
        }
    }

    public MExceptionDef findIdlException(ScopedName scopedName)
    {
        getLogger().fine("ModelRepository.find: [" + scopedName + "]");
        return findIdlException(new Scope(), scopedName);        
    }
    
    /*
     * Try to find an IDL type descriped by the current scope and a scoped name
     * (explore all combinations of the current scope). 
     */
    public MExceptionDef findIdlException(Scope currentScope, ScopedName scopedName)
    {
        getLogger().fine("ModelRepository.find: [" + currentScope + ", " + scopedName + "]");
        MExceptionDef type; 
        if(scopedName.toString().startsWith("::")) // Absolute scoped name
        {            
            type = exceptions.get(scopedName);
        }
        else 
        {
            type = exploreModulesForException(currentScope.toString(), scopedName.toString());
        }
        
        if(type == null)
            throw new RuntimeException("Unknown exception " + scopedName + "!");
        else
            return type;
    }
    
    
    public void clear()
    {
        idlType.clear();
        exceptions.clear();
        forwardDclTable.clear();
    }
    
    
    /** Utility Methods */
    
    protected MIDLType exploreModulesForIdlType(String scope, String name)
    {
        getLogger().fine("explore modules: " + scope + ", " + name);
        int index = scope.length();
        String s = scope;
        while(index != -1)
        {
            index = s.lastIndexOf("::");
            if(index >= 0)
            {
                s = s.substring(0, index);
                //System.out.println("   try: " + s + "::" + name);
                MIDLType type = idlType.get(new ScopedName(s + "::" + name));
                if (type != null)
                {
                    return type;
                }
            }
        }
        return null;
    }
    
    
    protected MExceptionDef exploreModulesForException(String scope, String name)
    {
        getLogger().fine("explore modules: " + scope + ", " + name);
        int index = scope.length();
        String s = scope;
        while(index != -1)
        {
            index = s.lastIndexOf("::");
            if(index >= 0)
            {
                s = s.substring(0, index);
                getLogger().fine("   try: " + s + "::" + name);
                MExceptionDef type = exceptions.get(new ScopedName(s + "::" + name));
                if (type != null)
                {
                    return type;
                }
            }
        }
        return null;
    }
}
