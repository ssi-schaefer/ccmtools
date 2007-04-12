package Components;

public class IllegalState extends Components.UserException
{
    private static final long serialVersionUID = -6931824844072445652L;

    private static final String DEFAULT_MESSAGE = "illegal state";

    public IllegalState()
    {
        super(DEFAULT_MESSAGE);
    }

    public IllegalState( String _reason )
    {
        super(_reason);
    }
}
