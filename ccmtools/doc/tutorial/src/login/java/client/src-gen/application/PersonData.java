package application;


/**
* application/PersonData.java .
* Generated by the IDL-to-Java compiler (portable), version "3.1"
* from ./idl2/application_PersonData.idl
* Wednesday, August 16, 2006 12:57:48 PM CEST
*/

public final class PersonData implements org.omg.CORBA.portable.IDLEntity
{
  public int id = (int)0;
  public String name = null;
  public String password = null;
  public application.Group group = null;

  public PersonData ()
  {
  } // ctor

  public PersonData (int _id, String _name, String _password, application.Group _group)
  {
    id = _id;
    name = _name;
    password = _password;
    group = _group;
  } // ctor

} // class PersonData
