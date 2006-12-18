package ccmtools.generator.idl.metamodel;

import java.util.List;

import ccmtools.utils.SourceFile;


public interface Idl3MirrorGeneratorElement
{
	String generateIdl3Mirror();
	
	List<SourceFile> generateIdl3MirrorSourceFiles();
}
