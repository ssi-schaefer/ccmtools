package application;


/**
* application/ServerHomeImplicitPOA.java .
* Generated by the IDL-to-Java compiler (portable), version "3.1"
* from ./idl2/application_ServerHome.idl
* Wednesday, August 16, 2006 12:57:50 PM CEST
*/

public abstract class ServerHomeImplicitPOA extends org.omg.PortableServer.Servant
 implements application.ServerHomeImplicitOperations, org.omg.CORBA.portable.InvokeHandler
{

  // Constructors

  private static java.util.Hashtable _methods = new java.util.Hashtable ();
  static
  {
    _methods.put ("create", new java.lang.Integer (0));
    _methods.put ("create_component", new java.lang.Integer (1));
  }

  public org.omg.CORBA.portable.OutputStream _invoke (String $method,
                                org.omg.CORBA.portable.InputStream in,
                                org.omg.CORBA.portable.ResponseHandler $rh)
  {
    org.omg.CORBA.portable.OutputStream out = null;
    java.lang.Integer __method = (java.lang.Integer)_methods.get ($method);
    if (__method == null)
      throw new org.omg.CORBA.BAD_OPERATION (0, org.omg.CORBA.CompletionStatus.COMPLETED_MAYBE);

    switch (__method.intValue ())
    {
       case 0:  // application/ServerHomeImplicit/create
       {
         try {
           application.Server $result = null;
           $result = this.create ();
           out = $rh.createReply();
           application.ServerHelper.write (out, $result);
         } catch (Components.CreateFailure $ex) {
           out = $rh.createExceptionReply ();
           Components.CreateFailureHelper.write (out, $ex);
         }
         break;
       }


  /**
    	 	 * This operation creates a new instance of the component type 
    	 	 * associated with the home object.
    	 	 */
       case 1:  // Components/KeylessCCMHome/create_component
       {
         try {
           Components.CCMObject $result = null;
           $result = this.create_component ();
           out = $rh.createReply();
           Components.CCMObjectHelper.write (out, $result);
         } catch (Components.CreateFailure $ex) {
           out = $rh.createExceptionReply ();
           Components.CreateFailureHelper.write (out, $ex);
         }
         break;
       }

       default:
         throw new org.omg.CORBA.BAD_OPERATION (0, org.omg.CORBA.CompletionStatus.COMPLETED_MAYBE);
    }

    return out;
  } // _invoke

  // Type-specific CORBA::Object operations
  private static String[] __ids = {
    "IDL:application/ServerHomeImplicit:1.0", 
    "IDL:Components/KeylessCCMHome:1.0"};

  public String[] _all_interfaces (org.omg.PortableServer.POA poa, byte[] objectId)
  {
    return (String[])__ids.clone ();
  }

  public ServerHomeImplicit _this() 
  {
    return ServerHomeImplicitHelper.narrow(
    super._this_object());
  }

  public ServerHomeImplicit _this(org.omg.CORBA.ORB orb) 
  {
    return ServerHomeImplicitHelper.narrow(
    super._this_object(orb));
  }


} // class ServerHomeImplicitPOA
