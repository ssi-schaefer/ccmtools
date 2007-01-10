package Components;


/***
 * Cookie values are created by multiplex receptacles, and are used to
 * correlate a connect operation with a disconnect operation on multiplex
 * receptacles.
 * CCM Specification 1-18
 ***/
public interface Cookie
{
	byte[] getCookieValue();
}
