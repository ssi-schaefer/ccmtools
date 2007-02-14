package ccmtools.generator.java.metamodel;

import java.util.List;

import ccmtools.parser.assembly.metamodel.Model;
import ccmtools.utils.SourceFile;

public interface JavaApplicationGeneratorElement
{
    List<SourceFile> generateApplicationSourceFiles();
    List<SourceFile> generateAssemblySourceFiles(Model assemblies);
}