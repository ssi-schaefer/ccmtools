package client;

import java.util.List;
import java.util.ArrayList;

import Components.ccm.local.CCMException;

import world.ccm.local.*;
import world.europe.ccm.local.*;

public class MyUserTypeImpl
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
        throws CCMException
    {
        System.out.println("MyUserTypeImpl.color_value() - get");
        return colorValue;
    }

    public void color_value(Color value)
        throws CCMException
    {
        System.out.println("MyUserTypeImpl.color_value() - set");
        colorValue = value;
    }


    public Person person_value()
        throws CCMException
    {
        System.out.println("MyUserTypeImpl.person_value() - get");
        return personValue;
    }

    public void person_value(Person value)
        throws CCMException
    {
        System.out.println("MyUserTypeImpl.person_value() - set");
        personValue = value;
    }


    public Address address_value()
        throws CCMException
    {
        System.out.println("MyUserTypeImpl.address_value() - get");
        return addressValue;
    }

    public void address_value(Address value)
        throws CCMException
    {
        System.out.println("MyUserTypeImpl.adderss_value() - set");
        addressValue = value;
    }


    public List<Integer> longList_value()
        throws CCMException
    {
        System.out.println("MyUserTypeImpl.longList_value() - get");
        return longListValue;
    }

    public void longList_value(List<Integer> value)
        throws CCMException
    {
        System.out.println("MyUserTypeImpl.longList_value() - set");
        longListValue = value;
    }
    public List<String> stringList_value()
        throws CCMException
    {
        System.out.println("MyUserTypeImpl.stringList_value() - get");
        return stringListValue;
    }

    public void stringList_value(List<String> value)
        throws CCMException
    {
        System.out.println("MyUserTypeImpl.stringList_value() - set");
        stringListValue = value;
    }


    public List<Person> personList_value()
        throws CCMException
    {
        System.out.println("MyUserTypeImpl.personList_value() - get");
        return personListValue;
    }

    public void personList_value(List<Person> value)
        throws CCMException
    {
        System.out.println("MyUserTypeImpl.personList_value() - set");
        personListValue = value;
    }


    public int time_t_value()
        throws CCMException
    {
        System.out.println("MyUserTypeImpl.time_t_value() - get");
        return time_tValue;
    }

    public void time_t_value(int value)
        throws CCMException
    {
        System.out.println("MyUserTypeImpl.time_t_value() - get");
        time_tValue = value;
    }
}
