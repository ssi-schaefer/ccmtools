package application;

/**
* application/PersonDataHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.1"
* from ./idl2/application_PersonData.idl
* Wednesday, August 16, 2006 12:56:52 PM CEST
*/

public final class PersonDataHolder implements org.omg.CORBA.portable.Streamable
{
  public application.PersonData value = null;

  public PersonDataHolder ()
  {
  }

  public PersonDataHolder (application.PersonData initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = application.PersonDataHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    application.PersonDataHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return application.PersonDataHelper.type ();
  }

}
