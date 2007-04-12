package Components;

public class ExceededConnectionLimit extends Components.UserException
{
    private static final long serialVersionUID = -5714895177604126672L;

    private static final String DEFAULT_MESSAGE = "exceeded connection limit";

    public ExceededConnectionLimit()
    {
        super(DEFAULT_MESSAGE);
    }

    public ExceededConnectionLimit( String _reason )
    {
        super(_reason);
    }
}
