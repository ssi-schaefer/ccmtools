package ccmtools.parser.idl;



import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.math.BigInteger;
import java.util.List;

import ccmtools.CcmtoolsException;
import ccmtools.Constants;
import ccmtools.metamodel.BaseIDL.MContainer;
import ccmtools.ui.UserInterfaceDriver;
import ccmtools.utils.CcmtoolsProperties;


public class ParserHelper
{
    private static ParserHelper instance = null;
    
    private int currentSourceLine;    
    private String currentSourceFile;
    private String mainSourceFile;
    
    private IdentifierTable typeIdTable = new IdentifierTable();
    private IdentifierTable forwardDclTable = new IdentifierTable();     
    private Scope scope = new Scope();
    
    public static ParserHelper getInstance()
    {
        if(instance == null)
        {
            instance = new ParserHelper();
        }
        return instance;
    }
    
    private ParserHelper()
    {
        init();        
    }
    
    
    public void init()
    {
        System.out.println("init ParserHelper");
        
        typeIdTable.clear();
        forwardDclTable.clear();
        scope.clear();
        
        currentSourceLine = 0;
        currentSourceFile = "";
        mainSourceFile = "";
    }
    
    
    public int getCurrentSourceLine()
    {
        return currentSourceLine;
    }
    private void setCurrentSourceLine(int value)
    {
        currentSourceLine = value;
    }
    public void incrementCurrentSourceLine()
    {
        currentSourceLine++;
    }
    
    
    public String getCurrentSourceFile()
    {
        return currentSourceFile;
    }
    private void setCurrentSourceFile(String value)
    {
        currentSourceFile = value;
    }
    
    public String getMajorSourceFile()
    {
        return mainSourceFile;
    }
    public void setMainSourceFile(String value)
    {
        mainSourceFile = value;
    }

    
    public void registerTypeId(String name)
    {
        String absoluteId = scope + name;
        System.out.println("registerType: " + absoluteId);
        if(!(typeIdTable.register(new Identifier(absoluteId))))
        {      
            throw new RuntimeException(getCurrentSourceFile() + " line " + getCurrentSourceLine() + " : "
                    + "Re-defined type identifier '" + name + "'");            
        }
    }

    public void registerForwardDclId(String name)
    {
        String absoluteId = scope + name;
        System.out.println("registerForwardDcl: " + absoluteId);
        forwardDclTable.register(new Identifier(absoluteId));
    }

    
    public Scope getScope()
    {
        return scope;
    }
    
    
    
    /*
     * Utility Methods
     */
    
    // # linenumber filename flags
    // # 1 "/home/eteinik/sandbox/workspace-development/TelegramGenerator/examples/simple_test/example1.tgen"
    public void handlePreprocessorLine(String line)
    {
        line = line.substring(0, line.lastIndexOf('\n'));
//        System.out.println("CPP: " + line);

        String[] elements = line.split(" ");
        if (elements[0].equals("#"))
        {
            if (elements.length >= 3)
            {
                if(elements[2].startsWith("\"<"))
                {
                    // e.g. <build-in> or <command line>
                }
                else
                {
                    setCurrentSourceLine(Integer.parseInt(elements[1]));
                    String fileName = elements[2];
                    setCurrentSourceFile(fileName.substring(fileName.indexOf('\"')+1, fileName.lastIndexOf('\"')));
                }
            }
        }
    }
 
    
    // #pragma 
    public void handlePragmaLine(String line)
    {
        line = line.substring(0, line.lastIndexOf('\n'));
        // TODO
        System.out.println("CPP: " + line);
    }

    
    /*
     * Factory methods 
     */
    
    public Double createFloat(String in)
    {
        return Double.parseDouble(in);
    }
    
    public BigInteger createFixed(String in)
    {
        return null;
    }
    
    public Integer createInteger(String in)
    {
        return Integer.parseInt(in);
    }
    
    public Integer createOctet(String in)
    {
        return null;
    }
    
    public Integer createHex(String in)
    {
        return null;
    }
    
    public Character createChar(String in)
    {
        return null;
    }
    
    public Character createWChar(String in)
    {
        return null;
    }
    
    public String createString(String in)
    {
        return null;
    }
    
    public String createWString(String in)
    {
        return null;
    }

    public MContainer loadCcmModel(UserInterfaceDriver uiDriver, String idlSource)
        throws CcmtoolsException
    {
        try
        {
            uiDriver.printMessage("parse");
            ParserHelper.getInstance().init();
            Idl3Scanner scanner = new Idl3Scanner(new StringReader(idlSource));
            Idl3Parser parser = new Idl3Parser(scanner);
            MContainer ccmModel = (MContainer) parser.parse().value;
            uiDriver.printMessage("done");
            return ccmModel;
        }
        catch (Exception e)
        {
            throw new CcmtoolsException(e.getMessage());
        }
    }


    public MContainer loadCcmModel(UserInterfaceDriver uiDriver, String idlFileName, List<String> includePaths)
        throws CcmtoolsException
    {    
        try
        {
            File idlFile = new File(idlFileName);
            String tmpFileName = idlFile.getName() + ".tmp";
            File tmpIdlFile = new File(tmpFileName);
            useCpp(uiDriver, idlFile.getAbsolutePath(), includePaths, tmpFileName);

            uiDriver.printMessage("parse " + tmpFileName);
            ParserHelper.getInstance().init();
            ParserHelper.getInstance().setMainSourceFile(idlFile.getAbsolutePath());
            Idl3Scanner scanner = new Idl3Scanner(new FileReader(tmpIdlFile));
            Idl3Parser parser = new Idl3Parser(scanner);                    
            MContainer ccmModel = (MContainer)parser.parse().value;    
            uiDriver.printMessage("done");
            return ccmModel;
        }
        catch (Exception e)
        {
            throw new CcmtoolsException(e.getMessage());
        }
    }
    
    private void useCpp(UserInterfaceDriver uiDriver, String sourceFileName, List<String> includes, String tmpFileName)
        throws CcmtoolsException
    {
        File tmpFile = new File(System.getProperty("user.dir"), tmpFileName);
        try
        {
            // Run a C preprocessor on the input file, in a separate process.
            StringBuffer cmd = new StringBuffer();
            if (CcmtoolsProperties.Instance().get("ccmtools.cpp").length() != 0)
            {
                cmd.append(CcmtoolsProperties.Instance().get("ccmtools.cpp"));
            }
            else
            {
                cmd.append(Constants.CPP_PATH);
            }
            cmd.append(" ");
            for (String includePath : includes)
            {
                cmd.append("-I").append(includePath).append(" ");
            }
            cmd.append(sourceFileName);
            uiDriver.printMessage(cmd.toString()); // print cpp command line

            Process preproc = Runtime.getRuntime().exec(cmd.toString());
            BufferedReader stdInput = new BufferedReader(new InputStreamReader(preproc.getInputStream()));
            BufferedReader stdError = new BufferedReader(new InputStreamReader(preproc.getErrorStream()));

            // Read the output and any errors from the command
            String s;
            StringBuffer code = new StringBuffer();
            while ((s = stdInput.readLine()) != null)
            {
                code.append(s).append("\n");
            }
            while ((s = stdError.readLine()) != null)
            {
                uiDriver.printMessage(s);
            }

            // Wait for the process to complete and evaluate the return
            // value of the attempted command
            preproc.waitFor();
            if (preproc.exitValue() != 0)
            {
                throw new RuntimeException("Preprocessor Error: Please verify your include paths or file names ("
                        + sourceFileName + ")!!");
            }
            else
            {
                FileWriter writer = new FileWriter(tmpFile);
                writer.write(code.toString(), 0, code.toString().length());
                writer.close();
            }
//          tmpFile.deleteOnExit();
        }
        catch (Exception e)
        {
            throw new CcmtoolsException(e.getMessage());
        }
    }

}
