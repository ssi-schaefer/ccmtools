package Components;

public class AlreadyConnected
	extends Components.UserException
{
	private static final long serialVersionUID = 6886751009749534622L;
	private static final String REPOSITORY_ID = "IDL:Components/AlreadyConnected:1.0";
	
	public AlreadyConnected() 
    {
		super(REPOSITORY_ID);
    }
	
    public AlreadyConnected(String reason) 
    {
    	    super(REPOSITORY_ID + " " + reason);
    }
}
