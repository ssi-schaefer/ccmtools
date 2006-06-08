package ccmtools.CppGenerator.plugin;

import java.util.List;

public interface AnyPlugin
{
	void load();
	void unload();
	
	/**
	 * Generate a list of AnyMapping objects which can be registered
	 * to the AnyPluginManager.
	 */
	List getAnyMappings();
}
