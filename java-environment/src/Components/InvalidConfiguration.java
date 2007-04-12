package Components;

public class InvalidConfiguration extends Components.UserException
{
    private static final long serialVersionUID = -294782326819622429L;

    private static final String DEFAULT_MESSAGE = "invalid configuration";

    public InvalidConfiguration()
    {
        super(DEFAULT_MESSAGE);
    }

    public InvalidConfiguration( String reason )
    {
        super(reason);
    }
}
