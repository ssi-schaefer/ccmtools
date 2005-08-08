package ccmtools.UI;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;


public class CcmtoolsProperties
{
    protected final String CCMTOOLS_PROPERTY_FILE_NAME = "ccmtools.properties";
    protected Properties ccmtoolsProperties_;
    protected String propertyPath_;
    protected static CcmtoolsProperties instance_ = null;
    
    public static CcmtoolsProperties Instance() 
    {
        if(instance_ == null) {
            try {
                instance_ = new CcmtoolsProperties();
            }
            catch(IOException e) {
                // TODO: Set default values
            }
        }
        return instance_;
    }
    
    public CcmtoolsProperties() 
    	throws IOException
    {
        propertyPath_ = System.getProperty("ccmtools.home") + File.separator 
        	+ "etc" + File.separator;
        load();
    }
        
    public String get(String key)
    {
        return ccmtoolsProperties_.getProperty(key);
    }

    public void set(String key, String value)
    {
        ccmtoolsProperties_.setProperty(key, value);
    }
    
    public void load() 
    	throws IOException
    {
        File file = new File(propertyPath_, CCMTOOLS_PROPERTY_FILE_NAME);
        System.out.println("> load properties from " + file);
        ccmtoolsProperties_ = new Properties();
        FileInputStream in = new FileInputStream(file);
        ccmtoolsProperties_.load(in);
        in.close();
    }
    
    public void save() 
    	throws IOException
    {
        File file = new File(propertyPath_, CCMTOOLS_PROPERTY_FILE_NAME);
        System.out.println("> store properties to " + file);
        FileOutputStream out = new FileOutputStream(file);
        ccmtoolsProperties_.store(out, "");
        out.close();
    }
    
}
