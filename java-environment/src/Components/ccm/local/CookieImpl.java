package Components.ccm.local;

import java.util.Arrays;


public class CookieImpl
	implements Cookie
{
	private static long nextId = 0L;
	private final byte[] cookieValue;
	
	public CookieImpl()
	{
		this(++nextId);
	}
	
	public CookieImpl(long id)
	{
		cookieValue = new byte[4];
		cookieValue[0] = (byte)(id & 0xff);
		cookieValue[1] = (byte)(id & 0xff00);
		cookieValue[2] = (byte)(id & 0xff0000);
		cookieValue[3] = (byte)(id & 0xff000000);
	}
	
	public CookieImpl(byte[] value)
    {		
		cookieValue = new byte[value.length];
		for(int i=0; i < value.length; i++)
		{
			cookieValue[i] = value[i];
		}
	}
	
	public byte[] getCookieValue()
	{
		return cookieValue;
	}

    public boolean equals(Object obj) 
    {
    		if (obj instanceof CookieImpl) 
    		{
    			byte[] value = ((CookieImpl)obj).getCookieValue();
    			return Arrays.equals(getCookieValue(),value);
    		}
    		return false;
    }
    
    public int hashCode() 
    {
    		return Arrays.hashCode(getCookieValue());
    }
    
    public String toString()
    {
    		return Arrays.toString(getCookieValue());
    }
}
