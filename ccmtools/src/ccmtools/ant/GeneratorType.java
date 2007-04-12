package ccmtools.ant;

import org.apache.tools.ant.types.EnumeratedAttribute;

public class GeneratorType
    extends EnumeratedAttribute
{

    public String[] getValues()
    {
        return new String[]  
        {
                // ccmtools.parser.idl.metamodel
                "model.validator",
                "model.parser",
                
                // ccmtools.generator.idl
                "idl3",
                "idl3.mirror",
                "idl2",
                
                // ccmtools.generator.java
                "java.iface",
                "java.local",
                "java.app",
                "java.remote",
                "java.clientlib",
                
                // ccmtools.generator.deployment
                "descriptor"
        };        
    }
}
