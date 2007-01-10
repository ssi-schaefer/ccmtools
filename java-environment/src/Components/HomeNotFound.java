package Components;

public class HomeNotFound
	extends Components.UserException
{
	private static final long serialVersionUID = -8114743147626972429L;
	private static final String REPOSITORY_ID = "IDL:Components/HomeNotFound:1.0";
	
	public HomeNotFound() 
    {
		super(REPOSITORY_ID);
    }
	
    public HomeNotFound(String reason) 
    {
    	    super(REPOSITORY_ID + " " + reason);
    }
}
