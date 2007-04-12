package ccmtools.local;

import Components.CCMExceptionReason;

public class CorbaConverterException
	extends Components.CCMException
{
	private static final long serialVersionUID = -2010901086161763339L;
	
	public CorbaConverterException() 
    {
        super(CCMExceptionReason.SYSTEM_ERROR);
    }
	
	public CorbaConverterException(String _reason)
	{
		super(_reason, CCMExceptionReason.SYSTEM_ERROR);
	}
}
