package client;

import java.util.List;
import java.util.ArrayList;

import ccm.local.Components.CCMException;

import world.europe.austria.ccm.local.*;

public class MyConsoleImpl
        implements world.europe.austria.ccm.local.Console
{
        public MyConsoleImpl()
        {
        }

        public int print(String msg)
            throws CCMException,
                   ErrorException,
                   SuperError,
                   FatalError
        {
	    System.out.println("MyConsoleImpl.print(" + msg + ")");

	    if(msg.equals("ErrorException"))
            {
		List<ErrorInfo> errorInfoList = new ArrayList<ErrorInfo>();
		ErrorInfo errorInfo = new ErrorInfo(7, "A simple error!");
		errorInfoList.add(errorInfo);
		ErrorException error = new ErrorException(errorInfoList);
		throw error;
	    }

	    if(msg.equals("SuperError"))
		throw new SuperError();
	    
	    if(msg.equals("FatalError"))
		throw new FatalError();
	    
	    return msg.length();
        }
}
