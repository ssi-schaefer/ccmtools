package client;

import java.util.List;
import java.util.ArrayList;

import ccm.local.Components.CCMException;

import world.europe.austria.ccm.local.*;

public class myConsoleImpl
	implements world.europe.austria.ccm.local.Console
{
	public myConsoleImpl()
	{
	}

	public int print(String msg) 
	    throws CCMException, 
		   SimpleError,
		   SuperError,
		   FatalError
	{
		System.out.println("myConsoleImpl.print(" + msg + ")");

		if(msg.equals("SimpleError")) 
		{

		    List<ErrorInfo> errorInfoList = new ArrayList<ErrorInfo>();
		    ErrorInfo errorInfo = new ErrorInfo(7, "A simple error!");
		    errorInfoList.add(errorInfo);
		    SimpleError error = new SimpleError(errorInfoList);
		    throw error;
		}
		
		if(msg.equals("SuperError"))
		    throw new SuperError();
		
		if(msg.equals("FatalError"))
		    throw new FatalError();

		return msg.length();
	}
}
