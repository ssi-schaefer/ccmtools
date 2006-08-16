package application;


/**
* application/_ServerHomeImplicitStub.java .
* Generated by the IDL-to-Java compiler (portable), version "3.1"
* from ./idl2/application_ServerHome.idl
* Wednesday, August 16, 2006 12:56:54 PM CEST
*/

public class _ServerHomeImplicitStub extends org.omg.CORBA.portable.ObjectImpl implements application.ServerHomeImplicit
{

  public application.Server create () throws Components.CreateFailure
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("create", true);
                $in = _invoke ($out);
                application.Server $result = application.ServerHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                if (_id.equals ("IDL:Components/CreateFailure:1.0"))
                    throw Components.CreateFailureHelper.read ($in);
                else
                    throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return create (        );
            } finally {
                _releaseReply ($in);
            }
  } // create


  /**
    	 	 * This operation creates a new instance of the component type 
    	 	 * associated with the home object.
    	 	 */
  public Components.CCMObject create_component () throws Components.CreateFailure
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("create_component", true);
                $in = _invoke ($out);
                Components.CCMObject $result = Components.CCMObjectHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                if (_id.equals ("IDL:Components/CreateFailure:1.0"))
                    throw Components.CreateFailureHelper.read ($in);
                else
                    throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return create_component (        );
            } finally {
                _releaseReply ($in);
            }
  } // create_component

  // Type-specific CORBA::Object operations
  private static String[] __ids = {
    "IDL:application/ServerHomeImplicit:1.0", 
    "IDL:Components/KeylessCCMHome:1.0"};

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
} // class _ServerHomeImplicitStub
