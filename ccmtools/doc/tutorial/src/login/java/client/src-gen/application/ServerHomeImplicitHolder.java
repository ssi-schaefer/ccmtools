package application;

/**
* application/ServerHomeImplicitHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.1"
* from ./idl2/application_ServerHome.idl
* Wednesday, August 16, 2006 12:57:50 PM CEST
*/

public final class ServerHomeImplicitHolder implements org.omg.CORBA.portable.Streamable
{
  public application.ServerHomeImplicit value = null;

  public ServerHomeImplicitHolder ()
  {
  }

  public ServerHomeImplicitHolder (application.ServerHomeImplicit initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = application.ServerHomeImplicitHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    application.ServerHomeImplicitHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return application.ServerHomeImplicitHelper.type ();
  }

}
