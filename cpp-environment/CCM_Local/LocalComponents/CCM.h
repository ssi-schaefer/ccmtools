//==============================================================================
// Local C++ CCM interfaces
//==============================================================================

#ifndef __LOCAL__COMPONENTS__H__
#define __LOCAL__COMPONENTS__H__

#include <string>
#include <vector>

#include <WX/Utils/smartptr.h>
#include <WX/Utils/value.h>


namespace LocalComponents {

  //============================================================================
  // Exceptions
  //============================================================================

  /**
   * This is the base class of all local CCM related exceptions.
   * (compare with CORBA::Exception in the remote case).
   **/
  class Exception {
    
  };

  class UserException : public Exception {

  };

  class SystemException : public Exception {

  };


  // is not part of the CCM specification
  class NotImplemented : public UserException {
  protected:
    std::string desc;
  public:
    NotImplemented ( void ) : desc ( "Not implemented" ) {}
    NotImplemented ( const std::string& s ) : desc ( s ) {}
    std::string what ( void ) { return desc; }
  };

  class InvalidName : public UserException {
  protected:
    std::string desc;
  public:
    InvalidName ( void ) : desc ( "Invalid name" ) {}
    InvalidName ( const std::string& s ) : desc ( s ) {}
    std::string what ( void ) { return desc; }
  };

  class HomeNotFound : public UserException {
  protected:
    std::string desc;
  public:
    HomeNotFound ( void ) : desc ( "Home not found" ) {}
    HomeNotFound ( const std::string& s ) : desc ( s ) {}
    std::string what ( void ) { return desc; }
  };

  class AlreadyConnected : public UserException {};

  class InvalidConnection : public UserException {};

  class NoConnection : public UserException {};

  class ExceededConnectionLimit : public UserException {};

  class CookieRequired : public UserException {};

  class IllegalState : public UserException {};

  class InvalidConfiguration : public UserException {};

  //  class NoKeyAvailable : public UserException {};

  typedef unsigned long FailureReason;

  class CreateFailure : public UserException {
  private:
    FailureReason _reason;
  public:
    CreateFailure () : _reason(0) {}
    CreateFailure ( const FailureReason reason ) : _reason(reason) {}
  };


  //  class FinderFailure : public UserException {
  //  private:
  //    FailureReason _reason;
  //  public:
  //    FinderFailure ( const FailureReason reason ) : _reason(reason) {}
  //  };

  class RemoveFailure : public UserException {
  private:
    FailureReason _reason;
  public:
    RemoveFailure () : _reason(0) {}
    RemoveFailure ( const FailureReason reason ) : _reason(reason) {}
  };

  //  class DuplicateKeyValue : public UserException {};
  //  class InvalidKey : public UserException {};
  //  class UnknownKeyValue : public UserException {};

  enum CCMExceptionReason {
    SYSTEM_ERROR,
    CREATE_ERROR,
    REMOVE_ERROR,
    DUPLICATE_KEY,
    FIND_ERROR,
    OBJECT_NOT_FOUND,
    NO_SUCH_ENTITY,
    OCL_ERROR
  };

  class CCMException : public UserException {
  private:
    CCMExceptionReason _reason;
  public:
    CCMException () : _reason(SYSTEM_ERROR) {}
    CCMException ( const CCMExceptionReason reason ) throw() : _reason(reason) {}
  };


  //============================================================================
  // Common interfaces
  //============================================================================

  /***
   * This is the local substitute for the CORBA::Object interface.
   * Every interface inherits from Object!
   * Note: This interface is not part of the CCM specification!
   ***/
  class Object
    : virtual public WX::Utils::RefCounted {
  public:
    virtual ~Object (  ) {}

    // Simulates the CORBA::Object::get_component() operation defines
    // since CORBA 3.0 (CCM Spec. 1-9)	
    virtual WX::Utils::SmartPtr<Object> get_component() {
	return WX::Utils::SmartPtr<Object>();
    };	
  };


  /***
   * The IRObject comes from CORBA::ComponentIR::ComponentDef
   ***/
  //  class IRObject {
  //  public:
  //    virtual ~IRObject() {}
  //  };


  /***
   * Enterprise Component is an empty callback interface that serves as common
   * base for all component implementations.
   * CCM Specification 3-39, 4-27
   ***/
  class EnterpriseComponent {
  public:
    virtual ~EnterpriseComponent (  ) {}
  };


  /***
   * From IDL3:
   * typedef SecurityLevel2::Credentials Principal;
   * CCM Specification 4-22
   * Light Weight CCM 4.4.3.1
   ***/
  //  class Principal {
  //  public:
  //    virtual ~Principal (  ) {}
  //  };



  //============================================================================
  // Home interfaces
  //============================================================================
  class CCMObject;

  /***
   * The CCMHome interface is interited by the explicit home interface.
   *
   * CCM Specification 1-41
   * Light Weight CCM 4.1.7.5
   * Extension to CCM-Spec: CCMException to remove_component()
   ***/
  class CCMHome
    : virtual public WX::Utils::RefCounted
  {
  public:
    virtual ~CCMHome() {}

    //    virtual IRObject* get_component_def() = 0;
    //    virtual IRObject* get_home_def() = 0;

    virtual void remove_component(const CCMObject& comp)
        throw(CCMException, RemoveFailure) = 0;
  };


  /***
   * The KeylessCCMHome interface is inherited by the implicit home interface.
   *
   * CCM Specification  1-42
   *
   * Extension to CCM-Spec: CCMException to create_component()
   ***/
  class KeylessCCMHome {
  public:
    virtual ~KeylessCCMHome() {}

    virtual CCMObject* create_component()
        throw(CCMException, CreateFailure) = 0;
  };


  /***
   * The HomeExecutorBase is a common base for all home implementations.
   * CCM Specification 3-40
   ***/
  class HomeExecutorBase {
  public:
    virtual ~HomeExecutorBase() {}
  };


  /***
   * The HomeRegistration is an internal interface that may be used by the
   * CORBA component to register its home so it can be located by a
   * HomeFinder
   * CCM Specification 4-34
   ***/
  class HomeRegistration {
  public:
    virtual ~HomeRegistration() {}

    /* The register_home operation is used to register a component home
     * with the HomeFinder so it can by located by a component client.
     */
    virtual void register_home(WX::Utils::SmartPtr<CCMHome> home_ref, 
			       const std::string& home_name) = 0;

    /* The unregister_home operation is used to remove a component home
     * from the HomeFinder.
     */
    virtual void unregister_home(WX::Utils::SmartPtr<CCMHome> home_ref) = 0;

    /*
     * This unregister_home operation is used to remove a component home
     * (defined by the home_name) from the HomeFinder.
     * Note: this method is NOT defined in the CCM specification!!
     */
    virtual void unregister_home(const std::string& home_name) = 0;
  };


  /***
   * Clients can use the HomeFinder interface to obtain homes for particular
   * component types, of particularly homes, or homes that are bound to
   * specific names in a naming service.
   * CCM Spec. 1-42
   ***/
  class HomeFinder
    : public HomeRegistration {
  public:
    virtual ~HomeFinder() {}

    virtual WX::Utils::SmartPtr<LocalComponents::CCMHome>
    find_home_by_component_type(const std::string& comp_repid)
        throw(HomeNotFound) = 0;

    virtual WX::Utils::SmartPtr<LocalComponents::CCMHome>
    find_home_by_name(const std::string& name)
        throw(HomeNotFound) = 0;

    virtual WX::Utils::SmartPtr<LocalComponents::CCMHome>
      find_home_by_type(const std::string& home_repid)
        throw(HomeNotFound) = 0;
  };



  //============================================================================
  // Context interfaces
  //============================================================================

 /***
  * The CCMContext is an internal interface which provides a component instance
  * with access to the common container-provided runtime services.
  * It serves as a "bootstrap" to the various services the container provides
  * for the component.
  * CCM Spec. 4-22
  * Light Weight CCM 4.4.3.2
  ***/
  class CCMContext {
  public:
    virtual ~CCMContext() {}

    //    virtual Principal* get_caller_principal() = 0;

    virtual HomeExecutorBase* get_CCM_home() = 0;

    //    virtual bool get_rollback_only()
    //      throw (IllegalState) = 0;

    //    virtual LocalTransaction::UserTransaction* get_user_transaction()
    //      throw (IllegalState) = 0;

    //    virtual bool is_caller_in_role(const std::string& role) = 0;

    //    virtual void set_rollback_only()
    //      throw (IllegalState) = 0;
  };


  /***
   * The SessionContext is an internal interface which provides a component
   * instance with access to the container-provided runtime services. The
   * SessionContext enables the component to simply obtain all the references
   * it may require to implement its behavior.
   * CCM Spec. 4-27
   ***/
  class SessionContext : public CCMContext
    {
    public:
      virtual ~SessionContext() {}

      /***
       * The get_CCM_object operation is used to get the reference used to
       * invoke the component (component reference or facet reference. If this
       * operation is issued outside of the scope of a callback operation, the
       * IllegalState exception is returned.
       ***/
      virtual Object* get_CCM_object()
	throw (IllegalState) = 0;
    };



  //============================================================================
  // Component interfaces
  //============================================================================


  /***
   * The SessionComponent is a callback interface implemented by a session CORBA
   * component. It provides operations for disassociating a context with the
   * component and to manage servant lifetime for a session component.
   * CCM Specification 4-28
   ***/
  class SessionComponent
    : public EnterpriseComponent {
    public:
    virtual ~SessionComponent() {}

    /*
     * The set_session_context operation is used to set the SessionContext
     * of the component. The container calls this operation after a component
     * instance has been created.
     */
    virtual void set_session_context (LocalComponents::SessionContext* ctx)
      throw (CCMException) = 0;

    /*
     * The ccm_activate operation is called by the container to notify a
     * session component that is has been made active.
     */
    virtual void ccm_activate()
      throw (CCMException) = 0;

    /*
     * The ccm_passivate operation is called by the container to notify a
     * session component that it has been made inactive.
     */
    virtual void ccm_passivate()
      throw (CCMException) = 0;

    /*
     * The void ccm_remove operation is called by the container when the
     * servant is about to be destroyed.
     */
    virtual void ccm_remove()
      throw (CCMException) = 0;
  };



  /***
   * The SessionSynchronisation interface is a callback interface that may be
   * optionally be implemented by the session component. It permits the
   * component to be notified of transaction boundaries by the container.
   * CCM Specification 4-29
   ***/
  class SessionSynchronisation {
  public:
    virtual ~SessionSynchronisation (  ) {}

    virtual void after_begin (  )
      throw ( CCMException ) = 0;

    virtual void before_completion (  )
      throw ( CCMException ) = 0;

    virtual void after_completion ( bool committed )
      throw ( CCMException ) = 0;
  };



  typedef std::string FeatureName;
  typedef std::string RepositoryId;
  typedef std::vector<FeatureName> NameList;

  /***
   *
   * Light Weight CCM 4.1.4
   ***/
  //  class PortDescription {
  //  private:
  //    FeatureName _name;
  //    RepositoryId _type_id;
  //  public:
  //    virtual ~PortDescription (  ) {}

  //    virtual FeatureName name (  ) const           { return _name; }
  //    virtual void name ( const FeatureName& name ) { _name = name; }

  //    virtual RepositoryId type_id (  ) const              { return _type_id; }
  //    virtual void type_id ( const RepositoryId& type_id ) {_type_id = type_id; }
  //  };

  //  class FacetDescription
  //    : public PortDescription {
  //    private:
  //    Object* _facet_ref;

  //    public:
  //    FacetDescription (  ) { _facet_ref = NULL; }
  //    virtual ~FacetDescription (  ) {}

  //    virtual Object* facet_ref (  ) const         { return _facet_ref; }
  //    virtual void facet_ref ( Object* facet_ref ) {_facet_ref = facet_ref; }
  //  };

  //  typedef std::vector<FacetDescription> FacetDescriptions;



  /***
   * The Navigation interface provides generic navigation capabilities. It is
   * inherited by all component interfaces, and may be optionally inherited by
   * any interface that is explicitly designed to be a facet interface for a
   * component.
   * CCM Specification 1-10
   * Light Weight CCM 4.1.4
   ***/
  class Navigation {
  public:
    virtual ~Navigation() {}

    /*
     * The provide_facet operation returns a reference to the facet
     * denoted by the name parameter. If the value of the name parameter does
     * not correspond to one of the component's facets, the InvalidName
     * exception shall be raised.
     */
    virtual WX::Utils::SmartPtr<Object> provide_facet(const std::string& name)
      throw(InvalidName) = 0;

    /*
     * The get_all_facets operation returns a sequence of value objects,
     * each of which contains the RepositoryId of the facet interface and
     * name of the facet, along with a reference to the facet.
     */
    //    virtual FacetDescriptions get_all_facets() = 0;

    /*
     * The get_named_facets operation returns a sequence of described
     * reterences, containing descriptions and references for the facets
     * denoted by the names parameter. If any name in the names parameter is not
     * a valid name for a provided interface on the component, the operation
     * raises the InvalidName exception.
     */
    //    virtual FacetDescriptions get_named_facets(const NameList& names)
    //        throw(InvalidName) = 0;

    /*
     * The same_component operation allows clients to determine reliably
     * whether two references belong to the same component instance.
     */
    //    virtual bool same_component(const Object& obj) = 0;
  };


  /***
   * Cookie values are created by multiplex receptacles, and are used to
   * correlate a connect operation with a disconnect operation on multiplex
   * receptacles.
   * CCM Specification 1-18
   ***/

  typedef std::string OctetSeq;

  class Cookie {
  private:
    OctetSeq _cookieValue;
  public:
    Cookie() { 
      _cookieValue = ""; 
    }

    Cookie(const std::string& value) { 
      _cookieValue = value; 
    }

    virtual ~Cookie() {}

    bool operator< (const Cookie& ck) const { 
      return _cookieValue < ck._cookieValue; 
    }

  protected:
    virtual OctetSeq cookieValue() const {  
      return _cookieValue; 
    }

    virtual void cookieValue (const OctetSeq& cookieValue) { 
      _cookieValue = cookieValue; 
    }
  };


  /***
   *
   * Light Weight CCM 4.1.5.3
   ***/
  //  class ConnectionDescription {
  //    private:
  //    Cookie _ck;
  //    Object* _objref;

  //    public:
  //    ConnectionDescription (  ) { _objref = NULL; }
  //    virtual ~ConnectionDescription (  ) {}

  //    virtual Cookie ck (  ) const              { return _ck; }
  //    virtual void ck ( const Cookie& ck )      {_ck = ck; }

  //    virtual Object* objref (  ) const         { return _objref; }
  //    virtual void objref ( Object* objref )    {_objref = objref; }
  //  };

  //  typedef std::vector<ConnectionDescription> ConnectionDescriptions;

  //  class ReceptacleDescription
  //    : public PortDescription {
  //    private:
  //    bool _is_multiple;
  //    ConnectionDescriptions _connections;

  //    public:
  //    ReceptacleDescription (  ) {
  //      _is_multiple = false;
  //    }
  //    virtual ~ReceptacleDescription (  ) {}

  //    virtual bool is_multiple (  ) const { 
  //        return _is_multiple; 
  //    }
  //    virtual void is_multiple ( bool is_multiple ) {
  //        _is_multiple = is_multiple; 
  //    }

  //    virtual ConnectionDescriptions connections (  ) const { 
  //        return _connections; 
  //    }
  //    virtual void connections ( const ConnectionDescriptions& connections ) {
  //        _connections = connections; 
  //    }
  //  };

  //  typedef std::vector<ReceptacleDescription> ReceptacleDescriptions;


  /***
   * The Receptacles interface provides generic operations for connecting
   * to a component's receptacles. The CCMObject interface is derived from
   * Receptacles.
   * CCM Specification 1-18
   * Light Weight CCM 4.1.5.3
   ***/
  class Receptacles {
  public:
    virtual ~Receptacles() {}

    /*
     * The connect() operation connects the object reference specified by
     * the connection parameter to the receptacle specified by the name
     * parameter on the target component.
     * multiplex receptacle: the operation returns a cookie value that can
     * be used subsequently to disconnect the object reference.
     * simplex receptacle: the return value is a nil.
     */
    virtual Cookie connect(const FeatureName& name, 
			    WX::Utils::SmartPtr<Object> connection)
      throw(InvalidName, 
	    InvalidConnection, 
	    AlreadyConnected, 
	    ExceededConnectionLimit) = 0;

    /*
     * Simplex receptacle: the operation will disassociate any object
     * reference currently connected to the receptacle - the cookie
     * parameter is ignored.
     * multiplex receptacle: the operation disassociates the object reference
     * associated with the cookie value from the receptacle.
     */
    virtual void disconnect(const FeatureName& name, 
			    const Cookie& ck)
      throw(InvalidName, 
	    InvalidConnection, 
	    CookieRequired, 
	    NoConnection) = 0;

    //    virtual ConnectionDescriptions get_connections(const FeatureName& name)
    //        throw(InvalidName) = 0;

    //    virtual ReceptacleDescriptions get_all_receptacles() = 0;

    //    virtual ReceptacleDescriptions get_named_receptacles(const NameList& names)
    //        throw(InvalidName) = 0;
  };



  /***
   *
   * Light Weight CCM 4.1.11.1
   ***/
  //  class ComponentPortDescription {
  //  private:
  //    FacetDescriptions _facets;
  //    ReceptacleDescriptions _receptacles;
  //    ConsumerDescriptions _consumers;
  //    EmitterDescriptions _emitters; 
  //    PublisherDescriptions _publishers;
  //  public:
  //    virtual ~ComponentPortDescription (  ) {}
  //    virtual FacetDescriptions facets (  ) const             
  //        { return _facets; }
  //    virtual void facets ( const FacetDescriptions& facets ) 
  //        {_facets = facets; }
  //    virtual ReceptacleDescriptions receptacles (  ) const                  
  //        { return _receptacles; }
  //    virtual void receptacles ( const ReceptacleDescriptions& receptacles ) 
  //        {_receptacles = receptacles; }
  //  };


  /***
   *
   * Light Weight CCM 4.1.7.2
   ***/
  //  class PrimaryKeyBase {
  //  public:
  //    virtual ~PrimaryKeyBase (  ) {}
  //  };


  /***
   * All interfaces for components types are derived from CCMObject.
   * CCM Specification 1-52
   * Light Weight CCM 4.1.11.1
   ***/
  class CCMObject : virtual public Object,
    public Navigation, public Receptacles {
    public:

    //    virtual IRObject* get_component_def() = 0;

    /*
     * The get_ccm_home() operation returns a CCMHome reference to the
     * home which manages this component.
     */
    virtual HomeExecutorBase* get_ccm_home() = 0;

    //    virtual PrimaryKeyBase* get_primary_key()
    //        throw(NoKeyAvailable) = 0;

    /*
     * This operation is called by a configurator to indicate that the
     * initial component configuration has completed.
     * If the component determines that it is not sufficiently configured
     * to allow normal client access, it raises the InvalidConfiguration
     * exception.
     */
    virtual void configuration_complete()
        throw(InvalidConfiguration) = 0;

    /*
     * This operation is used to delete a component.
     * Application failures during remove may raise the RemoveFailure
     * exception.
     */
    virtual void remove()
        throw(RemoveFailure) = 0;

    //    virtual ComponentPortDescription get_all_ports() = 0;
  };



  //============================================================================
  // Component configuration
  //============================================================================

  class WrongComponentType {};

  /***
   * A configurator is an object that encapsulates a specific attribute
   * configuration that can be reproduced an many instances of a component type.
   * A configurator is intended to invoke attribute set operations on the target
   * component.
   * CCM Specification 1-47
   ***/
  class Configurator {
  public:
    virtual ~Configurator (  ) {}

    /*
     * The configure (  ) operation establishes its encapsulated configuration
     * on the target component. If the target component is not of the type
     * expected by the configurator, the operation shall raise the
     * WrongComponentType exception.
     */
    virtual void configure ( const CCMObject& comp )
      throw ( WrongComponentType ) = 0;
  };



  //============================================================================
  // Component assembling
  //============================================================================

  enum AssemblyState { INACTIVE,
		       INSERVICE};

  class InvalidLocation {};
  class InvalidAssembly {};


  /**
   * The Assembly interface represents an assembly instantion. It is used to
   * build up and tear down component assemblies.
   *      
   * CCM Specification 6-73
   **/
  class Assembly { 
  public:

    /*
     * Creates required component servers, creates required containers, installs
     * required component homes, instantiates components, configures and 
     * interconnects them according to the assembly descriptor.
     */
    virtual void build()
        throw (CreateFailure) = 0;

    /*
     * Removes all connections between components and destroys all components,
     * homes, containers, and component servers that were created by the build 
     * operation.
     */
    virtual void tear_down()
        throw (RemoveFailure) = 0;

    /*
     * Returns whether the assembly is active or inactive.
     */
    virtual AssemblyState get_state() = 0;
  };


} // /namespace LocalComponents


#endif // __LOCAL__COMPONENTS__H__


