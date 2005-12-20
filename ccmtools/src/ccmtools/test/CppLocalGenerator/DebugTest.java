package ccmtools.test.CppLocalGenerator;

import ccmtools.UI.Main;
import ccmtools.test.CcmtoolsTestCase;

/**
 * @author eteinik
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class DebugTest extends CcmtoolsTestCase
{

    private String ccmtoolsDir;
    private String testDir;
    
    public DebugTest(String name)
    {
        super(name);
        // get current working directory (this is where build.xml is executed)
        ccmtoolsDir = System.getProperty("user.dir");
        testDir = ccmtoolsDir + "/test/CppGenerator";
    }

    
    /**
     * Calling make from Java is a hack, but practical experiances have shown
     * that using the same Makefile for C++ debugging and Java unit tests
     * reduces development time significantly - as long as you work with Linux...
     */
    public void testDebug()
    {
        try {
            String[] args = {
                    ""
            };
                
            Main.main(args);
            executeCommandLine("make -C " + testDir + "/facet_exception test");
        }
        catch(Exception e) {
            fail();
        }
    }
}