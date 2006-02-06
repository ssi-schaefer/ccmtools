package ccmtools.generator.java.metamodel;

public class PassingDirection
{
	// Typesafe enum pattern (Effective Java Item 21)
    private final int direction;

    private PassingDirection(int direction)
    {
    	this.direction = direction;
    }

    // <<enumeration>>
    public static final PassingDirection IN    = new PassingDirection(1);
    public static final PassingDirection OUT   = new PassingDirection(2);
    public static final PassingDirection INOUT = new PassingDirection(3);
    public static final PassingDirection RESULT = new PassingDirection(3);
}
