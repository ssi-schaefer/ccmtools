package ccmtools.CppGenerator.plugin;

import ccmtools.parser.idl.metamodel.BaseIDL.MAliasDef;

public interface AnyPlugin
{
	void load();
	void unload();
	
	String generateCode(MAliasDef alias, String tag);
}
