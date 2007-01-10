package Components;

public class CCMException
	extends Components.UserException
{
	private static final long serialVersionUID = -9007924014999270105L;
    private static final String REPOSITORY_ID = "IDL:Components/CCMException:1.0";
    
    private CCMExceptionReason reason;

    public CCMException() 
    {
        super(REPOSITORY_ID);
        setReason(null);
    }

    public CCMException(CCMExceptionReason r) 
    {
        super(REPOSITORY_ID);
        setReason(r);
    }
        
    public CCMException(String reason, CCMExceptionReason r)    
    {
        super(REPOSITORY_ID + " " + reason);
        setReason(r);       
    }

    public CCMExceptionReason getReason()
    {
        return this.reason;
    }     

    public void setReason(CCMExceptionReason value)
    {
        this.reason = value;
    }
}
