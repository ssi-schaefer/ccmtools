package ccm.local;

import java.io.Serializable;


public class IntegerHolder
	implements Serializable, Comparable<IntegerHolder>
{
	private static final long serialVersionUID = 2333283095934946312L;

	public static final int DEFAULT_VALUE = 0;
	
	private int value;


	public IntegerHolder()
	{
		setValue(DEFAULT_VALUE);
	}
	
	public IntegerHolder(int value)
	{
		setValue(value);
	}
	
	
	public int getValue()
	{
		return value;
	}

	public void setValue(int value)
	{
		this.value = value;
	}	
	
	
	
		
	public int hashCode() 
	{
		return value;
	}
	   
	
	public boolean equals(Object obj)
	{
		if (obj instanceof IntegerHolder)
		{
			return value == ((IntegerHolder) obj).getValue();
		}
		return false;
	}
	   
	
    public int compareTo(IntegerHolder anotherInteger)
	{
		int thisVal = this.value;
		int anotherVal = anotherInteger.value;
		return (thisVal < anotherVal ? -1 : (thisVal == anotherVal ? 0 : 1));
	}
}
