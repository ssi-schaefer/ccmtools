package ccmtools.generator.idl.metamodel;

import java.util.ArrayList;
import java.util.List;

import ccmtools.utils.Text;

public class Helper
{
    /*************************************************************************
     * Code Generation Helper Methods
     *************************************************************************/
    
    public static String generateExceptionList(List<ExceptionDef> exceptions)
    {
        List<String> exceptionList = new ArrayList<String>();
        for(ExceptionDef ex : exceptions)
        {
            exceptionList.add(ex.generateIdlMapping()); 
        }
        return Text.join(", ", exceptionList);
    }
}
