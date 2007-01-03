package ccmtools.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;


public class ConfigurationLocator
{
    protected final String CCMTOOLS_PROPERTY_FILE_NAME = "ccmtools.properties";
    protected Properties properties;
    protected String propertyPath;
    protected static ConfigurationLocator instance = null;
    
    public static ConfigurationLocator getInstance() 
    {
        if(instance == null) 
        {
            try 
            {
                instance = new ConfigurationLocator();
            }
            catch(IOException e) 
            {
                // TODO: Set default values
            }
        }
        return instance;
    }
    
    
    protected ConfigurationLocator() 
        throws IOException
    {
        properties = new Properties();

        if(System.getProperty("ccmtools.home") != null)
        {
            propertyPath = System.getProperty("ccmtools.home") + File.separator + "etc" + File.separator;
            load();
        }
    }

    
    /*
     * Generic access methods
     */
    
    public boolean isDefined(String key)
    {
        return properties.containsKey(key);        
    }
    
    /**
     * Read the property value for the given key.
     * 
     * @param key Property key.
     * @return Property value or an empty string if there is no 
     *         value defined in the property file.
     */
    public String get(String key)
    {
        String value = properties.getProperty(key);
        if(value == null) 
        {
            value = "";
        }
        return value;
    }

    public void set(String key, String value)
    {
        properties.setProperty(key, value);
    }

    public Set getKeySet()
    {
    	    return properties.keySet();
    }
    
    public Map<String, String> getPropertyMap()
    {
    	    Map<String, String> map = new HashMap<String, String>();    	
    	    Set keySet = getKeySet();
    	    for(Iterator i = keySet.iterator(); i.hasNext(); )
    	    {
    	        String key = (String)i.next();
    	        map.put(key, get(key));
    	    }
    	    return map;
    }
    
    protected void load() 
    	    throws IOException
    {
        File file = new File(propertyPath, CCMTOOLS_PROPERTY_FILE_NAME);
        FileInputStream in = new FileInputStream(file);
        properties.load(in);
        in.close();
    }
    
    protected void save() 
    	    throws IOException
    {
        File file = new File(propertyPath, CCMTOOLS_PROPERTY_FILE_NAME);
        FileOutputStream out = new FileOutputStream(file);
        properties.store(out, "");
        out.close();
    }
    
    
    /*
     * Typed access methods
     */

    private List<String> idlNamespaceExtension = new ArrayList<String>();
    
    public List<String> getIdlNamespaceExtension()
    {
        return idlNamespaceExtension;
    }    
    public void setIdlNamespaceExtension(List<String> idlNamespaceExtension)
    {
        this.idlNamespaceExtension = idlNamespaceExtension;
    }
    
    public List<String> getIdl2NamespaceExtension()
    {
        List<String> namespaceList = new ArrayList<String>();        
        // TODO: Read these informations from a property file
        namespaceList.add("ccmtools");
        namespaceList.add("corba");
        return namespaceList;
    }
    
    public List<String> getCppRemoteNamespaceExtension()
    {
        List<String> namespaceList = new ArrayList<String>();        
        // TODO: Read these informations from a property file
        namespaceList.add("ccmtools");
        namespaceList.add("remote");
        return namespaceList;
    }

    public List<String> getCppLocalNamespaceExtension()
    {
        List<String> namespaceList = new ArrayList<String>();        
        // TODO: Read these informations from a property file
        namespaceList.add("ccmtools");
        namespaceList.add("local");
        return namespaceList;
    }

}
