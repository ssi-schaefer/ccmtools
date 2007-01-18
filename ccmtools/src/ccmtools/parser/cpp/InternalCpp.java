package ccmtools.parser.cpp;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import ccmtools.CcmtoolsException;
import ccmtools.ui.UserInterfaceDriver;

public class InternalCpp
    implements PreProcessor
{
    public void process(UserInterfaceDriver uiDriver, String idlFileName, List<String> includes) 
        throws CcmtoolsException
    {
        try
        {
            File idlFile = new File(idlFileName);
            File tmpIdlFile = new File(idlFileName + ".tmp");
            OutputStream out = new FileOutputStream(tmpIdlFile);
            uiDriver.printMessage("use internal preprocessor: " + idlFile);
            JPP jpp = new JPP(idlFile.getAbsolutePath(), out, new Vector(includes), new Hashtable());
            jpp.preprocess();
            out.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new CcmtoolsException("[Internal preprocessor] " + e.getMessage());
        }
    }
}
