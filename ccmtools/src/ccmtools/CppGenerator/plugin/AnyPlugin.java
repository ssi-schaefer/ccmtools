package ccmtools.CppGenerator.plugin;

import ccmtools.Metamodel.BaseIDL.MAliasDef;

public interface AnyPlugin
{
	void load();
	void unload();
	
	String generateCode(MAliasDef alias, String tag);
}
