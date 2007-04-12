package ccmtools.ant;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.DirectoryScanner;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.Task;
import org.apache.tools.ant.types.FileSet;

public class CcmtoolsTask
    extends Task
{        
    /** 
     * Handle attribute generator 
     * 
     */
    private String generator;
    public void setGenerator(GeneratorType generator)
    {
        this.generator=generator.getValue();
    }
            
    
    /** 
     * Handle attribute destdir 
     * 
     */
    private File destDir = new File("./");
    public void setDestdir(File destDir)
    {
        this.destDir = destDir;
    }

    
    /** 
     * Handle nested includepath elements 
     * 
     */
    private List<String> includePaths;
    private List<IncludePath> includes = new ArrayList<IncludePath>();
    public IncludePath createInclude()
    {
        IncludePath p = new IncludePath();
        includes.add(p);
        return p;
    }

    
    /** 
     * Handle file sets 
     * 
     */
    private List<FileSet> filesets = new ArrayList<FileSet>();    
    public void addFileset(FileSet fileset)
    {
        filesets.add(fileset);
    }
    

    public void execute()
    {
        includePaths = new ArrayList<String>();
        for(IncludePath p : includes)
        {
            includePaths.addAll(Arrays.asList(p.getPaths()));
        }
        
        logTask();
        
        // Setup ccmtools properties
        String home = getProject().getProperty("ccmtools.home");
        if(home == null)
        {
            throw new BuildException("ccmtools.home property is not set!");
        }
        System.setProperty("ccmtools.home", home);
        System.setProperty("java.util.logging.config.file", 
                home + File.separator + "etc" + File.separator + "logging.properties");            

        
        // Call ccmtools generators
        if(generator.startsWith("model"))
        {
            executeModelTools();
        }
        else if(generator.startsWith("idl"))
        {
            executeIdlGenerator();
        }
        else if(generator.startsWith("java"))
        {
            executeJavaGenerator();
        }
    }

    
    protected void executeModelTools()
    {
        StringBuilder cmd = new StringBuilder();
        
        if(generator.equals("model.validator"))
        {
            cmd.append("-validator");
        }
        else if(generator.equals("model.parser"))
        {
            cmd.append("-parser");
        }
         
        
        for(String s : includePaths)
        {
            cmd.append(" -I" + s);
        }        
        
        for(FileSet fs: filesets)
        {
            DirectoryScanner ds = fs.getDirectoryScanner(getProject());
            String[] includedFiles = ds.getIncludedFiles();
            for(int i=0; i<includedFiles.length; i++)
            {
                File file = new File(ds.getBasedir(),includedFiles[i]);
                cmd.append(" ").append(file.getAbsolutePath());
            }            
        }
        
        log("command line = " + cmd.toString(), Project.MSG_VERBOSE);
        String[] args = cmd.toString().split(" ");
        ccmtools.parser.idl.metamodel.Main.main(args);                
    }
    
    
    protected void executeIdlGenerator()
    {
        StringBuilder cmd = new StringBuilder();
        
        if(generator.equals("idl3"))
        {
            cmd.append("-idl3");
        }
        else if(generator.equals("idl3.mirror"))
        {
            cmd.append("-idl3mirror");
        }
        else if(generator.equals("idl2"))
        {
            cmd.append("-idl2");
        }
            

        for(String s : includePaths)
        {
            cmd.append(" -I" + s);
        }
        
        cmd.append(" -o " + destDir.getAbsolutePath());

        for(FileSet fs: filesets)
        {
            DirectoryScanner ds = fs.getDirectoryScanner(getProject());
            String[] includedFiles = ds.getIncludedFiles();
            for(int i=0; i<includedFiles.length; i++)
            {
                File file = new File(ds.getBasedir(),includedFiles[i]);
                cmd.append(" ").append(file.getAbsolutePath());
            }            
        }
        
        log("command line = " + cmd.toString(), Project.MSG_VERBOSE);
        String[] args = cmd.toString().split(" ");
        ccmtools.generator.idl.Main.main(args);                
    }


    protected void executeJavaGenerator()
    {
        StringBuilder cmd = new StringBuilder();
        
        if(generator.equals("java.iface"))
        {
            cmd.append("-iface");
        }
        else if(generator.equals("java.local"))
        {
            cmd.append("-local");
        }
        else if(generator.equals("java.app"))
        {
            cmd.append("-app");
        }
        else if(generator.equals("java.remote"))
        {
            cmd.append("-remote");
        }
        else if(generator.equals("java.clientlib"))
        {
            cmd.append("-clientlib");
        }
            
        
        for(String s : includePaths)
        {
            cmd.append(" -I" + s);
        }
        
        cmd.append(" -o " + destDir.getAbsolutePath());
        
        for(FileSet fs: filesets)
        {
            DirectoryScanner ds = fs.getDirectoryScanner(getProject());
            String[] includedFiles = ds.getIncludedFiles();
            for(int i=0; i<includedFiles.length; i++)
            {
                File file = new File(ds.getBasedir(),includedFiles[i]);
                cmd.append(" ").append(file.getAbsolutePath());
            }            
        }
        
        log("command line = " + cmd.toString(), Project.MSG_VERBOSE);
        String[] args = cmd.toString().split(" ");
        ccmtools.generator.java.Main.main(args);                
    }

    
    public void logTask()
    {
        // Log ant task parameters
        String home = getProject().getProperty("ccmtools.home");
        log("property ccmtools.home = " + home, Project.MSG_VERBOSE);        
        log("attribute generator =  " + generator, Project.MSG_VERBOSE);
        if(destDir != null)
            log("attribute destdir = " + destDir.getAbsolutePath(), Project.MSG_VERBOSE);
            
        List<String> includePaths = new ArrayList<String>();
        for(IncludePath p : includes)
        {
            includePaths.addAll(Arrays.asList(p.getPaths()));
        }
        for(String s : includePaths)
        {
            log("nested element include = " + s, Project.MSG_VERBOSE);
        }
    }
}
