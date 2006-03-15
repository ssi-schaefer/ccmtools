package ccmtools.generator.java;

import java.util.HashMap;
import java.util.Map;

import ccmtools.generator.java.metamodel.AnyType;
import ccmtools.generator.java.metamodel.Type;
import ccmtools.generator.java.metamodel.plugin.EntityContainer;


/**
 * Any Plugin Concept
 * 
 * For "typedef any <someType>" constructs, we can register  
 * AnyPlugins that defines a class which implements the Type interface
 * and will be used by the generator to implement the <someType> type. 
 */

/**
 * This is the first version of the AnyPluginManager that is used by the
 * CcmToJavaModelMapper class to handle "typedef any <someType>" mappings.
 * 
 * TODO: AnyPlugins should be registered in a dynamic way using a
 * AnyPlugin Configuration file...
 */
public class AnyPluginManager
{
	private Map plugins = new HashMap();
	
	public AnyPluginManager()
	{
		// TODO: read plugin info from a configuration file
		plugins.put("EntityContainer", new EntityContainer());
		// ...
		
	}
	
	public Type load(String identifier)
	{
		if(plugins.containsKey(identifier))
		{
			return (Type)plugins.get(identifier);
		}
		else // no plugin registered
		{
			return new AnyType();
		}
	}
}
	