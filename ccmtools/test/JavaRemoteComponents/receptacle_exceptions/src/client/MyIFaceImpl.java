package client;

import java.util.List;
import java.util.ArrayList;

import Components.CCMException;

import world.europe.austria.*;

public class MyIFaceImpl
        implements world.europe.austria.IFace
{
        public MyIFaceImpl()
        {
        }

        public int foo(String msg)
            throws CCMException,
                   ErrorException,
                   SuperError,
                   FatalError
        {
	    System.out.println("MyIFaceImpl.foo(" + msg + ")");

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
