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

    
    public void registerForwardDeclaration(ScopedName id)
    {
        forwardDclTable.register(id);
    }

    public boolean isForwardDeclaration(ScopedName id)
    {        
        return forwardDclTable.contains(id);
    }
    

    
    public void registerIdlType(ScopedName id, MIDLType element)
    {
        if(idlType.containsKey(id))
        {
            System.out.println("+++ ModelRepository.contains: [" + id + "]");
            System.out.println("+++ " + idlType);
        }
        else
        {
            System.out.println("+++ ModelRepository.register: [" + id + ", " + element + "]");
            System.out.println("+++ " + idlType);
            idlType.put(id, element);
        }
    }
    
    public MIDLType findIdlType(ScopedName id)
    {
        System.out.println("+++ ModelRepository.find: [" + id + "]");
        System.out.println("+++ " + idlType);
        MIDLType type = idlType.get(id); 
        if(type == null)
        {
            throw new RuntimeException("Unknown type " + id + "!");
        }        
        return type;
    }


    public void registerException(ScopedName id, MExceptionDef ex)
    {
        if(!idlType.containsKey(id) && !exceptions.containsKey(id))
        {
            System.out.println("+++ add: [" + id + ", " + ex + "]");
            exceptions.put(id, ex);
        }
    }
    
    public MExceptionDef findIdlException(ScopedName id)
    {
        System.out.println("+++ find: [" + id + "]");
        MExceptionDef ex = exceptions.get(id); 
        if(ex == null)
        {
            throw new RuntimeException("Unknown exception " + id + "!");
        }        
        return ex;
    }
    
    
    public void clear()
    {
        idlType.clear();
        exceptions.clear();
        forwardDclTable.clear();
    }
}
