package ccmtools.corba.Components;

import java.util.Arrays;

public class CookieImpl
    extends Cookie
{
	private static final long serialVersionUID = 8713831906953804415L;
	private static int nextId = 0;
	
	public CookieImpl()
	{
		this(++nextId);
	}
	
	public CookieImpl(int id)
    {
		CookieValue = new byte[4];
		CookieValue[0] = (byte)(id & 0xff);
		CookieValue[1] = (byte)(id & 0xff00);
		CookieValue[2] = (byte)(id & 0xff0000);
		CookieValue[3] = (byte)(id & 0xff000000);
    }
	
	public CookieImpl(byte[] value)
    {		
		CookieValue = new byte[value.length];
		for(int i=0; i < value.length; i++)
		{
			CookieValue[i] = value[i];
		}
	}

    public boolean equals(Object obj) 
    {
    		if (obj instanceof Cookie) 
    		{
    			byte[] value = ((Cookie)obj).CookieValue;
    			return Arrays.equals(CookieValue,value);
    		}
    		return false;
    }
    
    public int hashCode() 
    {
    		return Arrays.hashCode(CookieValue);
    }
    
    public String toString()
    {
    		return Arrays.toString(CookieValue);
    }
}