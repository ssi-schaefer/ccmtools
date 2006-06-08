package ccmtools.CppGenerator.plugin;

import ccmtools.CppGenerator.CppLocalGenerator;
import ccmtools.Metamodel.BaseIDL.MAliasDef;
import ccmtools.Metamodel.BaseIDL.MTyped;
import ccmtools.Metamodel.BaseIDL.MTypedefDef;


/***
 * This any default mapping uses WX::Utils::Value objects
 * for local C++ components.
 * The debug method for WX::Utils::Value objects is already defined
 * in the cpp-environment library.
 * 
 * @author eteinik
 */
public class DefaultAnyMapping
    implements AnyMapping
{
    /** 
     * To use some utility methods, a mapping object has to know
     * ist local C++ generator
     **/
	public static final String DEFAULT_ANY_MAPPING = "WX::Utils::Value";
    protected CppLocalGenerator generator = null;
    
    public DefaultAnyMapping(CppLocalGenerator cppLocalGenerator) 
    {
        this.generator = cppLocalGenerator;
    }
    
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
        MTyped type = (MTyped) alias;
        StringBuffer code = new StringBuffer();
        code.append("typedef ");
        code.append(generator.getLanguageType(type));
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
