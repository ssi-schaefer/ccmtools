package Components;

public class CookieRequired
	extends Components.UserException
{
	private static final long serialVersionUID = -1104666651001979980L;
	private static final String REPOSITORY_ID = "IDL:Components/CookieRequired:1.0";
	
	public CookieRequired() 
    {
		super(REPOSITORY_ID);
    }
	
	public CookieRequired(String reason) 
    {
		super(REPOSITORY_ID + " " + reason);
    }	
}
