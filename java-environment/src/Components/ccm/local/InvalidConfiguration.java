package Components.ccm.local;

public class InvalidConfiguration
	extends Components.ccm.local.UserException
{
	private static final long serialVersionUID = -294782326819622429L;
	private static final String REPOSITORY_ID = "IDL:Components/InvalidConfiguration:1.0";
	
	public InvalidConfiguration() 
    {
		super(REPOSITORY_ID);
    }
	
    public InvalidConfiguration(String reason) 
    {
    	    super(REPOSITORY_ID + " " + reason);
    }
}
