package ccmtools.CodeGenerator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import ccmtools.utils.Timer;


public class TemplateLoader
{
    protected static TemplateLoader instance = null;
    protected Map templateMap;
    protected Map templateListMap;
    
    protected Timer timer;
    
    protected TemplateLoader()
    {
        templateMap = new HashMap();
        templateListMap = new HashMap();
        timer = new Timer();
    }
    
    public static TemplateLoader getInstance()
    {
        if(instance == null) {
            instance = new TemplateLoader();
        }
        return instance;    
    }

    /**
     * Handle templates using a cache meachanism.
     * Loaded templates will be stored in a templateMap, thus, a template
     * file will be loaded only once. 
     *  
     * @param file
     * @return The template stored in the given template file.
     */
    public String loadTemplate(File file)
    {
        timer.startClock();
        String fileName = file.toString();
        String template;
        if(templateMap.containsKey(fileName)) {
            template = (String)templateMap.get(fileName);
        }
        else {
            template = loadTemplateFile(file);
            templateMap.put(fileName,template);
        }
        timer.stopClock();
        return template;
    }
    
    protected String loadTemplateFile(File file)
    {
        StringBuffer buffer = new StringBuffer();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line = null;
            while ((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
            }
        }
        catch(FileNotFoundException e) {
            // TODO Logging
        }
        catch(IOException e) {
            // TODO Logging
        }
        return buffer.toString();
    }
    
    /**
     * Handle template lists using a cache mechanism.
     * 
     * @param dir
     * @return
     */
    public String[] loadTemplateList(File dir)
    {
        timer.startClock();
        String directoryName = dir.toString();
        String[] templateList;
        if(templateListMap.containsKey(directoryName)) {
            templateList = (String[])templateListMap.get(directoryName);
        }
        else {
            templateList = dir.list();
            templateListMap.put(directoryName, templateList);
        }        
        timer.stopClock();
        return templateList;
    }
    
    
    public long getTimerMillis()
    {
        return timer.getTimeMillis();
    }
}
