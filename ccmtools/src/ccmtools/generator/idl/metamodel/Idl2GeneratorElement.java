package ccmtools.generator.idl.metamodel;

import java.util.List;

import ccmtools.utils.SourceFile;


public interface Idl2GeneratorElement
{
    String runIdl2Generator();
    
	String generateIdl2();
	
    String generateIdl2IncludePath();
    
	List<SourceFile> generateIdl2SourceFiles();
}
