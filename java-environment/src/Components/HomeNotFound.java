package Components;

public class HomeNotFound extends Components.UserException
{
    private static final long serialVersionUID = -8114743147626972429L;

    private static final String DEFAULT_MESSAGE = "home not found";

    public HomeNotFound()
    {
        super(DEFAULT_MESSAGE);
    }

    public HomeNotFound( String _reason )
    {
        super(_reason);
    }
}
