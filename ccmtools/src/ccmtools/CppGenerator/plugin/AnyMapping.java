package ccmtools.CppGenerator.plugin;

import ccmtools.Metamodel.BaseIDL.MAliasDef;

/**
 * This interface defines the methods a generator can call to generate
 * code from a any plugin mapping.
 * An any mapping plugin is based on the CppLocalTemplates/MAilasDef template
 * structure:
 *   %(DebugInclude)s
 *   %(TypedefInclude)s    // calls getIncludeCode()
 *   %(OpenNamespace)s
 *   %(TypedefDefinition)s // calls getDefinitionCode()
 *   %(TypedefDebug)s      // calls getDebugCode()
 *   %(CloseNamespace)s
 */
public interface AnyMapping
{
    String getIncludeCode(MAliasDef alias);
    String getDefinitionCode(MAliasDef alias);
    String getDebugCode(MAliasDef alias);
}
