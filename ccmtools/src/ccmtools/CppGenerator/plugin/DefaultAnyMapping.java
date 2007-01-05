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
	public static final String DEFAULT_ANY_MAPPING = "wamas::platform::utils::Value";
    private static final String TAB = "    ";
    
    public String getIdlTypeName()
    {
        return DEFAULT_ANY_MAPPING; 
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
        code.append("wamas::platform::utils::SmartPtr<wamas::platform::utils::Value>");
        code.append(" ");
        code.append(typedef.getIdentifier());
        code.append(";\n");
        return code.toString();
    }    
}
