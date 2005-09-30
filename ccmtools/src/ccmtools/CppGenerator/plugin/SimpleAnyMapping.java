package ccmtools.CppGenerator.plugin;

import ccmtools.Metamodel.BaseIDL.MAliasDef;

/**
 * This simple mapping object can be used in all cases where the any target
 * type is a simple type (just a name, no templates etc.).
 * 
 * For example, if you have a C struct called Person simply create this
 * object with new AnyToSimpleMapping(generator, "Person); and you will
 * get a ccm::local::Person type which is a typedef to the existing C
 * structure.
 */
public class SimpleAnyMapping implements AnyMapping
{    
    protected String typeName;
    
    public SimpleAnyMapping(String typeName) 
    {
        this.typeName = typeName;
    }
    
    public String getTypeName()
    {
        return typeName;
    }
    
    public void setTypeName(String typeName)
    {
        this.typeName = typeName;
    }
    
    public String getIncludeCode(MAliasDef alias)
    {
        StringBuffer code = new StringBuffer();
        code.append("#include <").append(typeName).append(".h>\n");
        return code.toString();
    }

    public String getDefinitionCode(MAliasDef alias)
    {
        StringBuffer code = new StringBuffer();
        code.append("typedef ::").append(typeName).append(" ").append(typeName).append(";\n");
        return code.toString();
    }

    public String getDebugCode(MAliasDef alias)
    {
        StringBuffer code = new StringBuffer();
        code.append("#ifdef WXDEBUG\n");
        code.append("inline\n");
        code.append("std::string\n");
        code.append("ccmDebug(const ").append(typeName).append("& in, int indent = 0)\n");
        code.append("{\n");
        code.append("    std::ostringstream os;\n");
        code.append("    // TODO: call a toString method of the existing type!\n");
        code.append("    return os.str();\n");
        code.append("}\n");
        code.append("#endif // WXDEBUG\n");
        return code.toString();
    }
}
