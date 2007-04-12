package Components;

public class CreateFailure extends Components.UserException
{
    private static final long serialVersionUID = -4139103203013279104L;

    private static final String DEFAULT_MESSAGE = "create failure";

    public CreateFailure()
    {
        super(DEFAULT_MESSAGE);
    }

    public CreateFailure( String _reason )
    {
        super(_reason);
    }
}
