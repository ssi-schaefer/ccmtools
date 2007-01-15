package ccmtools.generator.java.metamodel;

import java.util.List;

import ccmtools.utils.SourceFile;

public interface JavaCorbaAdapterGeneratorElement
{
    List<SourceFile> generateCorbaAdapterSourceFiles();
}
