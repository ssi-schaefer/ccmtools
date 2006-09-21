package ccmtools.parser.idl;

import java.util.List;

public class ReadonlyAttributeDeclarator
    extends Declarator
{
    private List exceptions;
    
    public ReadonlyAttributeDeclarator(Declarator declarator, List exceptions)
    {
        super(declarator.toString());
        this.exceptions = exceptions;
    }
    
    public List getExceptions()
    {
        return exceptions;
    }
}
