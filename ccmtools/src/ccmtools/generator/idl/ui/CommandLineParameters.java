package ccmtools.generator.idl.ui;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import ccmtools.CcmtoolsException;

public class CommandLineParameters
{
	/** List of generator IDs */
	private List<String> generatorIds = new ArrayList<String>();
	
	/** */
	private boolean noExit;
	
    /** List of possible include paths */    
    private List<String> includePaths = new ArrayList<String>();

    /** Path to the output directory */
    private String outDir;
    
    /** List of IDL input files */
    private List<String> idlFiles = new ArrayList<String>();
    
    
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
		return idlFiles;
	}


	public List<String> getGeneratorIds()
	{
		return generatorIds;
	}
	
	
	// Parameter validation methods -------------------------------------------
	
	public void validate()
		throws CcmtoolsException
	{
		checkGeneratorId();
		checkOutputPath();
		checkIdlFiles();
	}

	/**
	 * Check if at least one geerator type is specified.
	 */
	private void checkGeneratorId()
		throws CcmtoolsException
	{
		if(getGeneratorIds().size() == 0)
		{
			throw new CcmtoolsException("At least one generator type must be specified (e.g. -idl3)!");
		}
	}
    
    /**
     * Check if the given output directory is valid.
     */
    private void checkOutputPath() 
        throws CcmtoolsException
    {
        if(outDir == null || outDir.length() == 0) 
        {
        		outDir = ".";
        }
    }
	    
    /**
     * Check if the given IDL file exists.
     */
    private void checkIdlFiles() 
        throws CcmtoolsException
    {
    	 	for(String idlFile : getIdlFiles())
    	 	{
            if(!isExistingFile(idlFile, getIncludePaths())) 
            {
                 throw new CcmtoolsException("Invalid IDL input file: " + idlFile);
            }
    	 	}
    }
    
    private boolean isExistingFile(String fileName, List<String> includePaths)
    {
        // File without name does not exist
        if(fileName == null)
        {
            return false;
        }
        
        // OK, if the given file exists
        File file = new File(fileName);
        if(file.exists())
        {
            return true;
        }
        
        // OK, if the assembly file can be found in any given
        // include path
        boolean hasFound = false;
        	for(String includePath : includePaths)
        	{
        		File path = new File(includePath, fileName);
            if(path.exists()) 
            {
                hasFound = true;
            }
        }    
        return hasFound; 
    }
    
    
    
    // Housekeeping methods ---------------------------------------------------
    
	public String toString()
	{
		StringBuilder buffer = new StringBuilder();

		for(String generatorId : getGeneratorIds())
		{
			buffer.append("Generator IDs: ");
			buffer.append(generatorId).append("\n");
		}
		
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
