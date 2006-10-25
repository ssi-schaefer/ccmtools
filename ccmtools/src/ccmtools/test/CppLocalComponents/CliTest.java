package ccmtools.test.CppLocalComponents;

import junit.framework.Test;
import ccmtools.test.CcmtoolsTestCase;

public class CliTest
	extends CcmtoolsTestCase
{
    public CliTest(String name)
    {
        super(name);
    }

    public static Test suite()
    {
        return new junit.framework.TestSuite(CliTest.class);
    }
    
    
    // ------------------------------------------------------------------------
    // CLI test cases
    // ------------------------------------------------------------------------
    
    public void testVersionOption()
    {
        runCcmtoolsGenerate("--version");
    }

    public void testHelpOption()
    {
        runCcmtoolsGenerate("--help");
    }	
}
