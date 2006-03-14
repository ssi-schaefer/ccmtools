package ccm.local;

import java.io.Serializable;

public class Holder<T>
	implements Serializable
{
	private static final long serialVersionUID = 1104196230540077898L;

	private T value;
	
	public Holder()
	{
		this(null);
	}
	
	public Holder(T value)
	{
		setValue(value);
	}
	
	
	public T getValue()
	{
		return value;
	}
	
	public void setValue(T value)
	{
		this.value = value;
	}
	
	
	public String toString()
	{
		return value.toString();
	}
}
