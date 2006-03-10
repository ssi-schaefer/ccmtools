package ccm.local;

public class StringCorbaConverter
{
	/**
	 * Convert from Java local to CORBA types 
	 */
	
	public static org.omg.CORBA.StringHolder convert(ccm.local.StringHolder in)
		throws CorbaConverterException
	{
		org.omg.CORBA.StringHolder out = new org.omg.CORBA.StringHolder(in.getValue());
		// TODO: performe some validations 
		return out;
	}
	
	
	/**
	 * Convert from CORBA to local Java types
	 */
	
	public static ccm.local.StringHolder convert(org.omg.CORBA.StringHolder in)
		throws CorbaConverterException	
	{
		ccm.local.StringHolder out = new ccm.local.StringHolder(in.value);
		// TODO: performe some validations 
		return out;
	}
}
