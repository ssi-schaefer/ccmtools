package Components;

public class RemoveFailure
	extends Components.UserException
{
	private static final long serialVersionUID = 1044618169291791571L;
	private static final String REPOSITORY_ID = "IDL:Components/RemoveFailure:1.0";
	
	public RemoveFailure() 
    {
		super(REPOSITORY_ID); 
    }
	
    public RemoveFailure(String reason) 
    {
    	    super(REPOSITORY_ID + " " + reason);
    }
}
