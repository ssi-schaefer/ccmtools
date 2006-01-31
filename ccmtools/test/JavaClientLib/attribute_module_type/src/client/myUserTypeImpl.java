package client;

import world.europe.austria.ccm.local.*;

public class myUserTypeImpl
    implements UserTypeInterface
{
    private world.europe.austria.Color colorValue;
    private world.europe.austria.Person personValue;
    private world.europe.austria.Address addressValue;
    private int[] longListValue;
    private String[] stringListValue;
    private world.europe.austria.Person[] personListValue;
    private int time_tValue;
    
    public world.europe.austria.Color color_value()
        throws ccm.local.Components.CCMException
    {
	System.out.println("myUserTypeImpl.color_value() - get");
	return colorValue;
    }

    public void color_value(world.europe.austria.Color value)
        throws ccm.local.Components.CCMException
    {
	System.out.println("myUserTypeImpl.color_value() - set");
	colorValue = value;	
    }


    public world.europe.austria.Person person_value()
        throws ccm.local.Components.CCMException
    {
	System.out.println("myUserTypeImpl.person_value() - get");
	return personValue;
    }

    public void person_value(world.europe.austria.Person value)
        throws ccm.local.Components.CCMException
    {
	System.out.println("myUserTypeImpl.person_value() - set");
	personValue = value;
    }


    public world.europe.austria.Address address_value()
        throws ccm.local.Components.CCMException
    {
	System.out.println("myUserTypeImpl.address_value() - get");
	return addressValue;
    }

    public void address_value(world.europe.austria.Address value)
        throws ccm.local.Components.CCMException
    {
	System.out.println("myUserTypeImpl.adderss_value() - set");
	addressValue = value;
    }


    public int[] longList_value()
        throws ccm.local.Components.CCMException
    {
	System.out.println("myUserTypeImpl.longList_value() - get");
	return longListValue;
    }

    public void longList_value(int[] value)
        throws ccm.local.Components.CCMException
    {
	System.out.println("myUserTypeImpl.longList_value() - set");
	longListValue = value;
    }

    public String[] stringList_value()
        throws ccm.local.Components.CCMException
    {
	System.out.println("myUserTypeImpl.stringList_value() - get");
	return stringListValue;
    }

    public void stringList_value(String[] value)
        throws ccm.local.Components.CCMException
    {
	System.out.println("myUserTypeImpl.stringList_value() - set");
	stringListValue = value;
    }


    public world.europe.austria.Person[] personList_value()
        throws ccm.local.Components.CCMException
    {
	System.out.println("myUserTypeImpl.personList_value() - get");
	return personListValue;
    }

    public void personList_value(world.europe.austria.Person[] value)
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
