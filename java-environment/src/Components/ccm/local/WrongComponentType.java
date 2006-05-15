package Components.ccm.local;

public class WrongComponentType
	extends Components.ccm.local.UserException
{
	private static final long serialVersionUID = 2208948298152201554L;
	private static final String REPOSITORY_ID = "IDL:Components/WrongComponentType:1.0";
	
	public WrongComponentType() 
    {
		super(REPOSITORY_ID); 
    }
	
    public WrongComponentType(String reason) 
    {
    	    super(REPOSITORY_ID + " " + reason);
    }
}
