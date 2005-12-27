package ccmtools.Deployment.Metamodel.impl;


// Typesafe enum pattern (Effective Java Item 21)
public class CCMComponentPortKind
    extends ModelElementImpl
{
    private final int portKind;

    private final static String[] Labels = {
        "",
        "Facet",
        "SimplexReceptacle",
        "MultiplexReceptacle",
        "EventEmitter",
        "EventPublisher",
        "EventConsumer"        
    };
    
    public static final CCMComponentPortKind Facet               = new CCMComponentPortKind(1);
    public static final CCMComponentPortKind SimplexReceptacle   = new CCMComponentPortKind(2);    
    public static final CCMComponentPortKind MultiplexReceptacle = new CCMComponentPortKind(3);
    public static final CCMComponentPortKind EventEmitter        = new CCMComponentPortKind(4);
    public static final CCMComponentPortKind EventPublisher      = new CCMComponentPortKind(5);
    public static final CCMComponentPortKind EventConsumer       = new CCMComponentPortKind(6);
    
    
    private CCMComponentPortKind(int pk)
    {
        portKind = pk;
    }
    
    public String toString()
    {
        return Labels[portKind];
    }

    public final static String[] getLabels()
    {
        return Labels;
    }
}
