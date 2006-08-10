package ccmtools.generator.idl.metamodel;

import java.util.List;

import ccmtools.utils.SourceFile;


public interface Idl2Generator
{
	String generateIdl2();
	
	List<SourceFile> generateIdl2SourceFiles();
}
