package ccmtools.parser.idl;

import java.util.HashSet;
import java.util.Set;

public class IdentifierTable
{
    private Set<Identifier> identifiers = new HashSet<Identifier>();
    
    public boolean register(Identifier id)
    {
        return identifiers.add(id);
    }    
    
    public void clear()
    {
        identifiers.clear();
    }
}
