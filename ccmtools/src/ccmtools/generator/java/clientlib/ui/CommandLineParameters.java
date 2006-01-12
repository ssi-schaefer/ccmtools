package ccmtools.generator.java.clientlib.ui;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import ccmtools.CcmtoolsException;

public class CommandLineParameters
{
	/** List of generator IDs */
	private List generatorIds = new ArrayList();
	
	/** */
	private boolean noExit;
	
    /** List of possible include paths */    
    private List includePaths = new ArrayList();

    /** Path to the output directory */
    private String outDir;
    
    /** List of IDL input files */
    private List idlFiles = new ArrayList();
    
    
    // Parameter getter and setter methods ------------------------------------
    
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

	
	public boolean isNoExit()
	{
		return noExit;
	}

	public void setNoExit(boolean noExit)
	{
		this.noExit = noExit;
	}

	
	public List getIdlFiles()
	{
		return idlFiles;
	}


	public List getGeneratorIds()
	{
		return generatorIds;
	}
	
	
	// Parameter validation methods -------------------------------------------
	
	public void validate()
		throws CcmtoolsException
	{
		checkIncludePaths();
		checkOutputPath();
		checkIdlFiles();
	}
	
	 /**
     * Check if the given include paths exists.
     * 
     * @throws CcmtoolsException
     */
    private void checkIncludePaths() 
        throws CcmtoolsException
    {
        // OK, if any given include directory exists
        for(Iterator i = includePaths.iterator(); i.hasNext();) {
            File path = new File((String) i.next());
            if(!path.exists()) {
                throw new CcmtoolsException("Invalid include path " + path);
            }
        }
    }
    
    /**
     * Check if the given output paths exists.
     * 
     * @throws CcmtoolsException
     */
    private void checkOutputPath() 
        throws CcmtoolsException
    {
        // OK, if any given output directory exists
        File path = new File(outDir);
        if(!path.exists()) {
            throw new CcmtoolsException("Invalid output path " + path);
        }
    }
	    
    /**
     * Check if the given IDL file exists.
     * @throws CcmtoolsException
     */
    private void checkIdlFiles() 
        throws CcmtoolsException
    {
    	 for(Iterator i = getIdlFiles().iterator(); i.hasNext();) {
             String idlFile = (String) i.next();
             if(!isExistingFile(idlFile, getIncludePaths())) {
                 throw new CcmtoolsException("Invalid IDL input file " + idlFile);
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
        for(Iterator i = includePaths.iterator(); i.hasNext();) {
            File path = new File((String) i.next(), fileName);
            if(path.exists()) {
                hasFound = true;
            }
        }    
        return hasFound; 
    }
    
    
    
    // Housekeeping methods ---------------------------------------------------
    
	public String toString()
	{
		StringBuffer buffer = new StringBuffer();

		for(Iterator i = getGeneratorIds().iterator(); i.hasNext(); )
		{
			buffer.append("Generator IDs: ");
			buffer.append((String)i.next()).append("\n");
		}
		
		buffer.append("No Exit: ").append(isNoExit()).append("\n");
		
        for(Iterator i=includePaths.iterator(); i.hasNext();) {
            buffer.append("Include Path: ");
            buffer.append((String)i.next()).append("\n");
        }

        if(getOutDir() != null)
        {
        	buffer.append("Output directory: ").append(getOutDir()).append("\n");
        }
        
		for(Iterator i = getIdlFiles().iterator(); i.hasNext(); )
		{
			buffer.append("IDL input files: ");
			buffer.append((String)i.next()).append("\n");
		}
		
		return buffer.toString();
	}

}
