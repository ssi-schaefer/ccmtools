package Components;

public class CookieRequired extends Components.UserException
{
    private static final long serialVersionUID = -1104666651001979980L;

    private static final String DEFAULT_MESSAGE = "cookie required";

    public CookieRequired()
    {
        super(DEFAULT_MESSAGE);
    }

    public CookieRequired( String _reason )
    {
        super(_reason);
    }
}
