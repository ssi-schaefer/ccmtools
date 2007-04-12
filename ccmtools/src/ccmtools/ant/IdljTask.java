package ccmtools.ant;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.DirectoryScanner;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.Task;
import org.apache.tools.ant.taskdefs.Execute;
import org.apache.tools.ant.taskdefs.LogStreamHandler;
import org.apache.tools.ant.types.Commandline;
import org.apache.tools.ant.types.FileSet;

public class IdljTask
    extends Task
{    
    /** 
     * Handle attribute keep 
     * If a file to be generated already exists, do not overwrite it.  
     * By default it is overwritten.
     */
    private boolean keep = false;
    public void setKeep(boolean keep)
    {
        this.keep = keep;
    }
    
    
    /** 
     * Handle attribute emitAll 
     * Emit all types, including those found in #included files.
     */
    private boolean emitAll = false;
    public void setEmitAll(boolean emitAll)
    {
        this.emitAll = emitAll;
    }

    
    /** 
     * Handle attribute noWarn 
     * Suppress warnings.
     */
    private boolean noWarn = false;
    public void setNoWarn(boolean noWarn)
    {
        this.noWarn = noWarn;
    }

    
    /** 
     * Handle attribute oldImplBase
     * 
     */
    private boolean oldImplBase = false;
    public void setOldImplBase(boolean oldImplBase)
    {
        this.oldImplBase = oldImplBase;
    }

    
    /** 
     * Handle attribute verbose
     * 
     */
    private boolean verbose = false;
    public void setVerbose(boolean verbose)
    {
        this.verbose = verbose;
    }

    
    /** 
     * Handle attribute skeletonName
     * 
     */
    private String skeletonName;
    public void setSkeletonName(String skeletonName)
    {
        this.skeletonName = skeletonName;
    }

        
    /** 
     * Handle attribute tieName
     * 
     */
    private String tieName;
    public void setTieName(String tieName)
    {
        this.tieName = tieName;
    }
    
    
    /** 
     * Handle attribute binding 
     * 
     */
    private String binding = "fall";
    public void setBinding(BindingType binding)
    {
        this.binding = binding.getValue();
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
     * Handle nested <include> elements 
     * 
     */
    private List<String> includePaths = new ArrayList<String>();
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
        for(IncludePath p : includes)
        {
            includePaths.addAll(Arrays.asList(p.getPaths()));
        }

        logTask();
    
        for(FileSet fs: filesets)
        {
            DirectoryScanner ds = fs.getDirectoryScanner(getProject());
            String[] includedFiles = ds.getIncludedFiles();
            for(int i=0; i<includedFiles.length; i++)
            {
                File file = new File(ds.getBasedir(),includedFiles[i]);
                runIdlCompiler(file);
            }            
        }        
    }

    
    public void runIdlCompiler(File idlFile)
    {
        // Construct the command line 
        Commandline cmdline = new Commandline();
        cmdline.setExecutable("idlj");
        if(keep)
        {
            cmdline.createArgument().setValue("-keep");
        }
        if(emitAll)
        {
            cmdline.createArgument().setValue("-emitAll");
        }
        if(noWarn)
        {
            cmdline.createArgument().setValue("-noWarn");
        }
        if(oldImplBase)
        {
            cmdline.createArgument().setValue("-oldImplBase");
        }
        if(verbose)
        {
            cmdline.createArgument().setValue("-verbose");
        }
        if(skeletonName != null)
        {
            cmdline.createArgument().setLine("-skeletonName " + skeletonName);
        }
        if(tieName != null)
        {
            cmdline.createArgument().setLine("-tieName " + tieName);
        }
        
        
        cmdline.createArgument().setValue("-" + binding);
        cmdline.createArgument().setLine("-td " + destDir.getAbsolutePath());
        for(String s : includePaths)
        {
            cmdline.createArgument().setLine("-i " + s);
        }
        cmdline.createArgument().setValue(idlFile.getAbsolutePath());
        log("command line = " + cmdline, Project.MSG_VERBOSE);
        
        // Configure the Execute object
        LogStreamHandler streamHandler = new LogStreamHandler(this, Project.MSG_INFO, Project.MSG_WARN);
        Execute runner = new Execute(streamHandler, null);
        runner.setAntRun(getProject());
        runner.setCommandline(cmdline.getCommandline());
        
        // Execute the command line
        int result = 0;
        try
        {
            log("compile " + idlFile.getAbsolutePath());
            result = runner.execute();
            log("result = " + result, Project.MSG_VERBOSE);
        }
        catch(IOException e)
        {
            log(e.getMessage(), Project.MSG_DEBUG);
            throw new BuildException(e.getMessage());
        }                
    }
    
    
    public void logTask()
    {
        // Log ant task parameters
        log("attribute keep = " + keep, Project.MSG_VERBOSE);
        log("attribute emitAll = " + emitAll, Project.MSG_VERBOSE);
        log("attribute noWarn = " + noWarn, Project.MSG_VERBOSE);
        log("attribute oldImplBase = " + oldImplBase, Project.MSG_VERBOSE);
        log("attribute verbose = " + verbose, Project.MSG_VERBOSE);
        log("attribute skeletonName = " + skeletonName, Project.MSG_VERBOSE);
        log("attribute tieName = " + tieName, Project.MSG_VERBOSE);
                
        log("attribute binding = " + binding, Project.MSG_VERBOSE);
        
        if(destDir != null)
            log("attribute destdir = " + destDir.getAbsolutePath(), Project.MSG_VERBOSE);
            
        for(String s : includePaths)
        {
            log("nested element include = " + s, Project.MSG_VERBOSE);
        }
        
        for(FileSet fs: filesets)
        {
            DirectoryScanner ds = fs.getDirectoryScanner(getProject());
            String[] includedFiles = ds.getIncludedFiles();
            for(int i=0; i<includedFiles.length; i++)
            {
                File file = new File(ds.getBasedir(),includedFiles[i]);
                log("nested element fileset = " + file.getAbsolutePath(), Project.MSG_VERBOSE);
            }            
        }
    }
}
