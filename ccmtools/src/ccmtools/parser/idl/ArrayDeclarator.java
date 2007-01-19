package ccmtools.parser.idl;

import ccmtools.parser.idl.metamodel.BaseIDL.MArrayDef;

public class ArrayDeclarator
    extends Declarator
{
    private MArrayDef arrayDef;
    
    public ArrayDeclarator(String declarator, MArrayDef arrayDef)
    {
        super(declarator);
        this.arrayDef = arrayDef;
    }
    
    public MArrayDef getArray()
    {
        return arrayDef;
    }
}
