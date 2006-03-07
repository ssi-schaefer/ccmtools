package ccm.local;

import java.io.Serializable;


public class LongHolder
	implements Serializable, Comparable<LongHolder>
{
	private static final long serialVersionUID = -969516340611062L;

	public static final long DEFAULT_VALUE = 0L;
	
	private long value;

	
	public LongHolder()
	{
		setValue(DEFAULT_VALUE);
	}
	
	public LongHolder(long value)
	{
		setValue(value);
	}
	
	
	public long getValue()
	{
		return value;
	}

	public void setValue(long value)
	{
		this.value = value;
	}
	
	
    public int hashCode()
	{
		return (int) (value ^ (value >>> 32));
	}
    

    public boolean equals(Object obj)
	{
		if (obj instanceof LongHolder)
		{
			return value == ((LongHolder) obj).getValue();
		}
		return false;
	}
	
    
	public int compareTo(LongHolder anotherLong)
	{
		long thisVal = this.value;
		long anotherVal = anotherLong.value;
		return (thisVal < anotherVal ? -1 : (thisVal == anotherVal ? 0 : 1));
	}
}
