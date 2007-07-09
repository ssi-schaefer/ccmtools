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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.DirectoryScanner;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.Task;
import org.apache.tools.ant.types.FileSet;

import ccmtools.utils.ConfigurationLocator;

/**
 * This <ccmtools> ant task is used to execute different ccmtools generators 
 * from an ant build file.
 */
public class CcmtoolsTask
    extends Task
{        
    /** 
     * attribute: generator 
     * Defines the ccmtools generator type (see GeneratorType)
     */
    private String generator;
    public void setGenerator(GeneratorType generator)
    {
        this.generator=generator.getValue();
    }
       
    
    /** 
     * Attribute: validate 
     * Forces the execution of ccmtools' model validator which ensures
     * that a given IDL file conforms to the supported ccmtools metamodel.
     */    
    private boolean validate = false;
    public void setValidate(boolean validate)
    {
        this.validate=validate;
    }
    
    
    /** 
     * Attribute: destdir 
     * Defines the generator's output directory.
     */
    private File destDir = new File("./");
    public void setDestdir(File destDir)
    {
        this.destDir = destDir;
    }

    
    /** 
     * Nested element: <include>  
     * Contains a path attribute to specify an include path for the given
     * generator (see IncludePath).
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
     * Nested element: <fileset> 
     * Specifies a set of input files for the given generator.
     */
    private List<FileSet> filesets = new ArrayList<FileSet>();    
    public void addFileset(FileSet fileset)
    {
        filesets.add(fileset);
    }
    
    
    /**
     * Execute the <ccmtools> task based on the given attributes and
     * nested elements.
     * To run the ccmtools generators, their static main methods are called
     * with a particular args array.
     */
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
            executeModelTools(generator);
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


    /**
     * Helper method to execute the ccmtools.parser.idl.metamodel.Main class. 
     */
    protected void executeModelTools(String type)
    {
        StringBuilder cmd = new StringBuilder();
        
        if(type.equals("model.validator"))
        {
            cmd.append("-validator");
        }
        else if(type.equals("model.parser"))
        {
            cmd.append("-parser");
        }
         
        
        for(String s : includePaths)
        {
            appendIncludePath(cmd, s);
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
        
        log(">> ccmmodel " + cmd.toString());
        String[] args = cmd.toString().split(" ");
        ccmtools.parser.idl.metamodel.Main.main(args);                
    }
    
    
    /**
     * adds an include path to an external command
     */
    static void appendIncludePath( StringBuilder cmd, String s )
    {
    	if(ConfigurationLocator.getInstance().isWindows())
    		cmd.append(" -I\"").append(s).append("\"");
        else
            cmd.append(" -I").append(s);
    }
    

    /**
     * Helper method to execute the ccmtools.generator.idl.Main class. 
     */
    protected void executeIdlGenerator()
    {
        if(validate)
        {
            executeModelTools("model.validator");
        }
        
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
            appendIncludePath(cmd, s);
        }
        
        cmd.append(" -o ").append(destDir.getAbsolutePath());

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
        
        log(">> ccmidl " + cmd.toString());
        String[] args = cmd.toString().split(" ");
        ccmtools.generator.idl.Main.main(args);                
    }


    /**
     * Helper method to execute the ccmtools.generator.java.Main class. 
     */
    protected void executeJavaGenerator()
    {
        if(validate)
        {
            executeModelTools("model.validator");
        }

        StringBuilder cmd = new StringBuilder();
        
        if(generator.equals("java.local"))
        {
            cmd.append("-iface -local");
        }        
        else if(generator.equals("java.local.iface"))
        {
            cmd.append("-iface");
        }
        else if(generator.equals("java.local.adapter"))
        {
            cmd.append("-local");
        }
        else if(generator.equals("java.impl"))
        {
            cmd.append("-app");
        }
        else if(generator.equals("java.remote.adapter"))
        {
            cmd.append("-remote");
        }
        else if(generator.equals("java.clientlib"))
        {
            cmd.append("-clientlib");
        }
            
        
        for(String s : includePaths)
        {
            appendIncludePath(cmd, s);
        }
        
        cmd.append(" -o ").append(destDir.getAbsolutePath());
        
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
        
        log(">> ccmjava " + cmd.toString());
        String[] args = cmd.toString().split(" ");
        ccmtools.generator.java.Main.main(args);                
    }

    
    /**
     * Helper class to log the given attributes and nested elements of
     * the <ccmtools> task (shown in ant's verbose mode).
     */
    public void logTask()
    {
        // Log ant task parameters
        String home = getProject().getProperty("ccmtools.home");
        log("property ccmtools.home = " + home, Project.MSG_VERBOSE);        
        log("attribute generator =  " + generator, Project.MSG_VERBOSE);
        log("attribute validate =  " + validate, Project.MSG_VERBOSE);
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
