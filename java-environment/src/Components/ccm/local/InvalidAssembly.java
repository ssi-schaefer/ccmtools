package Components.ccm.local;

public class InvalidAssembly
	extends Components.ccm.local.UserException
{
	private static final long serialVersionUID = 4478890731019313469L;
	private static final String REPOSITORY_ID = "IDL:Components/InvalidAssembly:1.0";
	
	public InvalidAssembly() 
    {
		super(REPOSITORY_ID);
    }
	
    public InvalidAssembly(String reason) 
    {
    	    super(REPOSITORY_ID + " " + reason);
    }
}
