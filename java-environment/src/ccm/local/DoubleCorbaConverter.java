package ccm.local;

public class DoubleCorbaConverter
{
	/**
	 * Convert from Java local to CORBA types 
	 */
	
	public static org.omg.CORBA.DoubleHolder convert(ccm.local.DoubleHolder in)
		throws CorbaConverterException
	{
		org.omg.CORBA.DoubleHolder out = new org.omg.CORBA.DoubleHolder(in.getValue());
		// TODO: performe some validations 
		return out;
	}
	
	
	/**
	 * Convert from CORBA to local Java types
	 */
	
	public static ccm.local.DoubleHolder convert(org.omg.CORBA.DoubleHolder in)
		throws CorbaConverterException	
	{
		ccm.local.DoubleHolder out = new ccm.local.DoubleHolder(in.value);
		// TODO: performe some validations 
		return out;
	}
}
