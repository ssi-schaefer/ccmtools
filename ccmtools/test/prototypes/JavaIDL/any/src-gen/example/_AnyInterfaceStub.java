package example;


/**
* example/_AnyInterfaceStub.java .
* Generated by the IDL-to-Java compiler (portable), version "3.1"
* from anyTest.idl
* Monday, August 7, 2006 3:09:10 PM CEST
*/

public class _AnyInterfaceStub extends org.omg.CORBA.portable.ObjectImpl implements example.AnyInterface
{

  public void f1 (org.omg.CORBA.Any p1)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("f1", true);
                $out.write_any (p1);
                $in = _invoke ($out);
                return;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                f1 (p1        );
            } finally {
                _releaseReply ($in);
            }
  } // f1

  // Type-specific CORBA::Object operations
  private static String[] __ids = {
    "IDL:example/AnyInterface:1.0"};

  public String[] _ids ()
  {
    return (String[])__ids.clone ();
  }

  private void readObject (java.io.ObjectInputStream s) throws java.io.IOException
  {
     String str = s.readUTF ();
     String[] args = null;
     java.util.Properties props = null;
     org.omg.CORBA.Object obj = org.omg.CORBA.ORB.init (args, props).string_to_object (str);
     org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl) obj)._get_delegate ();
     _set_delegate (delegate);
  }

  private void writeObject (java.io.ObjectOutputStream s) throws java.io.IOException
  {
     String[] args = null;
     java.util.Properties props = null;
     String str = org.omg.CORBA.ORB.init (args, props).object_to_string (this);
     s.writeUTF (str);
  }
} // class _AnyInterfaceStub
