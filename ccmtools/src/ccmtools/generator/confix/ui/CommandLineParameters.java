package ccmtools.generator.confix.ui;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import ccmtools.CcmtoolsException;

public class CommandLineParameters
{
	private static final String NL = "\n";
	
	/** List of generator IDs */
	private List generatorIds = new ArrayList();
		
    /** Path to the output directory */
    private String outDir;
	
    /** */
	private boolean noExit;
    
	private String packageName;
    
    private String packageVersion;
    
    
    
    // Parameter getter and setter methods ------------------------------------
    	
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

	
	public String getPackageName()
	{
		return packageName;
	}

	public void setPackageName(String packageName)
	{
		this.packageName = packageName;
	}

	
	public String getPackageVersion()
	{
		return packageVersion;
	}

	public void setPackageVersion(String packageVersion)
	{
		this.packageVersion = packageVersion;
	}

	
	public List getGeneratorIds()
	{
		return generatorIds;
	}
	
	
	// Parameter validation methods -------------------------------------------
	
	public void validate()
		throws CcmtoolsException
	{
		checkOutputPath();
		checkPackageName();
		checkPackageVersion();
	}
	
    /**
     * Check if the given output directory is valid.
     * 
     * @throws CcmtoolsException
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
     * Check if the given Confix package name is valid.
     * 
     * @throws CcmtoolsException
     */
    private void checkPackageName()
    	throws CcmtoolsException
    {
    	if(getPackageName() == null || getPackageName().length() == 0)
    	{
    		throw new CcmtoolsException("A valid Confix package name must be specified!"); 
    	}
    }
    
    /**
     * Check if the given Confix package version is valid.
     * 
     * @throws CcmtoolsException
     */
    private void checkPackageVersion()
    	throws CcmtoolsException
    {
    	if(getPackageVersion() == null || getPackageVersion().length() == 0)
    	{
    		throw new CcmtoolsException("A valid Confix package version must be specified!"); 
    	}
    	else
    	{
    		// TODO: check for ?.?.? pattern
    	}
    }
    
    
    // Housekeeping methods ---------------------------------------------------
    
	public String toString()
	{
		StringBuffer buffer = new StringBuffer();

		for(Iterator i = getGeneratorIds().iterator(); i.hasNext(); )
		{
			buffer.append("Generator IDs: ");
			buffer.append((String)i.next()).append(NL);
		}
		
        if(getOutDir() != null)
        {
        	buffer.append("Output directory: ").append(getOutDir()).append(NL);
        }
        
        buffer.append("Package name: ").append(packageName).append(NL);
        buffer.append("Package version: ").append(packageVersion).append(NL);
        
		return buffer.toString();
	}

}
