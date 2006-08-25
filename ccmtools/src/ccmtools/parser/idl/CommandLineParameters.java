package ccmtools.parser.idl;


import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;



public class CommandLineParameters
{
	/** */
	private boolean noExit;
	
    /** List of possible include paths */    
    private List<String> includePaths = new ArrayList<String>();

    /** Path to the output directory */
    private String outDir;
    
    /** List of input files */
    private List<String> inputFiles = new ArrayList<String>();
    
    
    // Parameter getter and setter methods ------------------------------------
    
	public List<String> getIncludePaths()
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

	
	public boolean isNoExit()
	{
		return noExit;
	}

	public void setNoExit(boolean noExit)
	{
		this.noExit = noExit;
	}

	
	public List<String> getIdlFiles()
	{
		return inputFiles;
	}


	
	
	// Parameter validation methods -------------------------------------------
	
	public void validate()
		throws Idl3ParserException
	{
		checkOutputPath();
		checkInputFiles();
	}
	
	
    /**
     * Check if the given output directory is valid.
     * 
     * @throws GeneratorException
     */
    private void checkOutputPath() 
        throws Idl3ParserException
    {
        if(outDir == null || outDir.length() == 0) 
        {
        	    outDir = ".";
        }
    }
	 
    
    /**
     * Check if the given IDL file exists.
     * @throws GeneratorException
     */
    private void checkInputFiles() 
        throws Idl3ParserException
    {
    	 for(Iterator i = getIdlFiles().iterator(); i.hasNext();) 
         {
             String idlFile = (String) i.next();
             if(!isExistingFile(idlFile, getIncludePaths())) 
             {
                 throw new Idl3ParserException("Invalid IDL input file " + idlFile);
             }
         }
    }
    
    private boolean isExistingFile(String fileName, List includePaths)
    {
        // File without name does not exist
        if(fileName == null)
            return false;
        
        // OK, if the given file exists
        File file = new File(fileName);
        if(file.exists()) 
            return true;
        
        // OK, if the assembly file can be found in any given
        // include path
        boolean hasFound = false;
        for(Iterator i = includePaths.iterator(); i.hasNext();) 
        {
            File path = new File((String) i.next(), fileName);
            if(path.exists()) 
            {
                hasFound = true;
            }
        }    
        return hasFound; 
    }
    
    
    public String toString()
    {
        StringBuilder buffer = new StringBuilder();

        buffer.append("No Exit: ").append(isNoExit()).append("\n");
        
        for(String includePath : includePaths)  
        {
            buffer.append("Include Path: ");
            buffer.append(includePath).append("\n");
        }

        if(getOutDir() != null)
        {
            buffer.append("Output directory: ").append(getOutDir()).append("\n");
        }
        
        for(String idlFile : getIdlFiles())         
        {
            buffer.append("IDL input files: ");
            buffer.append(idlFile).append("\n");
        }
        
        return buffer.toString();        
    }
}
