package ccm.local;

import java.io.Serializable;


public class FloatHolder
	implements Serializable, Comparable<FloatHolder>
{
	private static final long serialVersionUID = 8418859918252857704L;

	public static final float DEFAULT_VALUE = (float) 0.0;
	
	private float value;


	public FloatHolder()
	{
		setValue(DEFAULT_VALUE);
	}
	
	public FloatHolder(float value)
	{
		setValue(value);
	}
	
	
	public float getValue()
	{
		return value;
	}

	public void setValue(float value)
	{
		this.value = value;
	}	
	
		
    public int hashCode() 
    {
    	return Float.floatToIntBits(value);
    }

	
    public boolean equals(Object obj)
	{
		return (obj instanceof Float)
				&& (Float.floatToIntBits(((FloatHolder) obj).value) == Float.floatToIntBits(value));
	}

    
	public int compareTo(FloatHolder anotherFloat) 
    {
        return Float.compare(value, anotherFloat.value);
    }
}
