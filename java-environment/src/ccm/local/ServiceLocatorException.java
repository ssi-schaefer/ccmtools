package ccm.local;


public class ServiceLocatorException
	extends Exception
{
	private static final long serialVersionUID = 1292202915476176363L;


	public ServiceLocatorException() 
    {
        super("ServiceLocatorException");
    }

    
    public ServiceLocatorException(String message) 
    {
        super(message);
    }
}
