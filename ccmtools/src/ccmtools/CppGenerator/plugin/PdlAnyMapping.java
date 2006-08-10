package ccmtools.CppGenerator.plugin;

import ccmtools.metamodel.BaseIDL.MAliasDef;

/**
 * This mapping object can be used in all cases where the any target
 * type is a simple C struct generated from PDL files
 * (just a name, no templates etc.).
 */
public class PdlAnyMapping 
	implements AnyMapping
{    
    private PdlType pdlType;
    
    public PdlAnyMapping(PdlType type) 
    {
        setPdlType(type);
    }
    
    /**
     * Returns the name used for registering the AnyMapping object.
     */
    public String getIdlTypeName()
    {
    		return getPdlType().getPdlName(); 
    }
    
    public PdlType getPdlType()
    {
        return pdlType;
    }
    
    private void setPdlType(PdlType type)
    {
        this.pdlType = type;
    }
    
    
    // Generator methods -----------------------------------------------------
    
    public String getIncludeCode(MAliasDef alias)
    {
        StringBuffer code = new StringBuffer();
        code.append("#include <").append(getPdlType().getCName()).append(".h>\n");
        return code.toString();
    }

    public String getDefinitionCode(MAliasDef alias)
    {
        StringBuffer code = new StringBuffer();
        code.append("typedef ::").append(getPdlType().getCName()).append(" ");
        code.append(getPdlType().getPdlName()).append(";\n");
        return code.toString();
    }

    public String getDebugCode(MAliasDef alias)
    {
        StringBuffer code = new StringBuffer();
        code.append("#ifdef WXDEBUG\n");
        code.append("inline\n");
        code.append("std::string\n");
        code.append("ccmDebug(const ").append(getPdlType().getPdlName()).append("& in, int indent = 0)\n");
        code.append("{\n");
        code.append("    std::ostringstream os;\n");
        code.append("    os << \"").append(getPdlType().getPdlName());
        code.append(" (alias ").append(getPdlType().getCName()).append(")\";\n");
        code.append("    return os.str();\n");
        code.append("}\n");
        code.append("#endif // WXDEBUG\n");
        return code.toString();
    }
}
