package ccm.local;

public class ByteCorbaConverter
{
	/**
	 * Convert from Java local to CORBA types 
	 */
	
	public static org.omg.CORBA.ByteHolder convert(ccm.local.ByteHolder in)
		throws CorbaConverterException
	{
		org.omg.CORBA.ByteHolder out = new org.omg.CORBA.ByteHolder(in.getValue());
		// TODO: performe some validations 
		return out;
	}
	
	
	/**
	 * Convert from CORBA to local Java types
	 */
	
	public static ccm.local.ByteHolder convert(org.omg.CORBA.ByteHolder in)
		throws CorbaConverterException	
	{
		ccm.local.ByteHolder out = new ccm.local.ByteHolder(in.value);
		// TODO: performe some validations 
		return out;
	}
}
