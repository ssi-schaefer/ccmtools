package ccm.local;

public class LongCorbaConverter
{
	/**
	 * Convert from Java local to CORBA types 
	 */
	
	public static org.omg.CORBA.LongHolder convert(ccm.local.LongHolder in)
		throws CorbaConverterException
	{
		org.omg.CORBA.LongHolder out = new org.omg.CORBA.LongHolder(in.getValue());
		// TODO: performe some validations 
		return out;
	}
	
	
	/**
	 * Convert from CORBA to local Java types
	 */
	
	public static ccm.local.LongHolder convert(org.omg.CORBA.LongHolder in)
		throws CorbaConverterException	
	{
		ccm.local.LongHolder out = new ccm.local.LongHolder(in.value);
		// TODO: performe some validations 
		return out;
	}
}
