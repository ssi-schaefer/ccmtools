package client;

import ccm.local.Components.CCMException;

public class myConsoleImpl
	implements world.europe.austria.ccm.local.Console
{
	public myConsoleImpl()
	{
	}

	public int print(String msg) 
	    throws CCMException, 
		   world.europe.austria.SimpleError,
		   world.europe.austria.SuperError,
		   world.europe.austria.FatalError
	{
		System.out.println("myConsoleImpl.print(" + msg + ")");

		if(msg.equals("SimpleError")) 
		{
		    world.europe.austria.SimpleError error = new world.europe.austria.SimpleError();
		    world.europe.austria.ErrorInfo[] errorInfoList = new world.europe.austria.ErrorInfo[1];
		    world.europe.austria.ErrorInfo errorInfo = 
			new world.europe.austria.ErrorInfo(7, "A simple error!");
		    errorInfoList[0] = errorInfo;
		    error.info = errorInfoList;
		    throw error;
		}
		
		if(msg.equals("SuperError"))
		    throw new world.europe.austria.SuperError();
		
		if(msg.equals("FatalError"))
		    throw new world.europe.austria.FatalError();

		return msg.length();
	}
}
