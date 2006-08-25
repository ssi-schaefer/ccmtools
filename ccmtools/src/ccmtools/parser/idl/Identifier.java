package ccmtools.parser.idl;

public class Identifier
{
    private final String id; 

    
    public Identifier(String id)
    {
        this.id = id;
    }

    
    /**
     * An Identifier has the same hash code as the stored String.
     * Note: a equals b => hash(a) == hash(b)
     */
    public int hashCode() 
    {
        return id.toUpperCase().hashCode();
    };
    
    /**
     * Two Identifiers are equal iff their stored strings are equal.
     * Note that for IDL identifiers Upper- and lower-case letters are
     * treated as the same letter.
     */
    public boolean equals(Object obj)
    {
        if (this == obj) 
        {
            return true;
        }
        if (obj instanceof Identifier) 
        {
            Identifier anotherId = (Identifier)obj;
            return this.id.toUpperCase().equals(anotherId.id.toUpperCase());
        }
        else
        {
            return false;
        }
    }
    
    public String toString()
    {
        return id;
    }
}
