package ccm.local;

public class IntegerCorbaConverter
{
	/**
	 * Convert from Java local to CORBA types 
	 */
	
	public static org.omg.CORBA.IntHolder convert(ccm.local.IntegerHolder in)
		throws CorbaConverterException
	{
		org.omg.CORBA.IntHolder out = new org.omg.CORBA.IntHolder(in.getValue());
		// TODO: performe some validations 
		return out;
	}
	
	
	/**
	 * Convert from CORBA to local Java types
	 */
	
	public static ccm.local.IntegerHolder convert(org.omg.CORBA.IntHolder in)
		throws CorbaConverterException	
	{
		ccm.local.IntegerHolder out = new ccm.local.IntegerHolder(in.value);
		// TODO: performe some validations 
		return out;
	}
}
