package ccm.local;

public class CorbaConverterException
	extends ccm.local.Components.CCMException
{
	private static final long serialVersionUID = -2010901086161763339L;

	public CorbaConverterException() 
    {
        super("ccm.local.CorbaConverterException");
    }
	
	public CorbaConverterException(String msg)
	{
		super(msg);
	}
}
