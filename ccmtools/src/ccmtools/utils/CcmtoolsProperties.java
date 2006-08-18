package ccmtools.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;


public class CcmtoolsProperties
{
    protected final String CCMTOOLS_PROPERTY_FILE_NAME = "ccmtools.properties";
    protected Properties properties;
    protected String propertyPath;
    protected static CcmtoolsProperties instance = null;
    
    public static CcmtoolsProperties Instance() 
    {
        if(instance == null) 
        {
            try 
            {
                instance = new CcmtoolsProperties();
            }
            catch(IOException e) 
            {
                // TODO: Set default values
            }
        }
        return instance;
    }
    
    
    protected CcmtoolsProperties() 
        throws IOException
    {
        propertyPath = System.getProperty("ccmtools.home") + File.separator + "etc" + File.separator;
        load();
    }

    
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
//        System.out.println("> load properties from " + file);
        properties = new Properties();
        FileInputStream in = new FileInputStream(file);
        properties.load(in);
        in.close();
    }
    
    protected void save() 
    	    throws IOException
    {
        File file = new File(propertyPath, CCMTOOLS_PROPERTY_FILE_NAME);
//        System.out.println("> store properties to " + file);
        FileOutputStream out = new FileOutputStream(file);
        properties.store(out, "");
        out.close();
    }
}
