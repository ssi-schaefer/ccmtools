package ccmtools.Deployment;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class DeploymentParameters
{
    /** Path to the output directory */
    private String outDir;
    
    /** Qualified name of the assembly object */ 
    private String assemblyObject;
    
    /** Name of the assembly header file */
    private String assemblyFile;
    
    /** Name used to register a component home object to the HomeFinder */
    private String homeFinderName;
    
    /** List of possible include paths */    
    private List includePaths = new ArrayList();
    
    /** Component home IDL file */
    private String homeIdl;

    
    public String getAssemblyFile()
    {
        return assemblyFile;
    }
    
    public void setAssemblyFile(String assemblyFile)
    {
        this.assemblyFile = assemblyFile;
    }

    
    public String getAssemblyObject()
    {
        return assemblyObject;
    }
    
    public void setAssemblyObject(String assemblyObject)
    {
        this.assemblyObject = assemblyObject;
    }

    
    public String getHomeFinderName()
    {
        return homeFinderName;
    }
    
    public void setHomeFinderName(String homeFinderName)
    {
        this.homeFinderName = homeFinderName;
    }

    
    public String getHomeIdl()
    {
        return homeIdl;
    }
    
    public void setHomeIdl(String homeIdl)
    {
        this.homeIdl = homeIdl;
    }

    
    public List getIncludePaths()
    {
        return includePaths;
    }

    
    public String getOutDir()
    {
        return outDir;
    }
    
    public void setOutDir(String outDir)
    {
        this.outDir = outDir;
    }    

            
    public String toString()
    {
        StringBuffer buffer = new StringBuffer();
        if(homeIdl != null) {
             buffer.append("Component Home = ");
             buffer.append(homeIdl).append("\n");
        }
        if(homeFinderName != null) {
             buffer.append("HomeFinder Name = ");
             buffer.append(homeFinderName).append("\n");
        }
        if(assemblyObject != null) {
            buffer.append("Assembly Object = ");
            buffer.append(assemblyObject).append("\n");
        }
        if(assemblyFile != null) {
            buffer.append("Assembly File = ");
            buffer.append(assemblyFile).append("\n");
        }
        
        for(Iterator i=includePaths.iterator(); i.hasNext();) {
            buffer.append("Include Path = ");
            buffer.append((String)i.next()).append("\n");
        }
        
        if(outDir != null) {
            buffer.append("Output Path = ");
            buffer.append(outDir).append("\n");
        }
        return buffer.toString();
    }
}
