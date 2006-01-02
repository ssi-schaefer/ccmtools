package ccm.local.Components;

public class Exception
	extends java.lang.Exception
{
	private static final long serialVersionUID = -1127609876865983434L;

	public Exception() 
    {
        super("ccm.local.Components.Exception");
    }
    
    public Exception(String message) 
    {
        super(message);
    }
}
