package ccm.local.Components;

public class CCMException
	extends ccm.local.Components.Exception
{
	private static final long serialVersionUID = -9007924014999270105L;

	public CCMException() 
    {
        super("ccm.local.Components.CCMException");
    }
	
    public CCMException(String message)
	{
		super(message);
	}
}
