package ccmtools.parser.idl;

import java.util.List;

public class AttributeRaisesExpression
{
    private List getterExceptions;
    private List setterExceptions;
    
    public AttributeRaisesExpression(List getExceptions, List setExceptions)
    {
        this.getterExceptions = getExceptions;
        this.setterExceptions = setExceptions;
    }
    
    public List getGetterExceptions()
    {
        return getterExceptions;
    }
    
    public List getSetterExceptions()
    {
        return setterExceptions;
    }     
}
