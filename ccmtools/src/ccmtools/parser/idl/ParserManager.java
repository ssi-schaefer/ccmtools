package ccmtools.parser.idl;

import java.io.File;
import java.io.FileReader;
import java.io.StringReader;
import java.util.List;

import ccmtools.CcmtoolsException;
import ccmtools.parser.cpp.CppManager;
import ccmtools.parser.idl.metamodel.BaseIDL.MContainer;
import ccmtools.ui.UserInterfaceDriver;

public class ParserManager
{

    public static MContainer loadCcmModel(UserInterfaceDriver uiDriver, String idlSource) 
        throws CcmtoolsException
    {
        try
        {
            uiDriver.printMessage("parse");
            ParserHelper.getInstance().init();
            IdlScanner scanner = new IdlScanner(new StringReader(idlSource));
            IdlParser parser = new IdlParser(scanner);
            MContainer ccmModel = (MContainer) parser.parse().value;
            uiDriver.printMessage("done");
            return ccmModel;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new CcmtoolsException(e.getMessage());
        }
    }

    public static MContainer loadCcmModel(UserInterfaceDriver uiDriver, String idlFileName, List<String> includePaths)
        throws CcmtoolsException
    {
        try
        {            
            CppManager.createCpp().process(uiDriver, idlFileName, includePaths);
            MContainer ccmModel = parseIdlFile(uiDriver, idlFileName);
            return ccmModel;
        }
        catch (Exception e)
        {
            throw new CcmtoolsException(e.getMessage());
        }
    }


    public static MContainer parseIdlFile(UserInterfaceDriver uiDriver, String idlFileName)
        throws CcmtoolsException
    {
        return  parseIdlFile(uiDriver,idlFileName, true);
    }
    
    public static MContainer parseIdlFile(UserInterfaceDriver uiDriver, String idlFileName, boolean deleteOnExit)
        throws CcmtoolsException
    {
        try
        {
            File idlFile = new File(idlFileName);
            File tmpIdlFile = new File(idlFileName + ".tmp");
            if(deleteOnExit)
            {
                tmpIdlFile.deleteOnExit();
            }
            uiDriver.printMessage("use JFlex&Cup based IDL parser: " + tmpIdlFile);
            ParserHelper.getInstance().init();
            ParserHelper.getInstance().setMainSourceFile(idlFile.getAbsolutePath());
            IdlScanner scanner = new IdlScanner(new FileReader(tmpIdlFile));
            IdlParser parser = new IdlParser(scanner);
            MContainer ccmModel = (MContainer) parser.parse().value;

            String identifier = idlFileName.substring(0, idlFileName.lastIndexOf(".idl"));
            ccmModel.setIdentifier(identifier);
            uiDriver.printMessage("done");
            return ccmModel;
        }
        catch (Exception e)
        {
            throw new CcmtoolsException(e.getMessage());
        }
    }

    
    public static void reportError(String message)
    {
        StringBuilder out = new StringBuilder();
        out.append("ERROR [IDL parser]: ");
        out.append(message);
        throw new RuntimeException(out.toString());
    }
}
