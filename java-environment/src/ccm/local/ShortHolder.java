package ccm.local;

import java.io.Serializable;


public class ShortHolder
	implements Serializable, Comparable<ShortHolder>
{
	private static final long serialVersionUID = -1836037353450374053L;

	public static final short DEFAULT_VALUE = 0;
	
	private short value;

	
	public ShortHolder()
	{
		setValue(DEFAULT_VALUE);
	}
	
	public ShortHolder(short value)
	{
		setValue(value);
	}
	
	
	public short getValue()
	{
		return value;
	}

	public void setValue(short value)
	{
		this.value = value;
	}	
	
	
    public int hashCode()
	{
		return (int) value;
	}

    
    public boolean equals(Object obj)
	{
		if (obj instanceof ShortHolder)
		{
			return value == ((ShortHolder) obj).getValue();
		}
		return false;
	}
    
	
    public int compareTo(ShortHolder anotherShort)
	{
		return this.value - anotherShort.value;
	}
}
