package Components.ccm.local;

public class InvalidConnection
	extends Components.ccm.local.UserException
{
	private static final long serialVersionUID = -3095009263968067357L;
	private static final String REPOSITORY_ID = "IDL:Components/InvalidConnection:1.0";
		
	public InvalidConnection() 
    {
		super(REPOSITORY_ID);
    }
	
	public InvalidConnection(String reason) 
	{
		super(REPOSITORY_ID + " " + reason);
	}
}
