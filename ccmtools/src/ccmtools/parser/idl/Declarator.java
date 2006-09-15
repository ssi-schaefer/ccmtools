package ccmtools.parser.idl;


public class Declarator
{
    private String declarator;

    public Declarator(String declarator)
    {
        this.declarator = declarator;
    }
    
    public String toString()
    {
        return declarator;
    }
}
