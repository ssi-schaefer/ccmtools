package ccmtools.parser.idl;

import java.util.HashSet;
import java.util.Set;

public class IdentifierTable
{
    private Set<ScopedName> identifiers = new HashSet<ScopedName>();
    
    public boolean register(ScopedName id)
    {
        return identifiers.add(id);
    }    
    
    public boolean contains(ScopedName id)
    {
        System.out.println("+++ IdentifierTable.contains " + id + ", " + identifiers);
        return identifiers.contains(id);
    }
    public void clear()
    {
        identifiers.clear();
    }
}
