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
//            System.out.println("+++ " + idlType);
        }
        else
        {
            System.out.println("+++ ModelRepository.register: [" + scopedName + ", " + element + "]");
//            System.out.println("+++ " + idlType);
            idlType.put(scopedName, element);
        }
    }
    
    
    public MIDLType findIdlType(ScopedName scopedName)
    {
        System.out.println("+++ ModelRepository.find: [" + scopedName + "]");
//        System.out.println("+++ " + idlType);
//        MIDLType type = idlType.get(scopedName); 
//        if(type == null)
//        {
//            throw new RuntimeException("Unknown type " + scopedName + "!");
//        }        
//        return type;
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
            type = exploreModules(currentScope.toString(), scopedName.toString());
        }
        
        if(type == null)
            throw new RuntimeException("Unknown type " + scopedName + "!");
        else
            return type;
    }
    

    public void registerException(ScopedName scopedName, MExceptionDef ex)
    {
        if(!idlType.containsKey(scopedName) && !exceptions.containsKey(scopedName))
        {
            System.out.println("+++ add: [" + scopedName + ", " + ex + "]");
            exceptions.put(scopedName, ex);
        }
    }
    
    public MExceptionDef findIdlException(ScopedName scopedName)
    {
        System.out.println("+++ find: [" + scopedName + "]");
        MExceptionDef ex = exceptions.get(scopedName); 
        if(ex == null)
        {
            throw new RuntimeException("Unknown exception " + scopedName + "!");
        }        
        return ex;
    }
    
    
    public void clear()
    {
        idlType.clear();
        exceptions.clear();
        forwardDclTable.clear();
    }
    
    
    /** Utility Methods */
    
    protected MIDLType exploreModules(String scope, String name)
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
    
}
