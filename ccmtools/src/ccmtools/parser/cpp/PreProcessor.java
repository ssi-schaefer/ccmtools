package ccmtools.parser.cpp;

import java.util.List;

import ccmtools.CcmtoolsException;
import ccmtools.ui.UserInterfaceDriver;

public interface PreProcessor
{
    void process(UserInterfaceDriver uiDriver, String sourceFileName, List<String> includes)
        throws CcmtoolsException;
}
