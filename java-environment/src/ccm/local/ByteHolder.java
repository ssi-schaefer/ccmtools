package ccm.local;

import java.io.Serializable;


public class ByteHolder
	implements Serializable, Comparable<ByteHolder>
{
	private static final long serialVersionUID = -9062928526807553070L;

	public static final byte DEFAULT_VALUE = 0;
	
	private byte value;


	public ByteHolder()
	{
		setValue(DEFAULT_VALUE);
	}
	
	public ByteHolder(byte value)
	{
		setValue(value);
	}
	
	
	public byte getValue()
	{
		return value;
	}

	public void setValue(byte value)
	{
		this.value = value;
	}	
	
	
    /**
	 * Returns a hash code for this Byte.
	 */
	public int hashCode()
	{
		return (int) value;
	}

	
	/**
	 * Compares this object to the specified object. The result is true if and
	 * only if the argument is not null and is a ByteHolder object that contains
	 * the same byte value as this object.
	 */
    public boolean equals(Object obj)
	{
		if (obj instanceof ByteHolder)
		{
			return value == ((ByteHolder) obj).getValue();
		}
		return false;
	}
    
    
    /**
	 * Compares two ByteHolder objects numerically.
	 */
    public int compareTo(ByteHolder anotherByte)
	{
		return this.value - anotherByte.value;
	}
}
