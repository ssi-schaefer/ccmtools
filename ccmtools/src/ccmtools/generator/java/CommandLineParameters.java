package ccmtools.generator.java;

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
    
    /** List of assembly input files */
    private List<String> assemblyFiles = new ArrayList<String>();
    
    
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

    
    public List<String> getAssemblyFiles()
    {
        return assemblyFiles;
    }


	public List<String> getGeneratorIds()
	{
		return generatorIds;
	}
	
	
	// Parameter validation methods -------------------------------------------
	
	public void validate()
		throws CcmtoolsException
	{
		checkOutputPath();
		checkIdlFiles();
        checkAssemblyFiles();
	}
	
    
    /**
     * Check if the given output directory is valid.
     * 
     * @throws CcmtoolsException
     */
    private void checkOutputPath() 
        throws CcmtoolsException
    {
        if (outDir == null || outDir.length() == 0)
        {
            outDir = ".";
        }
    }
	    
    /**
     * Check if the given IDL file exists.
     * 
     * @throws CcmtoolsException
     */
    private void checkIdlFiles() throws CcmtoolsException
    {
        for (String idlFile : getIdlFiles())
        {
            if (!isExistingFile(idlFile, getIncludePaths()))
            {
                throw new CcmtoolsException("Invalid IDL input file " + idlFile);
            }
        }
    }

    /**
     * Check if the given assembly file exists.
     * 
     * @throws CcmtoolsException
     */
    private void checkAssemblyFiles() throws CcmtoolsException
    {
        for (String f : getAssemblyFiles())
        {
            if (f == null || !(new File(f)).exists())
                throw new CcmtoolsException("Invalid assembly input file " + f);
        }
    }
    
    private boolean isExistingFile(String fileName, List<String> includePaths)
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
        for (String includePath : includePaths)
        {
            File path = new File(includePath, fileName);
            if (path.exists())
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
			buffer.append("Generator IDs: ").append(generatorId).append("\n");
		}
		
		buffer.append("No Exit: ").append(isNoExit()).append("\n");
		
        for(String includePath : includePaths) 
        {            
            buffer.append("Include Path: ").append(includePath).append("\n");
        }

        if(getOutDir() != null)
        {
            buffer.append("Output directory: ").append(getOutDir()).append("\n");
        }
        
		for(String idlFile : getIdlFiles())
		{
			buffer.append("IDL input files: ").append(idlFile).append("\n");
		}
        
        for(String f : getAssemblyFiles())
        {
            buffer.append("assembly input files: ").append(f).append("\n");
        }
		
		return buffer.toString();
	}
}
