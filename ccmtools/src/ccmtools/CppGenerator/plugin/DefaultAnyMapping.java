package ccmtools.CppGenerator.plugin;

import ccmtools.Metamodel.BaseIDL.MAliasDef;
import ccmtools.Metamodel.BaseIDL.MTypedefDef;


/***
 * This any default mapping uses WX::Utils::Value objects
 * for local C++ components.
 * The debug method for WX::Utils::Value objects is already defined
 * in the cpp-environment library.
 */
public class DefaultAnyMapping
    implements AnyMapping
{
	public static final String DEFAULT_ANY_MAPPING = "WX::Utils::Value";
    
    public String getIdlTypeName()
    {
        return DEFAULT_ANY_MAPPING; 
    }
        
    public String getIncludeCode(MAliasDef alias)
    {
        StringBuffer code = new StringBuffer();
        code.append("#include <WX/Utils/value.h>\n");
        return code.toString();
    }
    
    public String getDefinitionCode(MAliasDef alias)
    {
        MTypedefDef typedef = (MTypedefDef) alias;
        StringBuffer code = new StringBuffer();
        code.append("typedef ");
        code.append("WX::Utils::SmartPtr<WX::Utils::Value>");
        code.append(" ");
        code.append(typedef.getIdentifier());
        code.append(";\n");
        return code.toString();
    }
    
    public String getDebugCode(MAliasDef alias)
    {
        StringBuffer code = new StringBuffer();
        code.append("#ifdef WXDEBUG\n");
        code.append("// use ccmDebug(const WX::Utils::SmartPtr<WX::Utils::Value>&)\n");
        code.append("// defined in ccm/CCM_Local/utils/Debug.h\n");
        code.append("#endif\n");
        return code.toString();
    }
}
