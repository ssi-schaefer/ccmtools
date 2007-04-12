package Components;

public class NoConnection extends Components.UserException
{
    private static final long serialVersionUID = 8687891271765409198L;

    private static final String DEFAULT_MESSAGE = "no connection";

    public NoConnection()
    {
        super(DEFAULT_MESSAGE);
    }

    public NoConnection( String reason )
    {
        super(reason);
    }
}
