package ccmtools.parser.idl;

import java.util.HashMap;
import java.util.Map;

import ccmtools.metamodel.BaseIDL.MExceptionDef;
import ccmtools.metamodel.BaseIDL.MIDLType;

public class ModelRepository
{
    private Map<Identifier, MIDLType> idlType = new HashMap<Identifier, MIDLType>();
    private Map<Identifier, MExceptionDef> exceptions = new HashMap<Identifier, MExceptionDef>();
    
    public void registerIdlType(Identifier id, MIDLType element)
    {
        if(!idlType.containsKey(id))
        {
            System.out.println("+++ add: [" + id + ", " + element + "]");
            idlType.put(id, element);
        }
    }
    
    public MIDLType findIdlType(Identifier id)
    {
        System.out.println("+++ find: [" + id + "]");
        MIDLType type = idlType.get(id); 
        if(type == null)
        {
            throw new RuntimeException("Unknown type " + id + "!");
        }        
        return type;
    }


    public void registerException(Identifier id, MExceptionDef ex)
    {
        if(!idlType.containsKey(id) && !exceptions.containsKey(id))
        {
            System.out.println("+++ add: [" + id + ", " + ex + "]");
            exceptions.put(id, ex);
        }
    }
    
    public MExceptionDef findIdlException(Identifier id)
    {
        System.out.println("+++ find: [" + id + "]");
        MExceptionDef ex = exceptions.get(id); 
        if(ex == null)
        {
            throw new RuntimeException("Unknown exception " + id + "!");
        }        
        return ex;
    }
}
