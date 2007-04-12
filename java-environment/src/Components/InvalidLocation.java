package Components;

public class InvalidLocation extends Components.UserException
{
    private static final long serialVersionUID = 292325942395365465L;

    private static final String DEFAULT_MESSAGE = "invalid location";

    public InvalidLocation()
    {
        super(DEFAULT_MESSAGE);
    }

    public InvalidLocation( String _reason )
    {
        super(_reason);
    }
}
