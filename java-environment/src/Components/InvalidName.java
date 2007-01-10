package Components;

public class InvalidName
	extends Components.UserException
{
	private static final long serialVersionUID = 6897296555631420409L;
	private static final String REPOSITORY_ID = "IDL:Components/InvalidName:1.0";
	
	public InvalidName() 
    {
		super(REPOSITORY_ID);
    }
	
    public InvalidName(String reason) 
    {
    	    super(REPOSITORY_ID + " " + reason);
    }
}
