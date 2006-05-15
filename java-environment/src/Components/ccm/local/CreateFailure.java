package Components.ccm.local;

public class CreateFailure
	extends Components.ccm.local.UserException
{
	private static final long serialVersionUID = -4139103203013279104L;
	private static final String REPOSITORY_ID = "IDL:Components/CreateFailure:1.0";
	
	public CreateFailure() 
    {
		super(REPOSITORY_ID);
    }
	
	public CreateFailure(String reason) 
	{
		super(REPOSITORY_ID + " " + reason);
	}	
}
