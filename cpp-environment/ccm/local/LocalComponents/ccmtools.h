/***
 * This header file defines a bunch of exceptions and interfaces which
 * are used and implemented by generated component code.
 * This file correspinds with the CCM.idl file that defines all execptions
 * and interfaces in terms of IDL.
 ***/

//==============================================================================
// Local C++ CCM interfaces
//==============================================================================

#ifndef __LOCAL__COMPONENTS__H__
#define __LOCAL__COMPONENTS__H__

#include <string>
#include <vector>
#include <map>
#include <stdexcept>
#include <iostream>

#include <wamas/platform/utils/smartptr.h>

#ifdef __WGCC
    #if defined _BUILDING_CCM_RUNTIME_
        #define _CCM_EXPORT_DECL_
    #else
        #define _CCM_EXPORT_DECL_ __declspec(dllimport)
    #endif
#else
    #define _CCM_EXPORT_DECL_
#endif


namespace Components {

  //============================================================================
  // Exceptions
  //============================================================================

/**
 * This is the base class of all local CCM related exceptions.
 * (compare with CORBA::Exception in the remote case).
 */
class Exception
  : public std::exception
{
  public:
    Exception() throw();
    Exception(const std::string& message) throw();
    virtual ~Exception() throw();
    virtual const char* what() const throw();

  private:
    std::string message_;
};


/**
 * This NotImplemented exception is not part of the CCM specification
 */
class NotImplemented
  : public Exception
{
  public:
    NotImplemented() throw();
    NotImplemented(const std::string& message) throw();
    virtual ~NotImplemented() throw();
};


class InvalidName
  : public Exception
{
  public:
    InvalidName() throw();
    virtual ~InvalidName() throw();
};


class HomeNotFound
  : public Exception
{
  public:
    HomeNotFound() throw();
    virtual ~HomeNotFound() throw();
};


class AlreadyConnected
  : public Exception
{
  public:
    AlreadyConnected() throw();
    virtual ~AlreadyConnected() throw();
};


class InvalidConnection
  : public Exception
{
  public:
    InvalidConnection() throw();
    virtual ~InvalidConnection() throw();
};


class NoConnection
  : public Exception
{
  public:
    NoConnection() throw();
    virtual ~NoConnection() throw();
};


class ExceededConnectionLimit
  : public Exception
{
  public:
    ExceededConnectionLimit() throw();
    virtual ~ExceededConnectionLimit() throw();
};


class CookieRequired
  : public Exception
{
  public:
    CookieRequired() throw();
    virtual ~CookieRequired() throw();
};


class IllegalState
  : public Exception
{
  public:
    IllegalState() throw();
    virtual ~IllegalState() throw();
};


class InvalidConfiguration
  : public Exception
{
  public:
    InvalidConfiguration() throw();
    virtual ~InvalidConfiguration() throw();
};


typedef unsigned long FailureReason;

class CreateFailure
  : public Exception
{
  public:
    CreateFailure() throw();
    CreateFailure(const FailureReason reason) throw();
    virtual ~CreateFailure() throw();

  private:
    FailureReason reason_;
};


class RemoveFailure
  : public Exception
{
  public:
    RemoveFailure() throw();
    RemoveFailure(const FailureReason reason) throw();
	virtual ~RemoveFailure() throw();

  private:
    FailureReason reason_;
};


enum CCMExceptionReason
{
    SYSTEM_ERROR,
    CREATE_ERROR,
    REMOVE_ERROR,
    DUPLICATE_KEY,
    FIND_ERROR,
    OBJECT_NOT_FOUND,
    NO_SUCH_ENTITY,
    OCL_ERROR
};


class CCMException
  : public Exception
{
  public:
    CCMException() throw();
    CCMException(const CCMExceptionReason reason) throw();
	virtual ~CCMException() throw();

  private:
    CCMExceptionReason reason_;
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
  : virtual public wamas::platform::utils::RefCounted
{
  public:
	typedef wamas::platform::utils::SmartPtr<Object> SmartPtr;

    virtual ~Object();

    // Simulates the CORBA::Object::get_component() operation defines
    // since CORBA 3.0 (CCM Spec. 1-9)
    virtual SmartPtr get_component();
};


/***
 * Enterprise Component is an empty callback interface that serves as common
 * base for all component implementations.
 * CCM Specification 3-39, 4-27
 ***/
class EnterpriseComponent
  : virtual public wamas::platform::utils::RefCounted
{
  public:
  	typedef wamas::platform::utils::SmartPtr<EnterpriseComponent> SmartPtr;

    virtual ~EnterpriseComponent() {}
};



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
  : virtual public wamas::platform::utils::RefCounted
{
  public:
    typedef wamas::platform::utils::SmartPtr<CCMHome> SmartPtr;

    virtual ~CCMHome() {}

    virtual void remove_component(wamas::platform::utils::SmartPtr<CCMObject> component)
        throw(CCMException, RemoveFailure) = 0;
};


/***
 * The KeylessCCMHome interface is inherited by the implicit home interface.
 *
 * CCM Specification  1-42
 *
 * Extension to CCM-Spec: CCMException to create_component()
 ***/
class KeylessCCMHome
  : virtual public wamas::platform::utils::RefCounted
{
  public:
    typedef wamas::platform::utils::SmartPtr<KeylessCCMHome> SmartPtr;

    virtual ~KeylessCCMHome() {}

    virtual wamas::platform::utils::SmartPtr<CCMObject> create_component()
        throw(CCMException, CreateFailure) = 0;
};


/***
 * The HomeExecutorBase is a common base for all home implementations.
 * CCM Specification 3-40
 ***/
class HomeExecutorBase
  : virtual public wamas::platform::utils::RefCounted
{
  public:
    typedef wamas::platform::utils::SmartPtr<HomeExecutorBase> SmartPtr;

    virtual ~HomeExecutorBase() {}
};


/***
 * The HomeRegistration is an internal interface that may be used by the
 * CORBA component to register its home so it can be located by a
 * HomeFinder
 * CCM Specification 4-34
 ***/
class HomeRegistration
  : virtual public wamas::platform::utils::RefCounted
{
  public:
    typedef wamas::platform::utils::SmartPtr<HomeRegistration> SmartPtr;

    virtual ~HomeRegistration() {}

    /* The register_home operation is used to register a component home
     * with the HomeFinder so it can by located by a component client.
     */
    virtual void register_home(CCMHome::SmartPtr home_ref,
			       const std::string& home_name) = 0;

    /* The unregister_home operation is used to remove a component home
     * from the HomeFinder.
     */
    virtual void unregister_home(CCMHome::SmartPtr home_ref) = 0;

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
class _CCM_EXPORT_DECL_ HomeFinder
	: virtual public HomeRegistration
{
	typedef wamas::platform::utils::SmartPtr<HomeFinder> SmartPtr;
	typedef std::map<std::string, CCMHome::SmartPtr> HomePoolMap;

  public:
	static HomeFinder* Instance();
	static void destroy();

	virtual ~HomeFinder() {}

	virtual CCMHome::SmartPtr find_home_by_name(const std::string& name)
		throw(HomeNotFound);

	virtual CCMHome::SmartPtr find_home_by_component_type(const std::string& comp_repid)
		throw(HomeNotFound);

	virtual CCMHome::SmartPtr find_home_by_type(const std::string& home_repid)
		throw(HomeNotFound);

    // Methods defined in Components::HomeRegistration
	virtual void register_home(CCMHome::SmartPtr home_ref, const std::string& home_name);
	virtual void unregister_home(CCMHome::SmartPtr home_ref);
	virtual void unregister_home(const std::string& home_name);

  protected:
	  static HomeFinder* instance_;
	  HomePoolMap HomePool;
	  HomeFinder() {};
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
class CCMContext
  : virtual public wamas::platform::utils::RefCounted
{
  public:
    typedef wamas::platform::utils::SmartPtr<CCMContext> SmartPtr;

    virtual ~CCMContext() {}
    virtual HomeExecutorBase* get_CCM_home() = 0;
};


/***
 * The SessionContext is an internal interface which provides a component
 * instance with access to the container-provided runtime services. The
 * SessionContext enables the component to simply obtain all the references
 * it may require to implement its behavior.
 * CCM Spec. 4-27
 ***/
class SessionContext
  : virtual public CCMContext
{
  public:
	typedef wamas::platform::utils::SmartPtr<SessionContext> SmartPtr;

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
  : virtual public EnterpriseComponent
{
  public:
    typedef wamas::platform::utils::SmartPtr<SessionComponent> SmartPtr;

    virtual ~SessionComponent() {}

    /*
     * The set_session_context operation is used to set the SessionContext
     * of the component. The container calls this operation after a component
     * instance has been created.
     */
    virtual void set_session_context (Components::SessionContext* ctx)
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
class SessionSynchronisation
  : virtual public wamas::platform::utils::RefCounted
{
  public:
    typedef wamas::platform::utils::SmartPtr<SessionSynchronisation> SmartPtr;

    virtual ~SessionSynchronisation() {}

    virtual void after_begin()
      throw(CCMException) = 0;

    virtual void before_completion()
      throw(CCMException) = 0;

    virtual void after_completion(bool committed)
      throw(CCMException) = 0;
};


typedef std::string FeatureName;

typedef std::string RepositoryId;

typedef std::vector<FeatureName> NameList;


/***
 * The Navigation interface provides generic navigation capabilities. It is
 * inherited by all component interfaces, and may be optionally inherited by
 * any interface that is explicitly designed to be a facet interface for a
 * component.
 * CCM Specification 1-10
 * Light Weight CCM 4.1.4
 ***/
class Navigation
  : virtual public wamas::platform::utils::RefCounted
{
  public:
    typedef wamas::platform::utils::SmartPtr<Navigation> SmartPtr;

    virtual ~Navigation() {}

    /*
     * The provide_facet operation returns a reference to the facet
     * denoted by the name parameter. If the value of the name parameter does
     * not correspond to one of the component's facets, the InvalidName
     * exception shall be raised.
     */
    virtual Object::SmartPtr provide_facet(const std::string& name)
      throw(InvalidName) = 0;
};


typedef std::string OctetSeq;

/***
 * Cookie values are created by multiplex receptacles, and are used to
 * correlate a connect operation with a disconnect operation on multiplex
 * receptacles.
 * CCM Specification 1-18
 ***/
class Cookie
  : virtual public wamas::platform::utils::RefCounted
{
  public:
	typedef wamas::platform::utils::SmartPtr<Cookie> SmartPtr;

    Cookie();
    Cookie(const std::string& value);
    virtual ~Cookie();
    bool operator< (const Cookie& ck) const;

  protected:
    virtual OctetSeq cookieValue() const;
    virtual void cookieValue(const OctetSeq& cookieValue);

  private:
    OctetSeq cookieValue_;
};


/***
 * The Receptacles interface provides generic operations for connecting
 * to a component's receptacles. The CCMObject interface is derived from
 * Receptacles.
 * CCM Specification 1-18
 * Light Weight CCM 4.1.5.3
 ***/
class Receptacles
  : virtual public wamas::platform::utils::RefCounted
{
  public:
	typedef wamas::platform::utils::SmartPtr<Receptacles> SmartPtr;

    virtual ~Receptacles() {}

    /*
     * The connect() operation connects the object reference specified by
     * the connection parameter to the receptacle specified by the name
     * parameter on the target component.
     * multiplex receptacle: the operation returns a cookie value that can
     * be used subsequently to disconnect the object reference.
     * simplex receptacle: the return value is a nil.
     */
    virtual Cookie connect(const FeatureName& name, Object::SmartPtr connection)
        throw(InvalidName, InvalidConnection, AlreadyConnected, ExceededConnectionLimit) = 0;

    /*
     * Simplex receptacle: the operation will disassociate any object
     * reference currently connected to the receptacle - the cookie
     * parameter is ignored.
     * multiplex receptacle: the operation disassociates the object reference
     * associated with the cookie value from the receptacle.
     */
    virtual void disconnect(const FeatureName& name, const Cookie& ck)
        throw(InvalidName, InvalidConnection, CookieRequired, NoConnection) = 0;
};


/***
 * All interfaces for components types are derived from CCMObject.
 * CCM Specification 1-52
 * Light Weight CCM 4.1.11.1
 ***/
class CCMObject
  	: virtual public Object,
    	  virtual public Navigation,
    	  virtual public Receptacles
{
  public:
    typedef wamas::platform::utils::SmartPtr<CCMObject> SmartPtr;

	virtual ~CCMObject() {}

    /*
     * The get_ccm_home() operation returns a CCMHome reference to the
     * home which manages this component.
     */
    virtual HomeExecutorBase* get_ccm_home() = 0;

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
};



//============================================================================
// Component configuration
//============================================================================

class WrongComponentType
  : public Exception
{
  public:
    WrongComponentType() throw();
    virtual ~WrongComponentType() throw();
};


/***
 * A configurator is an object that encapsulates a specific attribute
 * configuration that can be reproduced an many instances of a component type.
 * A configurator is intended to invoke attribute set operations on the target
 * component.
 * CCM Specification 1-47
 ***/
class Configurator
  : virtual public wamas::platform::utils::RefCounted
{
  public:
	typedef wamas::platform::utils::SmartPtr<Configurator> SmartPtr;

    virtual ~Configurator() {}

    /*
     * The configure (  ) operation establishes its encapsulated configuration
     * on the target component. If the target component is not of the type
     * expected by the configurator, the operation shall raise the
     * WrongComponentType exception.
     */
    virtual void configure(const CCMObject& comp)
      throw(WrongComponentType) = 0;
};



//============================================================================
// Component assembling
//============================================================================

enum AssemblyState { INACTIVE, INSERVICE};


/**
 * The Assembly interface represents an assembly instantion. It is used to
 * build up and tear down component assemblies.
 *
 * CCM Specification 6-73
 **/
class Assembly
  : virtual public wamas::platform::utils::RefCounted
{
  public:
    typedef wamas::platform::utils::SmartPtr<Assembly> SmartPtr;

	virtual ~Assembly() {}

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


    /*
     * Build a component assembly based on a given facade component.
     *
     * Note: This is an CCM extension to support nested components.
     */
    virtual void build(CCMObject::SmartPtr facadeComponent)
        throw(CreateFailure) = 0;

    /*
     * Call configuration_complete on every component instance in the assembly.
     *
     * Note: This is an CCM extension to support nested components.
     */
    virtual void configuration_complete() = 0;
};


class AssemblyFactory
  : virtual public wamas::platform::utils::RefCounted
{
  public:
	typedef wamas::platform::utils::SmartPtr<AssemblyFactory> SmartPtr;

	virtual ~AssemblyFactory() {}

	virtual Assembly::SmartPtr create()
		throw (CreateFailure) = 0;
};


template<class T>
class AssemblyFactoryTemplate
  : virtual public AssemblyFactory
{
  public:
	typedef wamas::platform::utils::SmartPtr<AssemblyFactoryTemplate> SmartPtr;

	virtual ~AssemblyFactoryTemplate() {}

	virtual Assembly::SmartPtr create()
		throw (CreateFailure)
	{
		Assembly::SmartPtr assembly(new T());
		return assembly;
	}
};


/**
@interface ComponentDelegator
*/
class ComponentDelegator
{
public:
    virtual ~ComponentDelegator() {}

    typedef std::map<Cookie, Object::SmartPtr> Connections;

    virtual Object::SmartPtr provide(const std::string& facet) = 0;
    virtual Cookie connect(const FeatureName& receptacle, Object::SmartPtr facet) = 0;
    virtual void disconnect(const FeatureName& receptacle, const Cookie& cookie) = 0;

    void disconnect(const FeatureName& receptacle);
};

} // /namespace Components


#endif // __LOCAL__COMPONENTS__H__
