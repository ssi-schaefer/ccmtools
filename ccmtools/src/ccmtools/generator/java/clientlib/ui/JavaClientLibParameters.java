package ccmtools.generator.java.clientlib.ui;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class JavaClientLibParameters
	implements CommandLineParameters
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
