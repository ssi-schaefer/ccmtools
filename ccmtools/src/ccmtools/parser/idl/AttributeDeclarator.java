package ccmtools.parser.idl;

import java.util.List;

public class AttributeDeclarator
    extends Declarator
{
    private AttributeRaisesExpression raisesExpr;
    
    public AttributeDeclarator(Declarator declarator, AttributeRaisesExpression raisesExpr)
    {
        super(declarator.toString());
        this.raisesExpr = raisesExpr;
    }
    
    public List getGetterExceptions()
    {
        return raisesExpr.getGetterExceptions();
    }
    
    public List getSetterExceptions()
    {
        return raisesExpr.getSetterExceptions();
    }  
}
