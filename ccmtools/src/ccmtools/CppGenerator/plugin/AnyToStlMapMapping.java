package ccmtools.CppGenerator.plugin;

import ccmtools.CppGenerator.CppLocalGenerator;
import ccmtools.Metamodel.BaseIDL.MAliasDef;


public class AnyToStlMapMapping implements AnyMapping
{
    /** 
     * To use some utility methods, a mapping object has to know
     * ist local C++ generator
     **/
    protected CppLocalGenerator cppLocalGenerator = null;
    
    public AnyToStlMapMapping(CppLocalGenerator cppLocalGenerator) 
    {
        this.cppLocalGenerator = cppLocalGenerator;
    }
    
    
    public String getIncludeCode(MAliasDef alias)
    {
        StringBuffer code = new StringBuffer();
        code.append("#include <map>\n");
        return code.toString();
    }

    public String getDefinitionCode(MAliasDef alias)
    {
        StringBuffer code = new StringBuffer();
        code.append("typedef std::map<std::string, int> StlMap;\n");
        return code.toString();
    }

    public String getDebugCode(MAliasDef alias)
    {
        StringBuffer code = new StringBuffer();
        code.append("#ifdef WXDEBUG\n");
        code.append("inline\n");
        code.append("std::string\n");
        code.append("ccmDebug(const StlMap& in, int indent = 0)\n");
        code.append("{\n");
        code.append("    std::ostringstream os;\n");
        code.append("    os << ::ccm::local::doIndent(indent) << \"StlMap:\" << std::endl;\n");
        code.append("    StlMap::const_iterator pos;\n");
        code.append("    for(pos = in.begin(); pos != in.end(); ++pos) {\n");
        code.append("        os << ::ccm::local::doIndent(indent+1);\n");
        code.append("        os << \"key = \" << pos->first << \" , value = \" << pos->second << endl;\n");
        code.append("    }\n");
        code.append("    return os.str();\n");
        code.append("}\n");
        code.append("#endif // WXDEBUG\n");
        return code.toString();
    }
}
