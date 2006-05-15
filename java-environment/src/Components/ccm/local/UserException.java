package Components.ccm.local;

public class UserException
	extends java.lang.Exception
{
	private static final long serialVersionUID = -1127609876865983434L;
	private static final String REPOSITORY_ID = "IDL:Components/UserException:1.0";
	
	public UserException() 
    {
		super(REPOSITORY_ID);
    }
    
    public UserException(String reason) 
    {
	    super(reason);
    }
}
