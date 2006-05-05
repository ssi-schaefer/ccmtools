package ccm.local.Components;

/***
 * Cookie values are created by multiplex receptacles, and are used to
 * correlate a connect operation with a disconnect operation on multiplex
 * receptacles.
 * CCM Specification 1-18
 ***/
public class Cookie
{
	private static long nextId = 0L;
	private final byte[] cookieValue;
	
	public Cookie()
	{
		this(++nextId);
	}
	
	public Cookie(long id)
	{
		cookieValue = new byte[4];
		cookieValue[0] = (byte)(id & 0xff);
		cookieValue[1] = (byte)(id & 0xff00);
		cookieValue[2] = (byte)(id & 0xff0000);
		cookieValue[3] = (byte)(id & 0xff000000);
	}
	
	public byte[] getCookieValue()
	{
		return cookieValue;
	}
}
