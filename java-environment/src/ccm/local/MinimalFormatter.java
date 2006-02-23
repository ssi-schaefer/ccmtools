package ccm.local;

import java.util.logging.Formatter;
import java.util.logging.LogRecord;

public class MinimalFormatter
	extends Formatter
{
    public synchronized String format(LogRecord record) 
    {
    	StringBuffer buffer = new StringBuffer();
    	buffer.append("[CCM:").append(record.getLevel()).append("] ");
    	buffer.append(record.getSourceClassName()).append(".");
    	buffer.append(record.getSourceMethodName()).append("()");
    	if(record.getMessage().length() != 0)
    	{
    		buffer.append(": ").append(record.getMessage());    		
    	}
    	buffer.append("\n");
    	return buffer.toString();
    }
}	
