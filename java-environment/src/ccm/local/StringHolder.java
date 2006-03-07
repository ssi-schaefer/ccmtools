package ccm.local;

import java.io.Serializable;


public class StringHolder
	implements Serializable, Comparable<StringHolder>
{
	private static final long serialVersionUID = -871933310773762520L;

	public static final String DEFAULT_VALUE = "";
	
	private String value;


	public StringHolder()
	{
		setValue(DEFAULT_VALUE);
	}
	
	public StringHolder(String value)
	{
		setValue(value);
	}
	
	
	public String getValue()
	{
		return value;
	}

	public void setValue(String value)
	{
		this.value = value;
	}
	
	
	public int hashCode() 
	{
		return getValue().hashCode();
	}
	
	
    public boolean equals(Object obj) 
    {
		if (obj instanceof StringHolder)
		{
			return getValue().equals(((StringHolder)obj).getValue());
		}
		return false;
    }
    
    
    public int compareTo(StringHolder anotherString) 
    {
    	return getValue().compareTo(anotherString.getValue());
    }
}
