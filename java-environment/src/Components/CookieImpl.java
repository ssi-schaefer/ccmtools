package Components;

public class CookieImpl
    extends Cookie
{
	private static final long serialVersionUID = 8713831906953804415L;
	private static long nextId = 0;
		
	public CookieImpl()
	{
		this(++nextId);
	}
	
	public CookieImpl(long id)
    {
		CookieValue = new byte[4];
		CookieValue[0] = (byte)(id & 0xff);
		CookieValue[1] = (byte)(id & 0xff00);
		CookieValue[2] = (byte)(id & 0xff0000);
		CookieValue[3] = (byte)(id & 0xff000000);
    }
}