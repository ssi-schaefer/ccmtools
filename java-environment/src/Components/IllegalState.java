package Components;

public class IllegalState
	extends Components.UserException
{
	private static final long serialVersionUID = -6931824844072445652L;
	private static final String REPOSITORY_ID = "IDL:Components/IllegalState:1.0";
	
	public IllegalState() 
    {
		super(REPOSITORY_ID);
    }
	
    public IllegalState(String reason) 
    {
    	    super(REPOSITORY_ID + " " + reason);
    }
}
