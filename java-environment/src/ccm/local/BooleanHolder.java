package ccm.local;

import java.io.Serializable;


public class BooleanHolder
	implements Serializable, Comparable<BooleanHolder>
{
	private static final long serialVersionUID = -7781424517307057071L;

	public static final boolean DEFAULT_VALUE = false;
	
	private boolean value;

	
	public BooleanHolder()
	{
		setValue(DEFAULT_VALUE);
	}
	
	public BooleanHolder(boolean value)
	{
		setValue(value);
	}
	
	
	public boolean getValue()
	{
		return value;
	}

	public void setValue(boolean value)
	{
		this.value = value;
	}
	

	/**
     * Returns a hash code for this BooleanHolder object.
     */
    public int hashCode()
	{
		return value ? 1231 : 1237;
	}
    
    
	/**
     * Returns true if and only if the argument is not 
     * null and is a BooleanHolder object that 
     * represents the same boolean value as this object. 
     */
    public boolean equals(Object obj)
	{
		if (obj instanceof BooleanHolder)
		{
			return value == ((BooleanHolder) obj).getValue();
		}
		return false;
	}
    
    
	/**
	 * Compares this BooleanHolder instance with another.
	 */
    public int compareTo(BooleanHolder b) {
        return (b.value == value ? 0 : (value ? 1 : -1));
    }
}
