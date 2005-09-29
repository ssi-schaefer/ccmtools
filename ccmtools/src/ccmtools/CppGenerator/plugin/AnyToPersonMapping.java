package ccmtools.CppGenerator.plugin;

import ccmtools.CppGenerator.CppLocalGenerator;
import ccmtools.Metamodel.BaseIDL.MAliasDef;


public class AnyToPersonMapping implements AnyMapping
{
    /** 
     * To use some utility methods, a mapping object has to know
     * ist local C++ generator
     **/
    protected CppLocalGenerator generator = null;
    
    public AnyToPersonMapping(CppLocalGenerator generator) 
    {
        this.generator = generator;
    }
    
    public String getIncludeCode(MAliasDef alias)
    {
        StringBuffer code = new StringBuffer();
        code.append("#include <Person.h>\n");
        return code.toString();
    }

    public String getDefinitionCode(MAliasDef alias)
    {
        StringBuffer code = new StringBuffer();
        code.append("typedef ::Person Person;\n");
        return code.toString();
    }

    public String getDebugCode(MAliasDef alias)
    {
        StringBuffer code = new StringBuffer();
        code.append("#ifdef WXDEBUG\n");
        code.append("inline\n");
        code.append("std::string\n");
        code.append("ccmDebug(const Person& in, int indent = 0)\n");
        code.append("{\n");
        code.append("    std::ostringstream os;\n");
        code.append("    // TODO: implement a string representation!\n");
        code.append("    return os.str();\n");
        code.append("}\n");
        code.append("#endif // WXDEBUG\n");
        return code.toString();
    }

}
