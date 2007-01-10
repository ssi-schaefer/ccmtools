package Components;

public class ExceededConnectionLimit
	extends Components.UserException
{
	private static final long serialVersionUID = -5714895177604126672L;
	private static final String REPOSITORY_ID = "IDL:Components/ExceededConnectionLimit:1.0";
	
	public ExceededConnectionLimit() 
    {
		super(REPOSITORY_ID);
    }
	
    public ExceededConnectionLimit(String reason) 
    {
    	    super(REPOSITORY_ID + " " + reason);
    }
}
