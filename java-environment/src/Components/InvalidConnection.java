package Components;

public class InvalidConnection extends Components.UserException
{
    private static final long serialVersionUID = -3095009263968067357L;

    private static final String DEFAULT_MESSAGE = "invalid connection";

    public InvalidConnection()
    {
        super(DEFAULT_MESSAGE);
    }

    public InvalidConnection( String _reason )
    {
        super(_reason);
    }
}
