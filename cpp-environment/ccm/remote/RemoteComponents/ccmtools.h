/*
 *  MICO --- an Open Source CORBA implementation
 *  Copyright (c) 1997-2003 by The Mico Team
 *
 *  This file was automatically generated. DO NOT EDIT!
 */

#include <CORBA.h>
#include <mico/throw.h>

#ifndef __CCMTOOLS_H__
#define __CCMTOOLS_H__






namespace ccmtools
{
namespace corba
{
namespace Components
{

class Navigation;
typedef Navigation *Navigation_ptr;
typedef Navigation_ptr NavigationRef;
typedef ObjVar< Navigation > Navigation_var;
typedef ObjOut< Navigation > Navigation_out;

class Receptacles;
typedef Receptacles *Receptacles_ptr;
typedef Receptacles_ptr ReceptaclesRef;
typedef ObjVar< Receptacles > Receptacles_var;
typedef ObjOut< Receptacles > Receptacles_out;

class CCMObject;
typedef CCMObject *CCMObject_ptr;
typedef CCMObject_ptr CCMObjectRef;
typedef ObjVar< CCMObject > CCMObject_var;
typedef ObjOut< CCMObject > CCMObject_out;

class CCMHome;
typedef CCMHome *CCMHome_ptr;
typedef CCMHome_ptr CCMHomeRef;
typedef ObjVar< CCMHome > CCMHome_var;
typedef ObjOut< CCMHome > CCMHome_out;

class KeylessCCMHome;
typedef KeylessCCMHome *KeylessCCMHome_ptr;
typedef KeylessCCMHome_ptr KeylessCCMHomeRef;
typedef ObjVar< KeylessCCMHome > KeylessCCMHome_var;
typedef ObjOut< KeylessCCMHome > KeylessCCMHome_out;

class HomeFinder;
typedef HomeFinder *HomeFinder_ptr;
typedef HomeFinder_ptr HomeFinderRef;
typedef ObjVar< HomeFinder > HomeFinder_var;
typedef ObjOut< HomeFinder > HomeFinder_out;

}
}
}






namespace ccmtools
{
namespace corba
{
namespace Components
{

typedef char* FeatureName;
typedef CORBA::String_var FeatureName_var;
typedef CORBA::String_out FeatureName_out;

struct InvalidName : public CORBA::UserException {
  #ifdef HAVE_EXPLICIT_STRUCT_OPS
  InvalidName();
  ~InvalidName();
  InvalidName( const InvalidName& s );
  InvalidName& operator=( const InvalidName& s );
  #endif //HAVE_EXPLICIT_STRUCT_OPS


  #ifdef HAVE_STD_EH
  InvalidName *operator->() { return this; }
  InvalidName& operator*() { return *this; }
  operator InvalidName*() { return this; }
  #endif // HAVE_STD_EH

  void _throwit() const;
  const char *_repoid() const;
  void _encode( CORBA::DataEncoder &en ) const;
  void _encode_any( CORBA::Any &a ) const;
  CORBA::Exception *_clone() const;
  static InvalidName *_downcast( CORBA::Exception *ex );
  static const InvalidName *_downcast( const CORBA::Exception *ex );
};

#ifdef HAVE_STD_EH
typedef InvalidName InvalidName_catch;
#else
typedef ExceptVar< InvalidName > InvalidName_var;
typedef TVarOut< InvalidName > InvalidName_out;
typedef InvalidName_var InvalidName_catch;
#endif // HAVE_STD_EH


/*
 * Base class and common definitions for interface Navigation
 */

class Navigation : 
  virtual public CORBA::Object
{
  public:
    virtual ~Navigation();

    #ifdef HAVE_TYPEDEF_OVERLOAD
    typedef Navigation_ptr _ptr_type;
    typedef Navigation_var _var_type;
    #endif

    static Navigation_ptr _narrow( CORBA::Object_ptr obj );
    static Navigation_ptr _narrow( CORBA::AbstractBase_ptr obj );
    static Navigation_ptr _duplicate( Navigation_ptr _obj )
    {
      CORBA::Object::_duplicate (_obj);
      return _obj;
    }

    static Navigation_ptr _nil()
    {
      return 0;
    }

    virtual void *_narrow_helper( const char *repoid );

    virtual CORBA::Object_ptr provide_facet( const char* name ) = 0;

  protected:
    Navigation() {};
  private:
    Navigation( const Navigation& );
    void operator=( const Navigation& );
};

// Stub for interface Navigation
class Navigation_stub:
  virtual public Navigation
{
  public:
    virtual ~Navigation_stub();
    CORBA::Object_ptr provide_facet( const char* name );

  private:
    void operator=( const Navigation_stub& );
};

#ifndef MICO_CONF_NO_POA

class Navigation_stub_clp :
  virtual public Navigation_stub,
  virtual public PortableServer::StubBase
{
  public:
    Navigation_stub_clp (PortableServer::POA_ptr, CORBA::Object_ptr);
    virtual ~Navigation_stub_clp ();
    CORBA::Object_ptr provide_facet( const char* name );

  protected:
    Navigation_stub_clp ();
  private:
    void operator=( const Navigation_stub_clp & );
};

#endif // MICO_CONF_NO_POA

struct InvalidConnection : public CORBA::UserException {
  #ifdef HAVE_EXPLICIT_STRUCT_OPS
  InvalidConnection();
  ~InvalidConnection();
  InvalidConnection( const InvalidConnection& s );
  InvalidConnection& operator=( const InvalidConnection& s );
  #endif //HAVE_EXPLICIT_STRUCT_OPS


  #ifdef HAVE_STD_EH
  InvalidConnection *operator->() { return this; }
  InvalidConnection& operator*() { return *this; }
  operator InvalidConnection*() { return this; }
  #endif // HAVE_STD_EH

  void _throwit() const;
  const char *_repoid() const;
  void _encode( CORBA::DataEncoder &en ) const;
  void _encode_any( CORBA::Any &a ) const;
  CORBA::Exception *_clone() const;
  static InvalidConnection *_downcast( CORBA::Exception *ex );
  static const InvalidConnection *_downcast( const CORBA::Exception *ex );
};

#ifdef HAVE_STD_EH
typedef InvalidConnection InvalidConnection_catch;
#else
typedef ExceptVar< InvalidConnection > InvalidConnection_var;
typedef TVarOut< InvalidConnection > InvalidConnection_out;
typedef InvalidConnection_var InvalidConnection_catch;
#endif // HAVE_STD_EH

struct AlreadyConnected : public CORBA::UserException {
  #ifdef HAVE_EXPLICIT_STRUCT_OPS
  AlreadyConnected();
  ~AlreadyConnected();
  AlreadyConnected( const AlreadyConnected& s );
  AlreadyConnected& operator=( const AlreadyConnected& s );
  #endif //HAVE_EXPLICIT_STRUCT_OPS


  #ifdef HAVE_STD_EH
  AlreadyConnected *operator->() { return this; }
  AlreadyConnected& operator*() { return *this; }
  operator AlreadyConnected*() { return this; }
  #endif // HAVE_STD_EH

  void _throwit() const;
  const char *_repoid() const;
  void _encode( CORBA::DataEncoder &en ) const;
  void _encode_any( CORBA::Any &a ) const;
  CORBA::Exception *_clone() const;
  static AlreadyConnected *_downcast( CORBA::Exception *ex );
  static const AlreadyConnected *_downcast( const CORBA::Exception *ex );
};

#ifdef HAVE_STD_EH
typedef AlreadyConnected AlreadyConnected_catch;
#else
typedef ExceptVar< AlreadyConnected > AlreadyConnected_var;
typedef TVarOut< AlreadyConnected > AlreadyConnected_out;
typedef AlreadyConnected_var AlreadyConnected_catch;
#endif // HAVE_STD_EH

struct ExceededConnectionLimit : public CORBA::UserException {
  #ifdef HAVE_EXPLICIT_STRUCT_OPS
  ExceededConnectionLimit();
  ~ExceededConnectionLimit();
  ExceededConnectionLimit( const ExceededConnectionLimit& s );
  ExceededConnectionLimit& operator=( const ExceededConnectionLimit& s );
  #endif //HAVE_EXPLICIT_STRUCT_OPS


  #ifdef HAVE_STD_EH
  ExceededConnectionLimit *operator->() { return this; }
  ExceededConnectionLimit& operator*() { return *this; }
  operator ExceededConnectionLimit*() { return this; }
  #endif // HAVE_STD_EH

  void _throwit() const;
  const char *_repoid() const;
  void _encode( CORBA::DataEncoder &en ) const;
  void _encode_any( CORBA::Any &a ) const;
  CORBA::Exception *_clone() const;
  static ExceededConnectionLimit *_downcast( CORBA::Exception *ex );
  static const ExceededConnectionLimit *_downcast( const CORBA::Exception *ex );
};

#ifdef HAVE_STD_EH
typedef ExceededConnectionLimit ExceededConnectionLimit_catch;
#else
typedef ExceptVar< ExceededConnectionLimit > ExceededConnectionLimit_var;
typedef TVarOut< ExceededConnectionLimit > ExceededConnectionLimit_out;
typedef ExceededConnectionLimit_var ExceededConnectionLimit_catch;
#endif // HAVE_STD_EH

struct CookieRequired : public CORBA::UserException {
  #ifdef HAVE_EXPLICIT_STRUCT_OPS
  CookieRequired();
  ~CookieRequired();
  CookieRequired( const CookieRequired& s );
  CookieRequired& operator=( const CookieRequired& s );
  #endif //HAVE_EXPLICIT_STRUCT_OPS


  #ifdef HAVE_STD_EH
  CookieRequired *operator->() { return this; }
  CookieRequired& operator*() { return *this; }
  operator CookieRequired*() { return this; }
  #endif // HAVE_STD_EH

  void _throwit() const;
  const char *_repoid() const;
  void _encode( CORBA::DataEncoder &en ) const;
  void _encode_any( CORBA::Any &a ) const;
  CORBA::Exception *_clone() const;
  static CookieRequired *_downcast( CORBA::Exception *ex );
  static const CookieRequired *_downcast( const CORBA::Exception *ex );
};

#ifdef HAVE_STD_EH
typedef CookieRequired CookieRequired_catch;
#else
typedef ExceptVar< CookieRequired > CookieRequired_var;
typedef TVarOut< CookieRequired > CookieRequired_out;
typedef CookieRequired_var CookieRequired_catch;
#endif // HAVE_STD_EH

struct NoConnection : public CORBA::UserException {
  #ifdef HAVE_EXPLICIT_STRUCT_OPS
  NoConnection();
  ~NoConnection();
  NoConnection( const NoConnection& s );
  NoConnection& operator=( const NoConnection& s );
  #endif //HAVE_EXPLICIT_STRUCT_OPS


  #ifdef HAVE_STD_EH
  NoConnection *operator->() { return this; }
  NoConnection& operator*() { return *this; }
  operator NoConnection*() { return this; }
  #endif // HAVE_STD_EH

  void _throwit() const;
  const char *_repoid() const;
  void _encode( CORBA::DataEncoder &en ) const;
  void _encode_any( CORBA::Any &a ) const;
  CORBA::Exception *_clone() const;
  static NoConnection *_downcast( CORBA::Exception *ex );
  static const NoConnection *_downcast( const CORBA::Exception *ex );
};

#ifdef HAVE_STD_EH
typedef NoConnection NoConnection_catch;
#else
typedef ExceptVar< NoConnection > NoConnection_var;
typedef TVarOut< NoConnection > NoConnection_out;
typedef NoConnection_var NoConnection_catch;
#endif // HAVE_STD_EH

class Cookie;
typedef Cookie *Cookie_ptr;
typedef Cookie_ptr CookieRef;
typedef ValueVar< Cookie > Cookie_var;
typedef ValueOut< Cookie > Cookie_out;


// Common definitions for valuetype Cookie
class Cookie : 
  virtual public CORBA::ValueBase
{
  public:
    static Cookie* _downcast (CORBA::ValueBase *);
    static Cookie* _downcast (CORBA::AbstractBase *);

    typedef SequenceTmpl< CORBA::Octet,MICO_TID_OCTET> _CookieValue_seq;
    virtual void CookieValue( const _CookieValue_seq & _p) = 0;
    virtual const _CookieValue_seq & CookieValue() const = 0;
    virtual _CookieValue_seq & CookieValue() = 0;


  public:
    CORBA::ValueBase * _copy_value ();
    CORBA::ValueDef_ptr get_value_def ();
    virtual void * _narrow_helper (const char *);
    void _get_marshal_info (std::vector<std::string> &, CORBA::Boolean &);
    void _marshal_members (CORBA::DataEncoder &);
    CORBA::Boolean _demarshal_members (CORBA::DataDecoder &);

  protected:
    Cookie ();
    virtual ~Cookie ();
    void _copy_members (const Cookie&);

  private:
    Cookie (const Cookie &);
    void operator= (const Cookie &);
};


/*
 * Base class and common definitions for interface Receptacles
 */

class Receptacles : 
  virtual public CORBA::Object
{
  public:
    virtual ~Receptacles();

    #ifdef HAVE_TYPEDEF_OVERLOAD
    typedef Receptacles_ptr _ptr_type;
    typedef Receptacles_var _var_type;
    #endif

    static Receptacles_ptr _narrow( CORBA::Object_ptr obj );
    static Receptacles_ptr _narrow( CORBA::AbstractBase_ptr obj );
    static Receptacles_ptr _duplicate( Receptacles_ptr _obj )
    {
      CORBA::Object::_duplicate (_obj);
      return _obj;
    }

    static Receptacles_ptr _nil()
    {
      return 0;
    }

    virtual void *_narrow_helper( const char *repoid );

    virtual Cookie* connect( const char* name, CORBA::Object_ptr connection ) = 0;
    virtual void disconnect( const char* name, Cookie* ck ) = 0;

  protected:
    Receptacles() {};
  private:
    Receptacles( const Receptacles& );
    void operator=( const Receptacles& );
};

// Stub for interface Receptacles
class Receptacles_stub:
  virtual public Receptacles
{
  public:
    virtual ~Receptacles_stub();
    Cookie* connect( const char* name, CORBA::Object_ptr connection );
    void disconnect( const char* name, Cookie* ck );

  private:
    void operator=( const Receptacles_stub& );
};

#ifndef MICO_CONF_NO_POA

class Receptacles_stub_clp :
  virtual public Receptacles_stub,
  virtual public PortableServer::StubBase
{
  public:
    Receptacles_stub_clp (PortableServer::POA_ptr, CORBA::Object_ptr);
    virtual ~Receptacles_stub_clp ();
    Cookie* connect( const char* name, CORBA::Object_ptr connection );
    void disconnect( const char* name, Cookie* ck );

  protected:
    Receptacles_stub_clp ();
  private:
    void operator=( const Receptacles_stub_clp & );
};

#endif // MICO_CONF_NO_POA

struct InvalidConfiguration : public CORBA::UserException {
  #ifdef HAVE_EXPLICIT_STRUCT_OPS
  InvalidConfiguration();
  ~InvalidConfiguration();
  InvalidConfiguration( const InvalidConfiguration& s );
  InvalidConfiguration& operator=( const InvalidConfiguration& s );
  #endif //HAVE_EXPLICIT_STRUCT_OPS


  #ifdef HAVE_STD_EH
  InvalidConfiguration *operator->() { return this; }
  InvalidConfiguration& operator*() { return *this; }
  operator InvalidConfiguration*() { return this; }
  #endif // HAVE_STD_EH

  void _throwit() const;
  const char *_repoid() const;
  void _encode( CORBA::DataEncoder &en ) const;
  void _encode_any( CORBA::Any &a ) const;
  CORBA::Exception *_clone() const;
  static InvalidConfiguration *_downcast( CORBA::Exception *ex );
  static const InvalidConfiguration *_downcast( const CORBA::Exception *ex );
};

#ifdef HAVE_STD_EH
typedef InvalidConfiguration InvalidConfiguration_catch;
#else
typedef ExceptVar< InvalidConfiguration > InvalidConfiguration_var;
typedef TVarOut< InvalidConfiguration > InvalidConfiguration_out;
typedef InvalidConfiguration_var InvalidConfiguration_catch;
#endif // HAVE_STD_EH

struct RemoveFailure : public CORBA::UserException {
  #ifdef HAVE_EXPLICIT_STRUCT_OPS
  RemoveFailure();
  ~RemoveFailure();
  RemoveFailure( const RemoveFailure& s );
  RemoveFailure& operator=( const RemoveFailure& s );
  #endif //HAVE_EXPLICIT_STRUCT_OPS


  #ifdef HAVE_STD_EH
  RemoveFailure *operator->() { return this; }
  RemoveFailure& operator*() { return *this; }
  operator RemoveFailure*() { return this; }
  #endif // HAVE_STD_EH

  void _throwit() const;
  const char *_repoid() const;
  void _encode( CORBA::DataEncoder &en ) const;
  void _encode_any( CORBA::Any &a ) const;
  CORBA::Exception *_clone() const;
  static RemoveFailure *_downcast( CORBA::Exception *ex );
  static const RemoveFailure *_downcast( const CORBA::Exception *ex );
};

#ifdef HAVE_STD_EH
typedef RemoveFailure RemoveFailure_catch;
#else
typedef ExceptVar< RemoveFailure > RemoveFailure_var;
typedef TVarOut< RemoveFailure > RemoveFailure_out;
typedef RemoveFailure_var RemoveFailure_catch;
#endif // HAVE_STD_EH


/*
 * Base class and common definitions for interface CCMHome
 */

class CCMHome : 
  virtual public CORBA::Object
{
  public:
    virtual ~CCMHome();

    #ifdef HAVE_TYPEDEF_OVERLOAD
    typedef CCMHome_ptr _ptr_type;
    typedef CCMHome_var _var_type;
    #endif

    static CCMHome_ptr _narrow( CORBA::Object_ptr obj );
    static CCMHome_ptr _narrow( CORBA::AbstractBase_ptr obj );
    static CCMHome_ptr _duplicate( CCMHome_ptr _obj )
    {
      CORBA::Object::_duplicate (_obj);
      return _obj;
    }

    static CCMHome_ptr _nil()
    {
      return 0;
    }

    virtual void *_narrow_helper( const char *repoid );

    virtual void remove_component( CCMObject_ptr comp ) = 0;

  protected:
    CCMHome() {};
  private:
    CCMHome( const CCMHome& );
    void operator=( const CCMHome& );
};

// Stub for interface CCMHome
class CCMHome_stub:
  virtual public CCMHome
{
  public:
    virtual ~CCMHome_stub();
    void remove_component( CCMObject_ptr comp );

  private:
    void operator=( const CCMHome_stub& );
};

#ifndef MICO_CONF_NO_POA

class CCMHome_stub_clp :
  virtual public CCMHome_stub,
  virtual public PortableServer::StubBase
{
  public:
    CCMHome_stub_clp (PortableServer::POA_ptr, CORBA::Object_ptr);
    virtual ~CCMHome_stub_clp ();
    void remove_component( CCMObject_ptr comp );

  protected:
    CCMHome_stub_clp ();
  private:
    void operator=( const CCMHome_stub_clp & );
};

#endif // MICO_CONF_NO_POA


/*
 * Base class and common definitions for interface CCMObject
 */

class CCMObject : 
  virtual public ::ccmtools::corba::Components::Navigation,
  virtual public ::ccmtools::corba::Components::Receptacles
{
  public:
    virtual ~CCMObject();

    #ifdef HAVE_TYPEDEF_OVERLOAD
    typedef CCMObject_ptr _ptr_type;
    typedef CCMObject_var _var_type;
    #endif

    static CCMObject_ptr _narrow( CORBA::Object_ptr obj );
    static CCMObject_ptr _narrow( CORBA::AbstractBase_ptr obj );
    static CCMObject_ptr _duplicate( CCMObject_ptr _obj )
    {
      CORBA::Object::_duplicate (_obj);
      return _obj;
    }

    static CCMObject_ptr _nil()
    {
      return 0;
    }

    virtual void *_narrow_helper( const char *repoid );

    virtual CCMHome_ptr get_ccm_home() = 0;
    virtual void configuration_complete() = 0;
    virtual void remove() = 0;

  protected:
    CCMObject() {};
  private:
    CCMObject( const CCMObject& );
    void operator=( const CCMObject& );
};

// Stub for interface CCMObject
class CCMObject_stub:
  virtual public CCMObject,
  virtual public ::ccmtools::corba::Components::Navigation_stub,
  virtual public ::ccmtools::corba::Components::Receptacles_stub
{
  public:
    virtual ~CCMObject_stub();
    CCMHome_ptr get_ccm_home();
    void configuration_complete();
    void remove();

  private:
    void operator=( const CCMObject_stub& );
};

#ifndef MICO_CONF_NO_POA

class CCMObject_stub_clp :
  virtual public CCMObject_stub,
  virtual public ::ccmtools::corba::Components::Navigation_stub_clp,
  virtual public ::ccmtools::corba::Components::Receptacles_stub_clp
{
  public:
    CCMObject_stub_clp (PortableServer::POA_ptr, CORBA::Object_ptr);
    virtual ~CCMObject_stub_clp ();
    CCMHome_ptr get_ccm_home();
    void configuration_complete();
    void remove();

  protected:
    CCMObject_stub_clp ();
  private:
    void operator=( const CCMObject_stub_clp & );
};

#endif // MICO_CONF_NO_POA

typedef CORBA::ULong FailureReason;
typedef FailureReason& FailureReason_out;
struct CreateFailure : public CORBA::UserException {
  #ifdef HAVE_EXPLICIT_STRUCT_OPS
  CreateFailure();
  ~CreateFailure();
  CreateFailure( const CreateFailure& s );
  CreateFailure& operator=( const CreateFailure& s );
  #endif //HAVE_EXPLICIT_STRUCT_OPS

  #ifndef HAVE_EXPLICIT_STRUCT_OPS
  CreateFailure();
  #endif //HAVE_EXPLICIT_STRUCT_OPS
  CreateFailure( ::ccmtools::corba::Components::FailureReason _m0 );

  #ifdef HAVE_STD_EH
  CreateFailure *operator->() { return this; }
  CreateFailure& operator*() { return *this; }
  operator CreateFailure*() { return this; }
  #endif // HAVE_STD_EH

  void _throwit() const;
  const char *_repoid() const;
  void _encode( CORBA::DataEncoder &en ) const;
  void _encode_any( CORBA::Any &a ) const;
  CORBA::Exception *_clone() const;
  static CreateFailure *_downcast( CORBA::Exception *ex );
  static const CreateFailure *_downcast( const CORBA::Exception *ex );
  FailureReason reason;
};

#ifdef HAVE_STD_EH
typedef CreateFailure CreateFailure_catch;
#else
typedef ExceptVar< CreateFailure > CreateFailure_var;
typedef TVarOut< CreateFailure > CreateFailure_out;
typedef CreateFailure_var CreateFailure_catch;
#endif // HAVE_STD_EH

typedef IfaceSequenceTmpl< CCMHome_var,CCMHome_ptr> CCMHomes;
typedef TSeqVar< IfaceSequenceTmpl< CCMHome_var,CCMHome_ptr> > CCMHomes_var;
typedef TSeqOut< IfaceSequenceTmpl< CCMHome_var,CCMHome_ptr> > CCMHomes_out;


/*
 * Base class and common definitions for interface KeylessCCMHome
 */

class KeylessCCMHome : 
  virtual public CORBA::Object
{
  public:
    virtual ~KeylessCCMHome();

    #ifdef HAVE_TYPEDEF_OVERLOAD
    typedef KeylessCCMHome_ptr _ptr_type;
    typedef KeylessCCMHome_var _var_type;
    #endif

    static KeylessCCMHome_ptr _narrow( CORBA::Object_ptr obj );
    static KeylessCCMHome_ptr _narrow( CORBA::AbstractBase_ptr obj );
    static KeylessCCMHome_ptr _duplicate( KeylessCCMHome_ptr _obj )
    {
      CORBA::Object::_duplicate (_obj);
      return _obj;
    }

    static KeylessCCMHome_ptr _nil()
    {
      return 0;
    }

    virtual void *_narrow_helper( const char *repoid );

    virtual CCMObject_ptr create_component() = 0;

  protected:
    KeylessCCMHome() {};
  private:
    KeylessCCMHome( const KeylessCCMHome& );
    void operator=( const KeylessCCMHome& );
};

// Stub for interface KeylessCCMHome
class KeylessCCMHome_stub:
  virtual public KeylessCCMHome
{
  public:
    virtual ~KeylessCCMHome_stub();
    CCMObject_ptr create_component();

  private:
    void operator=( const KeylessCCMHome_stub& );
};

#ifndef MICO_CONF_NO_POA

class KeylessCCMHome_stub_clp :
  virtual public KeylessCCMHome_stub,
  virtual public PortableServer::StubBase
{
  public:
    KeylessCCMHome_stub_clp (PortableServer::POA_ptr, CORBA::Object_ptr);
    virtual ~KeylessCCMHome_stub_clp ();
    CCMObject_ptr create_component();

  protected:
    KeylessCCMHome_stub_clp ();
  private:
    void operator=( const KeylessCCMHome_stub_clp & );
};

#endif // MICO_CONF_NO_POA

struct HomeNotFound : public CORBA::UserException {
  #ifdef HAVE_EXPLICIT_STRUCT_OPS
  HomeNotFound();
  ~HomeNotFound();
  HomeNotFound( const HomeNotFound& s );
  HomeNotFound& operator=( const HomeNotFound& s );
  #endif //HAVE_EXPLICIT_STRUCT_OPS


  #ifdef HAVE_STD_EH
  HomeNotFound *operator->() { return this; }
  HomeNotFound& operator*() { return *this; }
  operator HomeNotFound*() { return this; }
  #endif // HAVE_STD_EH

  void _throwit() const;
  const char *_repoid() const;
  void _encode( CORBA::DataEncoder &en ) const;
  void _encode_any( CORBA::Any &a ) const;
  CORBA::Exception *_clone() const;
  static HomeNotFound *_downcast( CORBA::Exception *ex );
  static const HomeNotFound *_downcast( const CORBA::Exception *ex );
};

#ifdef HAVE_STD_EH
typedef HomeNotFound HomeNotFound_catch;
#else
typedef ExceptVar< HomeNotFound > HomeNotFound_var;
typedef TVarOut< HomeNotFound > HomeNotFound_out;
typedef HomeNotFound_var HomeNotFound_catch;
#endif // HAVE_STD_EH


/*
 * Base class and common definitions for interface HomeFinder
 */

class HomeFinder : 
  virtual public CORBA::Object
{
  public:
    virtual ~HomeFinder();

    #ifdef HAVE_TYPEDEF_OVERLOAD
    typedef HomeFinder_ptr _ptr_type;
    typedef HomeFinder_var _var_type;
    #endif

    static HomeFinder_ptr _narrow( CORBA::Object_ptr obj );
    static HomeFinder_ptr _narrow( CORBA::AbstractBase_ptr obj );
    static HomeFinder_ptr _duplicate( HomeFinder_ptr _obj )
    {
      CORBA::Object::_duplicate (_obj);
      return _obj;
    }

    static HomeFinder_ptr _nil()
    {
      return 0;
    }

    virtual void *_narrow_helper( const char *repoid );

    virtual CCMHome_ptr find_home_by_component_type( const char* comp_repid ) = 0;
    virtual CCMHome_ptr find_home_by_home_type( const char* home_repid ) = 0;
    virtual CCMHome_ptr find_home_by_name( const char* home_name ) = 0;

  protected:
    HomeFinder() {};
  private:
    HomeFinder( const HomeFinder& );
    void operator=( const HomeFinder& );
};

// Stub for interface HomeFinder
class HomeFinder_stub:
  virtual public HomeFinder
{
  public:
    virtual ~HomeFinder_stub();
    CCMHome_ptr find_home_by_component_type( const char* comp_repid );
    CCMHome_ptr find_home_by_home_type( const char* home_repid );
    CCMHome_ptr find_home_by_name( const char* home_name );

  private:
    void operator=( const HomeFinder_stub& );
};

#ifndef MICO_CONF_NO_POA

class HomeFinder_stub_clp :
  virtual public HomeFinder_stub,
  virtual public PortableServer::StubBase
{
  public:
    HomeFinder_stub_clp (PortableServer::POA_ptr, CORBA::Object_ptr);
    virtual ~HomeFinder_stub_clp ();
    CCMHome_ptr find_home_by_component_type( const char* comp_repid );
    CCMHome_ptr find_home_by_home_type( const char* home_repid );
    CCMHome_ptr find_home_by_name( const char* home_name );

  protected:
    HomeFinder_stub_clp ();
  private:
    void operator=( const HomeFinder_stub_clp & );
};

#endif // MICO_CONF_NO_POA

}
}
}


#ifndef MICO_CONF_NO_POA



namespace POA_ccmtools
{
namespace corba
{
namespace Components
{

class Navigation : virtual public PortableServer::StaticImplementation
{
  public:
    virtual ~Navigation ();
    ccmtools::corba::Components::Navigation_ptr _this ();
    bool dispatch (CORBA::StaticServerRequest_ptr);
    virtual void invoke (CORBA::StaticServerRequest_ptr);
    virtual CORBA::Boolean _is_a (const char *);
    virtual CORBA::InterfaceDef_ptr _get_interface ();
    virtual CORBA::RepositoryId _primary_interface (const PortableServer::ObjectId &, PortableServer::POA_ptr);

    virtual void * _narrow_helper (const char *);
    static Navigation * _narrow (PortableServer::Servant);
    virtual CORBA::Object_ptr _make_stub (PortableServer::POA_ptr, CORBA::Object_ptr);

    virtual CORBA::Object_ptr provide_facet( const char* name ) = 0;

  protected:
    Navigation () {};

  private:
    Navigation (const Navigation &);
    void operator= (const Navigation &);
};

class Receptacles : virtual public PortableServer::StaticImplementation
{
  public:
    virtual ~Receptacles ();
    ccmtools::corba::Components::Receptacles_ptr _this ();
    bool dispatch (CORBA::StaticServerRequest_ptr);
    virtual void invoke (CORBA::StaticServerRequest_ptr);
    virtual CORBA::Boolean _is_a (const char *);
    virtual CORBA::InterfaceDef_ptr _get_interface ();
    virtual CORBA::RepositoryId _primary_interface (const PortableServer::ObjectId &, PortableServer::POA_ptr);

    virtual void * _narrow_helper (const char *);
    static Receptacles * _narrow (PortableServer::Servant);
    virtual CORBA::Object_ptr _make_stub (PortableServer::POA_ptr, CORBA::Object_ptr);

    virtual ::ccmtools::corba::Components::Cookie* connect( const char* name, CORBA::Object_ptr connection ) = 0;
    virtual void disconnect( const char* name, ::ccmtools::corba::Components::Cookie* ck ) = 0;

  protected:
    Receptacles () {};

  private:
    Receptacles (const Receptacles &);
    void operator= (const Receptacles &);
};

class CCMHome : virtual public PortableServer::StaticImplementation
{
  public:
    virtual ~CCMHome ();
    ccmtools::corba::Components::CCMHome_ptr _this ();
    bool dispatch (CORBA::StaticServerRequest_ptr);
    virtual void invoke (CORBA::StaticServerRequest_ptr);
    virtual CORBA::Boolean _is_a (const char *);
    virtual CORBA::InterfaceDef_ptr _get_interface ();
    virtual CORBA::RepositoryId _primary_interface (const PortableServer::ObjectId &, PortableServer::POA_ptr);

    virtual void * _narrow_helper (const char *);
    static CCMHome * _narrow (PortableServer::Servant);
    virtual CORBA::Object_ptr _make_stub (PortableServer::POA_ptr, CORBA::Object_ptr);

    virtual void remove_component( ::ccmtools::corba::Components::CCMObject_ptr comp ) = 0;

  protected:
    CCMHome () {};

  private:
    CCMHome (const CCMHome &);
    void operator= (const CCMHome &);
};

class CCMObject : 
  virtual public POA_ccmtools::corba::Components::Navigation,
  virtual public POA_ccmtools::corba::Components::Receptacles
{
  public:
    virtual ~CCMObject ();
    ccmtools::corba::Components::CCMObject_ptr _this ();
    bool dispatch (CORBA::StaticServerRequest_ptr);
    virtual void invoke (CORBA::StaticServerRequest_ptr);
    virtual CORBA::Boolean _is_a (const char *);
    virtual CORBA::InterfaceDef_ptr _get_interface ();
    virtual CORBA::RepositoryId _primary_interface (const PortableServer::ObjectId &, PortableServer::POA_ptr);

    virtual void * _narrow_helper (const char *);
    static CCMObject * _narrow (PortableServer::Servant);
    virtual CORBA::Object_ptr _make_stub (PortableServer::POA_ptr, CORBA::Object_ptr);

    virtual ::ccmtools::corba::Components::CCMHome_ptr get_ccm_home() = 0;
    virtual void configuration_complete() = 0;
    virtual void remove() = 0;

  protected:
    CCMObject () {};

  private:
    CCMObject (const CCMObject &);
    void operator= (const CCMObject &);
};

class KeylessCCMHome : virtual public PortableServer::StaticImplementation
{
  public:
    virtual ~KeylessCCMHome ();
    ccmtools::corba::Components::KeylessCCMHome_ptr _this ();
    bool dispatch (CORBA::StaticServerRequest_ptr);
    virtual void invoke (CORBA::StaticServerRequest_ptr);
    virtual CORBA::Boolean _is_a (const char *);
    virtual CORBA::InterfaceDef_ptr _get_interface ();
    virtual CORBA::RepositoryId _primary_interface (const PortableServer::ObjectId &, PortableServer::POA_ptr);

    virtual void * _narrow_helper (const char *);
    static KeylessCCMHome * _narrow (PortableServer::Servant);
    virtual CORBA::Object_ptr _make_stub (PortableServer::POA_ptr, CORBA::Object_ptr);

    virtual ::ccmtools::corba::Components::CCMObject_ptr create_component() = 0;

  protected:
    KeylessCCMHome () {};

  private:
    KeylessCCMHome (const KeylessCCMHome &);
    void operator= (const KeylessCCMHome &);
};

class HomeFinder : virtual public PortableServer::StaticImplementation
{
  public:
    virtual ~HomeFinder ();
    ccmtools::corba::Components::HomeFinder_ptr _this ();
    bool dispatch (CORBA::StaticServerRequest_ptr);
    virtual void invoke (CORBA::StaticServerRequest_ptr);
    virtual CORBA::Boolean _is_a (const char *);
    virtual CORBA::InterfaceDef_ptr _get_interface ();
    virtual CORBA::RepositoryId _primary_interface (const PortableServer::ObjectId &, PortableServer::POA_ptr);

    virtual void * _narrow_helper (const char *);
    static HomeFinder * _narrow (PortableServer::Servant);
    virtual CORBA::Object_ptr _make_stub (PortableServer::POA_ptr, CORBA::Object_ptr);

    virtual ::ccmtools::corba::Components::CCMHome_ptr find_home_by_component_type( const char* comp_repid ) = 0;
    virtual ::ccmtools::corba::Components::CCMHome_ptr find_home_by_home_type( const char* home_repid ) = 0;
    virtual ::ccmtools::corba::Components::CCMHome_ptr find_home_by_name( const char* home_name ) = 0;

  protected:
    HomeFinder () {};

  private:
    HomeFinder (const HomeFinder &);
    void operator= (const HomeFinder &);
};

}
}
}


#endif // MICO_CONF_NO_POA



namespace OBV_ccmtools
{
namespace corba
{
namespace Components
{


// OBV class for valuetype Cookie
class Cookie : virtual public ccmtools::corba::Components::Cookie
{
  public:
    Cookie ();
    Cookie (const SequenceTmpl< CORBA::Octet,MICO_TID_OCTET>& _CookieValue);
    virtual ~Cookie();

  public:
    typedef SequenceTmpl< CORBA::Octet,MICO_TID_OCTET> _CookieValue_seq;
    void CookieValue( const _CookieValue_seq & _p);
    const _CookieValue_seq & CookieValue() const;
    _CookieValue_seq & CookieValue();


  private:
    struct _M {
      SequenceTmpl< CORBA::Octet,MICO_TID_OCTET> CookieValue;
    } _m;
};

}
}
}


extern CORBA::StaticTypeInfo *_marshaller_ccmtools_corba_Components_InvalidName;

extern CORBA::StaticTypeInfo *_marshaller_ccmtools_corba_Components_Navigation;

extern CORBA::StaticTypeInfo *_marshaller_ccmtools_corba_Components_InvalidConnection;

extern CORBA::StaticTypeInfo *_marshaller_ccmtools_corba_Components_AlreadyConnected;

extern CORBA::StaticTypeInfo *_marshaller_ccmtools_corba_Components_ExceededConnectionLimit;

extern CORBA::StaticTypeInfo *_marshaller_ccmtools_corba_Components_CookieRequired;

extern CORBA::StaticTypeInfo *_marshaller_ccmtools_corba_Components_NoConnection;

extern CORBA::StaticTypeInfo *_marshaller_ccmtools_corba_Components_Cookie;

extern CORBA::StaticTypeInfo *_marshaller_ccmtools_corba_Components_Receptacles;

extern CORBA::StaticTypeInfo *_marshaller_ccmtools_corba_Components_InvalidConfiguration;

extern CORBA::StaticTypeInfo *_marshaller_ccmtools_corba_Components_RemoveFailure;

extern CORBA::StaticTypeInfo *_marshaller_ccmtools_corba_Components_CCMHome;

extern CORBA::StaticTypeInfo *_marshaller_ccmtools_corba_Components_CCMObject;

extern CORBA::StaticTypeInfo *_marshaller_ccmtools_corba_Components_CreateFailure;

extern CORBA::StaticTypeInfo *_marshaller_ccmtools_corba_Components_KeylessCCMHome;

extern CORBA::StaticTypeInfo *_marshaller_ccmtools_corba_Components_HomeNotFound;

extern CORBA::StaticTypeInfo *_marshaller_ccmtools_corba_Components_HomeFinder;

extern CORBA::StaticTypeInfo *_marshaller__seq_ccmtools_corba_Components_CCMHome;

#endif
