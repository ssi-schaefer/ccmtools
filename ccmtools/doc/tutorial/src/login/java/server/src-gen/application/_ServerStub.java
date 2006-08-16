package application;


/**
* application/_ServerStub.java .
* Generated by the IDL-to-Java compiler (portable), version "3.1"
* from ./idl2/application_Server.idl
* Wednesday, August 16, 2006 12:56:53 PM CEST
*/

public class _ServerStub extends org.omg.CORBA.portable.ObjectImpl implements application.Server
{

  public application.Login provide_login ()
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("provide_login", true);
                $in = _invoke ($out);
                application.Login $result = application.LoginHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return provide_login (        );
            } finally {
                _releaseReply ($in);
            }
  } // provide_login


  /**
    		 * Returns a CCMHome reference to the home that manages this 
    		 * component.
    		 */
  public Components.CCMHome get_ccm_home ()
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("get_ccm_home", true);
                $in = _invoke ($out);
                Components.CCMHome $result = Components.CCMHomeHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return get_ccm_home (        );
            } finally {
                _releaseReply ($in);
            }
  } // get_ccm_home


  /**
      		 * This operation is called by a configurator to indicate that
      		 * the initial component configuration has completed.
      		 */
  public void configuration_complete () throws Components.InvalidConfiguration
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("configuration_complete", true);
                $in = _invoke ($out);
                return;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                if (_id.equals ("IDL:Components/InvalidConfiguration:1.0"))
                    throw Components.InvalidConfigurationHelper.read ($in);
                else
                    throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                configuration_complete (        );
            } finally {
                _releaseReply ($in);
            }
  } // configuration_complete


  /**
      		 * This operation is used to delete a component.
      		 */
  public void remove () throws Components.RemoveFailure
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("remove", true);
                $in = _invoke ($out);
                return;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                if (_id.equals ("IDL:Components/RemoveFailure:1.0"))
                    throw Components.RemoveFailureHelper.read ($in);
                else
                    throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                remove (        );
            } finally {
                _releaseReply ($in);
            }
  } // remove


  /**
  		 * Returns a reference to the facet denoted by the name parameter.
  		 */
  public org.omg.CORBA.Object provide_facet (String name) throws Components.InvalidName
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("provide_facet", true);
                Components.FeatureNameHelper.write ($out, name);
                $in = _invoke ($out);
                org.omg.CORBA.Object $result = org.omg.CORBA.ObjectHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                if (_id.equals ("IDL:Components/InvalidName:1.0"))
                    throw Components.InvalidNameHelper.read ($in);
                else
                    throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return provide_facet (name        );
            } finally {
                _releaseReply ($in);
            }
  } // provide_facet


  /**
  		 * Connect the object reference specified by the connection parameter
  		 * to the receptacle specified by the name parameter on the target
  		 * component.
  		 * If the specified receptacle is a multiplex receptacle, the 
  		 * operation returns a cookie value that can be used subsequently
  		 * to disconnect the object reference.
  		 * If the receptacle is a simplex receptacle, the return value is a
  		 * nil.
  		 */
  public Components.Cookie connect (String name, org.omg.CORBA.Object connection) throws Components.InvalidName, Components.InvalidConnection, Components.AlreadyConnected, Components.ExceededConnectionLimit
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("connect", true);
                Components.FeatureNameHelper.write ($out, name);
                org.omg.CORBA.ObjectHelper.write ($out, connection);
                $in = _invoke ($out);
                Components.Cookie $result = Components.CookieHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                if (_id.equals ("IDL:Components/InvalidName:1.0"))
                    throw Components.InvalidNameHelper.read ($in);
                else if (_id.equals ("IDL:Components/InvalidConnection:1.0"))
                    throw Components.InvalidConnectionHelper.read ($in);
                else if (_id.equals ("IDL:Components/AlreadyConnected:1.0"))
                    throw Components.AlreadyConnectedHelper.read ($in);
                else if (_id.equals ("IDL:Components/ExceededConnectionLimit:1.0"))
                    throw Components.ExceededConnectionLimitHelper.read ($in);
                else
                    throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return connect (name, connection        );
            } finally {
                _releaseReply ($in);
            }
  } // connect


  /**
  		 * If the receptacle defined by the name parameter is a simplex one,
  		 * the operation will disassociate any object reference currently 
  		 * connected to the receptacle (the cookie value is ignored).
  		 * If the receptacle defined by the name parameter is a multiplex
  		 * receptacle, the disconnect operation disassociates the object
  		 * reference associated with the cookie value from the receptacle.
  		 */
  public void disconnect (String name, Components.Cookie ck) throws Components.InvalidName, Components.InvalidConnection, Components.CookieRequired, Components.NoConnection
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("disconnect", true);
                Components.FeatureNameHelper.write ($out, name);
                Components.CookieHelper.write ($out, ck);
                $in = _invoke ($out);
                return;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                if (_id.equals ("IDL:Components/InvalidName:1.0"))
                    throw Components.InvalidNameHelper.read ($in);
                else if (_id.equals ("IDL:Components/InvalidConnection:1.0"))
                    throw Components.InvalidConnectionHelper.read ($in);
                else if (_id.equals ("IDL:Components/CookieRequired:1.0"))
                    throw Components.CookieRequiredHelper.read ($in);
                else if (_id.equals ("IDL:Components/NoConnection:1.0"))
                    throw Components.NoConnectionHelper.read ($in);
                else
                    throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                disconnect (name, ck        );
            } finally {
                _releaseReply ($in);
            }
  } // disconnect

  // Type-specific CORBA::Object operations
  private static String[] __ids = {
    "IDL:application/Server:1.0", 
    "IDL:Components/CCMObject:1.0", 
    "IDL:Components/Navigation:1.0", 
    "IDL:Components/Receptacles:1.0"};

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
} // class _ServerStub
