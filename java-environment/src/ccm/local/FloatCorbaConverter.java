package ccm.local;

public class FloatCorbaConverter
{
	/**
	 * Convert from Java local to CORBA types 
	 */
	
	public static org.omg.CORBA.FloatHolder convert(ccm.local.FloatHolder in)
		throws CorbaConverterException
	{
		org.omg.CORBA.FloatHolder out = new org.omg.CORBA.FloatHolder(in.getValue());
		// TODO: performe some validations 
		return out;
	}
	
	
	/**
	 * Convert from CORBA to local Java types
	 */
	
	public static ccm.local.FloatHolder convert(org.omg.CORBA.FloatHolder in)
		throws CorbaConverterException	
	{
		ccm.local.FloatHolder out = new ccm.local.FloatHolder(in.value);
		// TODO: performe some validations 
		return out;
	}
}
