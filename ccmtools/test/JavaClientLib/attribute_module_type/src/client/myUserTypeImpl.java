package client;

import java.util.List;
import java.util.ArrayList;
import world.europe.austria.ccm.local.*;

public class myUserTypeImpl
    implements UserTypeInterface
{
    private Color colorValue;
    private Person personValue;
    private Address addressValue;
    private List<Integer> longListValue;
    private List<String> stringListValue;
    private List<Person> personListValue;
    private int time_tValue;
    
    public Color color_value()
        throws ccm.local.Components.CCMException
    {
	System.out.println("myUserTypeImpl.color_value() - get");
	return colorValue;
    }

    public void color_value(Color value)
        throws ccm.local.Components.CCMException
    {
	System.out.println("myUserTypeImpl.color_value() - set");
	colorValue = value;	
    }


    public Person person_value()
        throws ccm.local.Components.CCMException
    {
	System.out.println("myUserTypeImpl.person_value() - get");
	return personValue;
    }

    public void person_value(Person value)
        throws ccm.local.Components.CCMException
    {
	System.out.println("myUserTypeImpl.person_value() - set");
	personValue = value;
    }


    public Address address_value()
        throws ccm.local.Components.CCMException
    {
	System.out.println("myUserTypeImpl.address_value() - get");
	return addressValue;
    }

    public void address_value(Address value)
        throws ccm.local.Components.CCMException
    {
	System.out.println("myUserTypeImpl.adderss_value() - set");
	addressValue = value;
    }


    public List<Integer> longList_value()
        throws ccm.local.Components.CCMException
    {
	System.out.println("myUserTypeImpl.longList_value() - get");
	return longListValue;
    }

    public void longList_value(List<Integer> value)
        throws ccm.local.Components.CCMException
    {
	System.out.println("myUserTypeImpl.longList_value() - set");
	longListValue = value;
    }

    public List<String> stringList_value()
        throws ccm.local.Components.CCMException
    {
	System.out.println("myUserTypeImpl.stringList_value() - get");
	return stringListValue;
    }

    public void stringList_value(List<String> value)
        throws ccm.local.Components.CCMException
    {
	System.out.println("myUserTypeImpl.stringList_value() - set");
	stringListValue = value;
    }


    public List<Person> personList_value()
        throws ccm.local.Components.CCMException
    {
	System.out.println("myUserTypeImpl.personList_value() - get");
	return personListValue;
    }

    public void personList_value(List<Person> value)
        throws ccm.local.Components.CCMException
    {
	System.out.println("myUserTypeImpl.personList_value() - set");
	personListValue = value;
    }


    public int time_t_value()
        throws ccm.local.Components.CCMException
    {
	System.out.println("myUserTypeImpl.time_t_value() - get");
	return time_tValue;
    }

    public void time_t_value(int value)
        throws ccm.local.Components.CCMException
    {
	System.out.println("myUserTypeImpl.time_t_value() - get");
	time_tValue = value;
    }
}
