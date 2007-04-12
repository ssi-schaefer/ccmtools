package Components;

public class RemoveFailure extends Components.UserException
{
    private static final long serialVersionUID = 1044618169291791571L;

    private static final String DEFAULT_MESSAGE = "remove failure";

    public RemoveFailure()
    {
        super(DEFAULT_MESSAGE);
    }

    public RemoveFailure( String reason )
    {
        super(reason);
    }
}
