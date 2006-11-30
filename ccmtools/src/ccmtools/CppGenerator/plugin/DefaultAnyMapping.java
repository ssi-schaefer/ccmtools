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
    
    public String getDebugCode(MAliasDef alias)
    {
        StringBuffer code = new StringBuffer();
        code.append("#ifdef WXDEBUG\n");
        code.append("inline\n");
        code.append("std::string\n");
        code.append("ccmDebug(const ").append(alias.getIdentifier()).append("& in, int indent = 0)\n");
        code.append("{\n");
        code.append(TAB).append("// TODO: use ccmDebug(const wamas::platform::utils::SmartPtr<wamas::platform::utils::Value>&)\n");
        code.append(TAB).append("// defined in ccm/CCM_Local/utils/Debug.h\n");
        code.append(TAB).append("std::ostringstream os;\n");
        code.append(TAB).append("os << \"").append(alias.getIdentifier()).append(" (alias wamas::platform::utils::Value)\";\n");
        code.append(TAB).append("return os.str();\n");
        code.append("}\n");        
        code.append("#endif\n");
        return code.toString();
    }
}
