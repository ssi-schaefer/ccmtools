/* CCM Tools : ant tasks
 * Egon Teiniker <egon.teiniker@fh-joanneum.at>
 * Copyright (C) 2002 - 2007 ccmtools.sourceforge.net
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library; if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */

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


/**
 * This <idlj> ant task is used to execute java's IDL compiler 
 * from an ant build file.
 *
 * TODO: there are some idlj options that have not been implemented yet 
 * as ant attributes or nested elements: 
 *  
 *  -d <symbol>
 *      This is equivalent to the following line in an IDL file:  
 *      #define <symbol>
 *      
 *  -pkgPrefix <t> <prefix>   
 *      When the type or module name <t> is encountered at file scope, 
 *      begin the Java package name for all files generated for <t> 
 *      with <prefix>.
 *      
 *  -pkgTranslate <t> <pkg>   
 *      When the type or module name <t> in encountered, replace
 *      it with <pkg> in the generated java package.  Note that
 *      pkgPrefix changes are made first.  <t> must match the
 *      full package name exactly.  Also, <t> must not be
 *      org, org.omg, or any subpackage of org.omg.       
 */
public class IdljTask
    extends Task
{    
    /** 
     * Attribute: keep 
     * If a file to be generated already exists, do not overwrite it.  
     * By default it is overwritten.
     */
    private boolean keep = false;
    public void setKeep(boolean keep)
    {
        this.keep = keep;
    }
    
    
    /** 
     * Attribute: emitAll 
     * Emit all types, including those found in #included files.
     */
    private boolean emitAll = false;
    public void setEmitAll(boolean emitAll)
    {
        this.emitAll = emitAll;
    }

    
    /** 
     * Attribute: noWarn 
     * Suppress warnings.
     */
    private boolean noWarn = false;
    public void setNoWarn(boolean noWarn)
    {
        this.noWarn = noWarn;
    }

    
    /** 
     * Attribute: oldImplBase
     * Generate skeletons compatible with old (pre-1.4) JDK ORBs.
     */
    private boolean oldImplBase = false;
    public void setOldImplBase(boolean oldImplBase)
    {
        this.oldImplBase = oldImplBase;
    }

    
    /** 
     * Attribute: verbose
     * Verbose mode.
     */
    private boolean verbose = false;
    public void setVerbose(boolean verbose)
    {
        this.verbose = verbose;
    }

    
    /** 
     * Attribute: skeletonName="<xxx%yyy>"
     * Name the skeleton according to the pattern.
     * The defaults are:
     *  %POA for the POA base class (-fserver or -fall)
     *  _%ImplBase for the oldImplBase base class
     */
    private String skeletonName;
    public void setSkeletonName(String skeletonName)
    {
        this.skeletonName = skeletonName;
    }

        
    /** 
     * Attribute: tieName="<xxx%yyy>"
     * Name the tie according to the pattern.  
     * The defaults are:
     *  %POATie for the POA tie (-fserverTie or -fallTie)
     *  %_Tie for the oldImplBase tie
     */
    private String tieName;
    public void setTieName(String tieName)
    {
        this.tieName = tieName;
    }
    
    
    /** 
     * Attribute: binding="f<side>" 
     * Define what bindings to emit.
     * <side> is one of client, server, all, serverTIE, allTIE.  
     * serverTIE and allTIE cause delegate model skeletons to be emitted.  
     * If this flag is not used, -fclient is assumed.
     */
    private String binding = "fclient";
    public void setBinding(BindingType binding)
    {
        this.binding = binding.getValue();
    }

    
    /** 
     * Attribute: destdir="<dir>" 
     * Use <dir> for the output directory instead of the current directory.
     */
    private File destDir = new File("./");
    public void setDestdir(File destDir)
    {
        this.destDir = destDir;
    }

    
    /** 
     * Nested element: <include>  
     * By default, the current directory is scanned for included files.  
     * This option adds another directory.
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
     * Nested element: fileset 
     * Specifies a set of input files for the IDL generator.
     */
    private List<FileSet> filesets = new ArrayList<FileSet>();
    
    public void addFileset(FileSet fileset)
    {
        filesets.add(fileset);
    }
    

    /**
     * Execute the <ccmtools> task based on the given attributes and
     * nested elements.
     * To run the IDL generator, a helper method is called for every
     * single input file.
     */
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

    
    /**
     * Helper method to execute java's idlj script.
     * Note that idlj must be reachable from the environment's PATH variable. 
     */
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
        cmdline.createArgument().setLine("-td \"" + destDir.getAbsolutePath() + "\"");
        for(String s : includePaths)
        {
            cmdline.createArgument().setLine("-i \"" + s + "\"");
        }
        cmdline.createArgument().setValue("\"" + idlFile.getAbsolutePath() + "\"");
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
    
    
    /**
     * Helper class to log the given attributes and nested elements of
     * the <idlj> task (shown in ant's verbose mode).
     */
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
