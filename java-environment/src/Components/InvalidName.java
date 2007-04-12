package Components;

public class InvalidName extends Components.UserException
{
    private static final long serialVersionUID = 6897296555631420409L;

    private static final String DEFAULT_MESSAGE = "invalid name";

    public InvalidName()
    {
        super(DEFAULT_MESSAGE);
    }

    public InvalidName( String reason )
    {
        super(reason);
    }
}
