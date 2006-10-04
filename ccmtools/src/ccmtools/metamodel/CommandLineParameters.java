package ccmtools.metamodel;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import ccmtools.CcmtoolsException;

public class CommandLineParameters
{
	/** */
	private boolean noExit;
    
    /** List of generator IDs */
    private List<String> actionIds = new ArrayList<String>();
    
    /** List of possible include paths */    
    private List<String> includePaths = new ArrayList<String>();
    
    /** List of IDL input files */
    private List<String> idlFiles = new ArrayList<String>();
    
    
    // Parameter getter and setter methods ------------------------------------
    
    public List<String> getActionIds()
    {
        return actionIds;
    }
    
	public List<String> getIncludePaths()
	{
		return includePaths;
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
	
	
	// Parameter validation methods -------------------------------------------
	
	public void validate()
		throws CcmtoolsException
	{
		checkIdlFiles();
        checkActions();
	}

    
    private void checkActions()
        throws CcmtoolsException
    {
        if(getActionIds().size() == 0)
        {
            throw new CcmtoolsException("No action specified!");
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
		
		buffer.append("No Exit: ").append(isNoExit()).append("\n");
		
        for(Iterator i=includePaths.iterator(); i.hasNext();) {
            buffer.append("Include Path: ");
            buffer.append((String)i.next()).append("\n");
        }
        
		for(Iterator i = getIdlFiles().iterator(); i.hasNext(); )
		{
			buffer.append("IDL input files: ");
			buffer.append((String)i.next()).append("\n");
		}
		
		return buffer.toString();
	}
}
