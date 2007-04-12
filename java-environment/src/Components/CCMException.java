package Components;

public class CCMException extends Components.UserException
{
    private static final long serialVersionUID = -9007924014999270105L;

    private static final String DEFAULT_MESSAGE = "CCM exception";

    private CCMExceptionReason reason;

    public CCMException()
    {
        super(DEFAULT_MESSAGE);
        setReason(null);
    }

    public CCMException( CCMExceptionReason r )
    {
        super(DEFAULT_MESSAGE);
        setReason(r);
    }

    public CCMException( String message, CCMExceptionReason r )
    {
        super(message);
        setReason(r);
    }

    public CCMException( String message, CCMExceptionReason r, Throwable cause )
    {
        super(message, cause);
        setReason(r);
    }

    public CCMException( CCMExceptionReason r, Throwable cause )
    {
        super(DEFAULT_MESSAGE, cause);
        setReason(r);
    }

    public CCMExceptionReason getReason()
    {
        return this.reason;
    }

    public void setReason( CCMExceptionReason value )
    {
        this.reason = value;
    }
}
