package application;

/**
* application/ServerHomeExplicitHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.1"
* from ./idl2/application_ServerHome.idl
* Wednesday, August 16, 2006 12:57:50 PM CEST
*/

public final class ServerHomeExplicitHolder implements org.omg.CORBA.portable.Streamable
{
  public application.ServerHomeExplicit value = null;

  public ServerHomeExplicitHolder ()
  {
  }

  public ServerHomeExplicitHolder (application.ServerHomeExplicit initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = application.ServerHomeExplicitHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    application.ServerHomeExplicitHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return application.ServerHomeExplicitHelper.type ();
  }

}
