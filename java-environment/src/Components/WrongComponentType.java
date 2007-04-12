package Components;

public class WrongComponentType extends Components.UserException
{
    private static final long serialVersionUID = 2208948298152201554L;

    private static final String DEFAULT_MESSAGE = "wrong component type";

    public WrongComponentType()
    {
        super(DEFAULT_MESSAGE);
    }

    public WrongComponentType( String reason )
    {
        super(reason);
    }
}
