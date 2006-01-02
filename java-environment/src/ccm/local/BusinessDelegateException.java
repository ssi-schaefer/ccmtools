package ccm.local;

public class BusinessDelegateException
	extends Exception
{
	private static final long serialVersionUID = 3027667597202955443L;

	public BusinessDelegateException() 
    {
        super("BusinessDelegateException");
    }

    
    public BusinessDelegateException(String message) 
    {
        super(message);
    }
}
