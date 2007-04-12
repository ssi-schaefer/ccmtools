package ccmtools.ant;

import org.apache.tools.ant.types.EnumeratedAttribute;

public class BindingType
    extends EnumeratedAttribute
{

    public String[] getValues()
    {
        return new String[]  
        {
                "fclient",
                "fserver",
                "fserverTIE",
                "fall",
                "fallTIE",
        };        
    }
}
