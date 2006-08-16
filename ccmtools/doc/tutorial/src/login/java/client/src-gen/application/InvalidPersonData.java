package application;


/**
* application/InvalidPersonData.java .
* Generated by the IDL-to-Java compiler (portable), version "3.1"
* from ./idl2/application_InvalidPersonData.idl
* Wednesday, August 16, 2006 12:57:46 PM CEST
*/

public final class InvalidPersonData extends org.omg.CORBA.UserException
{
  public String message = null;

  public InvalidPersonData ()
  {
    super(InvalidPersonDataHelper.id());
  } // ctor

  public InvalidPersonData (String _message)
  {
    super(InvalidPersonDataHelper.id());
    message = _message;
  } // ctor


  public InvalidPersonData (String $reason, String _message)
  {
    super(InvalidPersonDataHelper.id() + "  " + $reason);
    message = _message;
  } // ctor

} // class InvalidPersonData
