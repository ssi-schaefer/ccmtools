package Components.ccm.local;

public class InvalidLocation
	extends Components.ccm.local.UserException
{
	private static final long serialVersionUID = 292325942395365465L;
	private static final String REPOSITORY_ID = "IDL:Components/InvalidLocation:1.0";
	
	public InvalidLocation() 
    {
		super(REPOSITORY_ID);
    }
	
	public InvalidLocation(String reason) 
	{
		super(REPOSITORY_ID + " " + reason);
	}
}
