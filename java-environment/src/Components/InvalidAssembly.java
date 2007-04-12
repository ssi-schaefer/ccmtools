package Components;

public class InvalidAssembly extends Components.UserException
{
    private static final long serialVersionUID = 4478890731019313469L;

    private static final String DEFAULT_MESSAGE = "invalid assembly";

    public InvalidAssembly()
    {
        super(DEFAULT_MESSAGE);
    }

    public InvalidAssembly( String _reason )
    {
        super(_reason);
    }
}
