package ccm.local;

import java.io.Serializable;


public class DoubleHolder
	implements Serializable, Comparable<DoubleHolder>
{
	private static final long serialVersionUID = -2695689219362861981L;

	public static final double DEFAULT_VALUE = 0.0D;
	
	private double value;

	
	public DoubleHolder()
	{
		setValue(DEFAULT_VALUE);
	}
	
	public DoubleHolder(double value)
	{
		setValue(value);
	}
	
	
	public double getValue()
	{
		return value;
	}

	public void setValue(double value)
	{
		this.value = value;
	}	
	
	
	
    public boolean equals(Object obj)
	{
		return (obj instanceof Double)
				&& (Double.doubleToLongBits(((DoubleHolder) obj).value) == Double.doubleToLongBits(value));
	}
    
	
    public int compareTo(DoubleHolder anotherDouble)
	{
		return Double.compare(value, anotherDouble.value);
	}
}
