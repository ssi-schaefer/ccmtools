package ccm.local;

import java.io.Serializable;


public class CharacterHolder
	implements Serializable, Comparable<CharacterHolder>
{
	private static final long serialVersionUID = 1668875810751484728L;

	public static final char DEFAULT_VALUE = 0;
	
	private char value;

	
	public CharacterHolder()
	{
		setValue(DEFAULT_VALUE);
	}
	
	public CharacterHolder(char value)
	{
		setValue(value);
	}
	
	
	public char getValue()
	{
		return value;
	}

	public void setValue(char value)
	{
		this.value = value;
	}
	
	
    /**
     * Returns a hash code for this CharacterHolder.
     */
    public int hashCode() 
    {
        return (int)value;
    }
    
	
	/**
     * Compares this object against the specified object.
     * The result is true if and only if the argument is not
     * null and is a CharacterHolder object that
     * represents the same char value as this object.
     */
    public boolean equals(Object obj) 
    {
        if (obj instanceof CharacterHolder) 
        {
            return value == ((CharacterHolder)obj).getValue();
        }
        return false;
    }
    
    
    /**
     * Compares two CharacterHolder objects numerically.
     */
    public int compareTo(CharacterHolder anotherCharacter) 
    {
        return this.value - anotherCharacter.value;
    }
}
