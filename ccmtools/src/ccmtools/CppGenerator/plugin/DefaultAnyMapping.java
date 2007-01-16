package ccmtools.CppGenerator.plugin;

import ccmtools.metamodel.BaseIDL.MAliasDef;
import ccmtools.metamodel.BaseIDL.MTypedefDef;


/***
 * This any default mapping uses wamas::platform::utils::Value objects
 * for local C++ components.
 * The debug method for wamas::platform::utils::Value objects is already defined
 * in the cpp-environment library.
 */
public class DefaultAnyMapping
    implements AnyMapping
{
    public String getIdlTypeName()
    {
        return "::wamas::platform::utils::Value"; 
    }
        
    public String getIncludeCode(MAliasDef alias)
    {
        StringBuffer code = new StringBuffer();
        code.append("#include <wamas/platform/utils/Value.h>\n");
        return code.toString();
    }
    
    public String getDefinitionCode(MAliasDef alias)
    {
        MTypedefDef typedef = (MTypedefDef) alias;
        StringBuffer code = new StringBuffer();
        code.append("typedef ");
        code.append("::wamas::platform::utils::SmartPtr< ::wamas::platform::utils::Value >");
        code.append(" ");
        code.append(typedef.getIdentifier());
        code.append(";\n");
        return code.toString();
    }    
}
