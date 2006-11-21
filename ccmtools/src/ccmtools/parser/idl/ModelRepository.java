package ccmtools.parser.idl;

import java.util.HashMap;
import java.util.Map;

import ccmtools.metamodel.BaseIDL.MExceptionDef;
import ccmtools.metamodel.BaseIDL.MIDLType;

public class ModelRepository
{
    private Map<ScopedName, MIDLType> idlType = new HashMap<ScopedName, MIDLType>();
    private Map<ScopedName, MExceptionDef> exceptions = new HashMap<ScopedName, MExceptionDef>();
    
    private IdentifierTable forwardDclTable = new IdentifierTable();

    
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
            System.out.println("+++ ModelRepository.contains: [" + scopedName + "]");
        }
        else
        {
            System.out.println("+++ ModelRepository.register: [" + scopedName + ", " + element + "]");
            idlType.put(scopedName, element);
        }
    }
    
    
    public MIDLType findIdlType(ScopedName scopedName)
    {
        System.out.println("+++ ModelRepository.find: [" + scopedName + "]");
        return findIdlType(new Scope(), scopedName);
    }


    /*
     * Try to find an IDL type descriped by the current scope and a scoped name
     * (explore all combinations of the current scope). 
     */
    public MIDLType findIdlType(Scope currentScope, ScopedName scopedName)
    {
        System.out.println("+++ ModelRepository.find: [" + currentScope + ", " + scopedName + "]");
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
            System.out.println("+++ ModelRepository.contains: [" + scopedName + "]");
        }
        else
        {
            System.out.println("+++ ModelRepository.register: [" + scopedName + ", " + element + "]");
            exceptions.put(scopedName, element);
        }
    }

    public MExceptionDef findIdlException(ScopedName scopedName)
    {
        System.out.println("+++ ModelRepository.find: [" + scopedName + "]");
        return findIdlException(new Scope(), scopedName);        
    }
    
    /*
     * Try to find an IDL type descriped by the current scope and a scoped name
     * (explore all combinations of the current scope). 
     */
    public MExceptionDef findIdlException(Scope currentScope, ScopedName scopedName)
    {
        System.out.println("+++ ModelRepository.find: [" + currentScope + ", " + scopedName + "]");
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
        System.out.println("+++ explore: " + scope + ", " + name);
        int index = scope.length();
        String s = scope;
        while(index != -1)
        {
            index = s.lastIndexOf("::");
            if(index >= 0)
            {
                s = s.substring(0, index);
                System.out.println("   try: " + s + "::" + name);
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
        System.out.println("+++ explore: " + scope + ", " + name);
        int index = scope.length();
        String s = scope;
        while(index != -1)
        {
            index = s.lastIndexOf("::");
            if(index >= 0)
            {
                s = s.substring(0, index);
                System.out.println("   try: " + s + "::" + name);
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
