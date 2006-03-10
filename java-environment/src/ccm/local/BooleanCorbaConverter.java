package ccm.local;

public class BooleanCorbaConverter
{
	/**
	 * Convert from Java local to CORBA types 
	 */
	
	public static org.omg.CORBA.BooleanHolder convert(ccm.local.BooleanHolder in)
		throws CorbaConverterException
	{
		org.omg.CORBA.BooleanHolder out = new org.omg.CORBA.BooleanHolder(in.getValue());
		// TODO: performe some validations 
		return out;
	}
	
	
	/**
	 * Convert from CORBA to local Java types
	 */
	
	public static ccm.local.BooleanHolder convert(org.omg.CORBA.BooleanHolder in)
		throws CorbaConverterException	
	{
		ccm.local.BooleanHolder out = new ccm.local.BooleanHolder(in.value);
		// TODO: performe some validations 
		return out;
	}
}
