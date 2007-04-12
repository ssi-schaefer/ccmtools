package Components;

public class AlreadyConnected
	extends Components.UserException
{
	private static final long serialVersionUID = 6886751009749534622L;
	
	public AlreadyConnected() 
    {
		super();
    }
	
    public AlreadyConnected(String _reason) 
    {
    	    super(_reason);
    }
}
