package ccmtools.local;

import Components.CCMExceptionReason;

public class CorbaConverterException
	extends Components.CCMException
{
	private static final long serialVersionUID = -2010901086161763339L;
	private static final String REPOSITORY_ID = "IDL:Components/CorbaConverterException:1.0";
	
	public CorbaConverterException() 
    {
        super(REPOSITORY_ID, CCMExceptionReason.SYSTEM_ERROR);
    }
	
	public CorbaConverterException(String reason)
	{
		super(REPOSITORY_ID + "  " + reason, CCMExceptionReason.SYSTEM_ERROR);
	}
}
