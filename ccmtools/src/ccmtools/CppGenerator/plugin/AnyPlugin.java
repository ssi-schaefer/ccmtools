package ccmtools.CppGenerator.plugin;

import ccmtools.metamodel.BaseIDL.MAliasDef;

public interface AnyPlugin
{
	void load();
	void unload();
	
	String generateCode(MAliasDef alias, String tag);
}
