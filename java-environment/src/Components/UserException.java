package Components;

public class UserException extends java.lang.Exception
{
    private static final long serialVersionUID = -1127609876865983434L;

    private static final String DEFAULT_MESSAGE = "ccmtools user exception";

    public UserException()
    {
        super(DEFAULT_MESSAGE);
    }

    public UserException( String message )
    {
        super(message);
    }

    public UserException( String message, Throwable cause )
    {
        super(message, cause);
    }

    public UserException( Throwable cause )
    {
        super(DEFAULT_MESSAGE, cause);
    }

    public String getRepoId()
    {
        String cls = this.getClass().getName();
        return "IDL:ccmtools/" + cls + ":1.0";
    }
}
