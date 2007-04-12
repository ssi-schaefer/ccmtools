package ccmtools.ant;

import org.apache.tools.ant.types.Path;

public class IncludePath
{
    
    public String[] getPaths()
    {
        return path.list();
    }

    /** Handle path attribute */
    private Path path;
    public void setPath(Path path)
    {
        this.path = path;
    }
            
            
    public String toString()
    {
        return path.toString();
    }
}
