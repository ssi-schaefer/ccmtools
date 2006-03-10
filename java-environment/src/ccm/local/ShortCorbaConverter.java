package ccm.local;

public class ShortCorbaConverter
{
	/**
	 * Convert from Java local to CORBA types 
	 */
	
	public static org.omg.CORBA.ShortHolder convert(ccm.local.ShortHolder in)
		throws CorbaConverterException
	{
		org.omg.CORBA.ShortHolder out = new org.omg.CORBA.ShortHolder(in.getValue());
		// TODO: performe some validations 
		return out;
	}
	
	
	/**
	 * Convert from CORBA to local Java types
	 */
	
	public static ccm.local.ShortHolder convert(org.omg.CORBA.ShortHolder in)
		throws CorbaConverterException	
	{
		ccm.local.ShortHolder out = new ccm.local.ShortHolder(in.value);
		// TODO: performe some validations 
		return out;
	}
}
