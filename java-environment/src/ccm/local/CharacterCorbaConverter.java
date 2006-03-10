package ccm.local;

public class CharacterCorbaConverter
{
	/**
	 * Convert from Java local to CORBA types 
	 */
	
	public static org.omg.CORBA.CharHolder convert(ccm.local.CharacterHolder in)
		throws CorbaConverterException
	{
		org.omg.CORBA.CharHolder out = new org.omg.CORBA.CharHolder(in.getValue());
		// TODO: performe some validations 
		return out;
	}
	
	
	/**
	 * Convert from CORBA to local Java types
	 */
	
	public static ccm.local.CharacterHolder convert(org.omg.CORBA.CharHolder in)
		throws CorbaConverterException	
	{
		ccm.local.CharacterHolder out = new ccm.local.CharacterHolder(in.value);
		// TODO: performe some validations 
		return out;
	}
}
