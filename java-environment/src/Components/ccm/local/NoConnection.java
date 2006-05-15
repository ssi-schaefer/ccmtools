package Components.ccm.local;

public class NoConnection
	extends Components.ccm.local.UserException
{
	private static final long serialVersionUID = 8687891271765409198L;
	private static final String REPOSITORY_ID = "IDL:Components/NoConnection:1.0";
	
	public NoConnection() 
    {
		super(REPOSITORY_ID);
    }
	
    public NoConnection(String reason) 
    {
    	    super(REPOSITORY_ID + " " + reason);
    }
}
