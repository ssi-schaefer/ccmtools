package ccmtools.CppGenerator.plugin;

import ccmtools.Metamodel.BaseIDL.MAliasDef;

public interface AnyMapping
{
    String getIncludeCode(MAliasDef alias);
    String getDefinitionCode(MAliasDef alias);
    String getDebugCode(MAliasDef alias);
}
