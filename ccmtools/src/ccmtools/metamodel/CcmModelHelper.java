package ccmtools.metamodel;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import ccmtools.CcmtoolsException;
import ccmtools.Constants;

import ccmtools.metamodel.BaseIDL.MContained;
import ccmtools.metamodel.BaseIDL.MContainer;
import ccmtools.metamodel.BaseIDL.MModuleDef;
import ccmtools.ui.UserInterfaceDriver;
import ccmtools.utils.CcmtoolsProperties;
import ccmtools.utils.Text;

public class CcmModelHelper
{

    /*
     * Handle IDL files  
     */
    
    public static  MContainer loadCcmModel(UserInterfaceDriver uiDriver, String fileName, List includes) 
        throws CcmtoolsException
    {
        StringBuilder includePath = new StringBuilder();
        for(Iterator i = includes.iterator(); i.hasNext();)
        {
            includePath.append("-I").append((String) i.next()).append(" ");
        }
        return loadCcmModel(uiDriver, fileName, includePath.toString());
    }
    
    
    public static MContainer loadCcmModel(UserInterfaceDriver uiDriver, String fileName, String includePath) 
        throws CcmtoolsException
    {
        MContainer ccmModel = null;
        File source = new File(fileName);
        try
        {
            // Create the name of the temporary idl file generated from the C preprocessor 
            String tmpFile = "_CCM_" + source.getName();
            File idlfile = new File(System.getProperty("user.dir"), tmpFile.substring(0, tmpFile.lastIndexOf(".idl")));

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
            cmd.append(" ").append(includePath).append(" ");
            cmd.append(source);
            uiDriver.printMessage(cmd.toString()); // show cpp command line

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
                throw new CcmtoolsException("Preprocessor: Please verify your include paths or file names (" 
                        + source + ").");
            }
            else
            {
                FileWriter writer = new FileWriter(idlfile);
                writer.write(code.toString(), 0, code.toString().length());
                writer.close();
            }

            // Parse the resulting preprocessed file.
            // uiDriver.printMessage("parse " + idlfile.toString());
            ccmtools.parser.idl3.ParserManager manager = new ccmtools.parser.idl3.ParserManager(source.toString());

            ccmModel = manager.parseFile(idlfile.toString());
            if (ccmModel == null)
            {
                throw new CcmtoolsException("Parser error " + source + ":\n" + "parser returned an empty CCM model");
            }
            String kopf_name = source.getName();// .split("\\.")[0];
            kopf_name = kopf_name.replaceAll("[^\\w]", "_");
            ccmModel.setIdentifier(kopf_name);
            idlfile.deleteOnExit();
        }
        catch (Exception e)
        {
            throw new CcmtoolsException("loadCcmModel():" + e.getMessage());
        }
        return ccmModel;
    }


    /*
     * Handle IDL repository IDs
     */
    
    public static String getRepositoryId(MContained node)
    {
    		return "IDL:" + CcmModelHelper.getAbsoluteName(node, "/") + ":1.0";
    }


    public static String getRepositoryId(String name)
    {
        return "IDL:" + name + ":1.0";
    }


    public static String getRepositoryId(String[] name)
    {
        StringBuffer buffer = new StringBuffer();
        buffer.append("IDL:");
        if(name != null && name.length > 0) 
        {
            buffer.append(name[0]);
            if(name.length > 1) 
            {
                for(int i = 1; i < name.length; i++) 
                {
                    buffer.append("/");
                    buffer.append(name[i]);
                }
            }
        }
        buffer.append(":1.0");
        return buffer.toString();
    }


    public static String getRepositoryId(List ns, String name)
    {
        StringBuffer buffer = new StringBuffer();
        buffer.append("IDL:");
        buffer.append(Text.joinList("/", ns));
        buffer.append("/").append(name);
        buffer.append(":1.0");
        return buffer.toString();
    }

    
    /*
     * Handle model element absolute names
     */

    public static List getListFromAbsoluteName(String name)
    {
        List<String> list = new ArrayList<String>();
        if(name != null) 
        {
            String[] names = name.split("/");
            for(int i = 0; i < names.length; i++) 
            {
                list.add(names[i]);
            }
        }
        return list;
    }

    public static String[] getArrayFromAbsoluteName(String name)
    {
        return name.split("/");
    }

    public static String getNameFromRepositoryId(String repoId)
    {
        return repoId.substring(repoId.indexOf(':')+1, repoId.lastIndexOf(':'));
    }


    public static String getAbsoluteName(MContained node, String sep)
    {
        if (CcmModelHelper.getNamespaceList(node).size() == 0)
        {
            return node.getIdentifier();
        }
        else
        {
            return getNamespace(node, sep) + sep + node.getIdentifier();
        }
    }

    

    /*
     * Handle model element namespaces
     */
    
    public static String getNamespace(MContained node, String sep)
    {
        List nsList = CcmModelHelper.getNamespaceList(node);
        return Text.join(sep, nsList);
    }


    /**
     * Calculates the model element namespace by going back from the
     * model element to the top container element and collecting the 
     * names of all module definitions in-between.
     *
     * @param node
     * @return
     */
    public static List<String> getNamespaceList(MContained node)
    {        
        List<String> scope = new ArrayList<String>();
        MContainer c = node.getDefinedIn();
        while(c.getDefinedIn() != null) 
        {
            if(c instanceof MModuleDef)
            {
                scope.add(0, c.getIdentifier());
            }
            c = c.getDefinedIn();
        }
        return scope;
    }
    
}
