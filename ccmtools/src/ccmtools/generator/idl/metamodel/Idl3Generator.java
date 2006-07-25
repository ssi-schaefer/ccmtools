package ccmtools.generator.idl.metamodel;

import java.util.List;

import ccmtools.utils.SourceFile;


public interface Idl3Generator
{
	String generateIdl3();
	
	List<SourceFile> generateIdl3SourceFiles();
}
