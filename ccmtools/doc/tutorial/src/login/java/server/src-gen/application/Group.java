package application;


/**
* application/Group.java .
* Generated by the IDL-to-Java compiler (portable), version "3.1"
* from ./idl2/application_Group.idl
* Wednesday, August 16, 2006 12:56:50 PM CEST
*/

public class Group implements org.omg.CORBA.portable.IDLEntity
{
  private        int __value;
  private static int __size = 3;
  private static application.Group[] __array = new application.Group [__size];

  public static final int _GUEST = 0;
  public static final application.Group GUEST = new application.Group(_GUEST);
  public static final int _USER = 1;
  public static final application.Group USER = new application.Group(_USER);
  public static final int _ADMIN = 2;
  public static final application.Group ADMIN = new application.Group(_ADMIN);

  public int value ()
  {
    return __value;
  }

  public static application.Group from_int (int value)
  {
    if (value >= 0 && value < __size)
      return __array[value];
    else
      throw new org.omg.CORBA.BAD_PARAM ();
  }

  protected Group (int value)
  {
    __value = value;
    __array[__value] = this;
  }
} // class Group
