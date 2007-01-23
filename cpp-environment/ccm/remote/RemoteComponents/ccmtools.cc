/*
 *  MICO --- an Open Source CORBA implementation
 *  Copyright (c) 1997-2006 by The Mico Team
 *
 *  This file was automatically generated. DO NOT EDIT!
 */

#include <ccmtools.h>


using namespace std;

//--------------------------------------------------------
//  Implementation of stubs
//--------------------------------------------------------


#ifdef HAVE_EXPLICIT_STRUCT_OPS
ccmtools::corba::Components::InvalidName::InvalidName()
{
}

ccmtools::corba::Components::InvalidName::InvalidName( const InvalidName& _s )
{
}

ccmtools::corba::Components::InvalidName::~InvalidName()
{
}

ccmtools::corba::Components::InvalidName&
ccmtools::corba::Components::InvalidName::operator=( const InvalidName& _s )
{
  return *this;
}
#endif

class _Marshaller_ccmtools_corba_Components_InvalidName : public ::CORBA::StaticTypeInfo {
    typedef ::ccmtools::corba::Components::InvalidName _MICO_T;
  public:
    ~_Marshaller_ccmtools_corba_Components_InvalidName();
    StaticValueType create () const;
    void assign (StaticValueType dst, const StaticValueType src) const;
    void free (StaticValueType) const;
    ::CORBA::Boolean demarshal (::CORBA::DataDecoder&, StaticValueType) const;
    void marshal (::CORBA::DataEncoder &, StaticValueType) const;
};


_Marshaller_ccmtools_corba_Components_InvalidName::~_Marshaller_ccmtools_corba_Components_InvalidName()
{
}

::CORBA::StaticValueType _Marshaller_ccmtools_corba_Components_InvalidName::create() const
{
  return (StaticValueType) new _MICO_T;
}

void _Marshaller_ccmtools_corba_Components_InvalidName::assign( StaticValueType d, const StaticValueType s ) const
{
  *(_MICO_T*) d = *(_MICO_T*) s;
}

void _Marshaller_ccmtools_corba_Components_InvalidName::free( StaticValueType v ) const
{
  delete (_MICO_T*) v;
}

::CORBA::Boolean _Marshaller_ccmtools_corba_Components_InvalidName::demarshal( ::CORBA::DataDecoder &dc, StaticValueType v ) const
{
  string repoid;
  return
    dc.except_begin( repoid ) &&
    dc.except_end();
}

void _Marshaller_ccmtools_corba_Components_InvalidName::marshal( ::CORBA::DataEncoder &ec, StaticValueType v ) const
{
  ec.except_begin( "IDL:ccmtools/corba/Components/InvalidName:1.0" );
  ec.except_end();
}

::CORBA::StaticTypeInfo *_marshaller_ccmtools_corba_Components_InvalidName;

void ccmtools::corba::Components::InvalidName::_throwit() const
{
  #ifdef HAVE_EXCEPTIONS
  #ifdef HAVE_STD_EH
  throw *this;
  #else
  throw InvalidName_var( (ccmtools::corba::Components::InvalidName*)_clone() );
  #endif
  #else
  CORBA::Exception::_throw_failed( _clone() );
  #endif
}

const char *ccmtools::corba::Components::InvalidName::_repoid() const
{
  return "IDL:ccmtools/corba/Components/InvalidName:1.0";
}

void ccmtools::corba::Components::InvalidName::_encode( CORBA::DataEncoder &_en ) const
{
  _marshaller_ccmtools_corba_Components_InvalidName->marshal( _en, (void*) this );
}

void ccmtools::corba::Components::InvalidName::_encode_any( CORBA::Any & ) const
{
  // use --any to make this work!
  assert(0);
}

CORBA::Exception *ccmtools::corba::Components::InvalidName::_clone() const
{
  return new InvalidName( *this );
}

ccmtools::corba::Components::InvalidName *ccmtools::corba::Components::InvalidName::_downcast( CORBA::Exception *_ex )
{
  if( _ex && !strcmp( _ex->_repoid(), "IDL:ccmtools/corba/Components/InvalidName:1.0" ) )
    return (InvalidName *) _ex;
  return NULL;
}

const ccmtools::corba::Components::InvalidName *ccmtools::corba::Components::InvalidName::_downcast( const CORBA::Exception *_ex )
{
  if( _ex && !strcmp( _ex->_repoid(), "IDL:ccmtools/corba/Components/InvalidName:1.0" ) )
    return (InvalidName *) _ex;
  return NULL;
}


/*
 * Base interface for class Navigation
 */

ccmtools::corba::Components::Navigation::~Navigation()
{
}

void *
ccmtools::corba::Components::Navigation::_narrow_helper( const char *_repoid )
{
  if( strcmp( _repoid, "IDL:ccmtools/corba/Components/Navigation:1.0" ) == 0 )
    return (void *)this;
  return NULL;
}

ccmtools::corba::Components::Navigation_ptr
ccmtools::corba::Components::Navigation::_narrow( CORBA::Object_ptr _obj )
{
  ccmtools::corba::Components::Navigation_ptr _o;
  if( !CORBA::is_nil( _obj ) ) {
    void *_p;
    if( (_p = _obj->_narrow_helper( "IDL:ccmtools/corba/Components/Navigation:1.0" )))
      return _duplicate( (ccmtools::corba::Components::Navigation_ptr) _p );
    if (!strcmp (_obj->_repoid(), "IDL:ccmtools/corba/Components/Navigation:1.0") || _obj->_is_a_remote ("IDL:ccmtools/corba/Components/Navigation:1.0")) {
      _o = new ccmtools::corba::Components::Navigation_stub;
      _o->CORBA::Object::operator=( *_obj );
      return _o;
    }
  }
  return _nil();
}

ccmtools::corba::Components::Navigation_ptr
ccmtools::corba::Components::Navigation::_narrow( CORBA::AbstractBase_ptr _obj )
{
  return _narrow (_obj->_to_object());
}

class _Marshaller_ccmtools_corba_Components_Navigation : public ::CORBA::StaticTypeInfo {
    typedef ccmtools::corba::Components::Navigation_ptr _MICO_T;
  public:
    ~_Marshaller_ccmtools_corba_Components_Navigation();
    StaticValueType create () const;
    void assign (StaticValueType dst, const StaticValueType src) const;
    void free (StaticValueType) const;
    void release (StaticValueType) const;
    ::CORBA::Boolean demarshal (::CORBA::DataDecoder&, StaticValueType) const;
    void marshal (::CORBA::DataEncoder &, StaticValueType) const;
};


_Marshaller_ccmtools_corba_Components_Navigation::~_Marshaller_ccmtools_corba_Components_Navigation()
{
}

::CORBA::StaticValueType _Marshaller_ccmtools_corba_Components_Navigation::create() const
{
  return (StaticValueType) new _MICO_T( 0 );
}

void _Marshaller_ccmtools_corba_Components_Navigation::assign( StaticValueType d, const StaticValueType s ) const
{
  *(_MICO_T*) d = ::ccmtools::corba::Components::Navigation::_duplicate( *(_MICO_T*) s );
}

void _Marshaller_ccmtools_corba_Components_Navigation::free( StaticValueType v ) const
{
  ::CORBA::release( *(_MICO_T *) v );
  delete (_MICO_T*) v;
}

void _Marshaller_ccmtools_corba_Components_Navigation::release( StaticValueType v ) const
{
  ::CORBA::release( *(_MICO_T *) v );
}

::CORBA::Boolean _Marshaller_ccmtools_corba_Components_Navigation::demarshal( ::CORBA::DataDecoder &dc, StaticValueType v ) const
{
  ::CORBA::Object_ptr obj;
  if (!::CORBA::_stc_Object->demarshal(dc, &obj))
    return FALSE;
  *(_MICO_T *) v = ::ccmtools::corba::Components::Navigation::_narrow( obj );
  ::CORBA::Boolean ret = ::CORBA::is_nil (obj) || !::CORBA::is_nil (*(_MICO_T *)v);
  ::CORBA::release (obj);
  return ret;
}

void _Marshaller_ccmtools_corba_Components_Navigation::marshal( ::CORBA::DataEncoder &ec, StaticValueType v ) const
{
  ::CORBA::Object_ptr obj = *(_MICO_T *) v;
  ::CORBA::_stc_Object->marshal( ec, &obj );
}

::CORBA::StaticTypeInfo *_marshaller_ccmtools_corba_Components_Navigation;


/*
 * Stub interface for class Navigation
 */

ccmtools::corba::Components::Navigation_stub::~Navigation_stub()
{
}

#ifndef MICO_CONF_NO_POA

void *
POA_ccmtools::corba::Components::Navigation::_narrow_helper (const char * repoid)
{
  if (strcmp (repoid, "IDL:ccmtools/corba/Components/Navigation:1.0") == 0) {
    return (void *) this;
  }
  return NULL;
}

POA_ccmtools::corba::Components::Navigation *
POA_ccmtools::corba::Components::Navigation::_narrow (PortableServer::Servant serv) 
{
  void * p;
  if ((p = serv->_narrow_helper ("IDL:ccmtools/corba/Components/Navigation:1.0")) != NULL) {
    serv->_add_ref ();
    return (POA_ccmtools::corba::Components::Navigation *) p;
  }
  return NULL;
}

ccmtools::corba::Components::Navigation_stub_clp::Navigation_stub_clp ()
{
}

ccmtools::corba::Components::Navigation_stub_clp::Navigation_stub_clp (PortableServer::POA_ptr poa, CORBA::Object_ptr obj)
  : CORBA::Object(*obj), PortableServer::StubBase(poa)
{
}

ccmtools::corba::Components::Navigation_stub_clp::~Navigation_stub_clp ()
{
}

#endif // MICO_CONF_NO_POA

CORBA::Object_ptr ccmtools::corba::Components::Navigation_stub::provide_facet( const char* _par_name )
{
  CORBA::StaticAny _sa_name( CORBA::_stc_string, &_par_name );
  CORBA::Object_ptr _res = CORBA::Object::_nil();
  CORBA::StaticAny __res( CORBA::_stc_Object, &_res );

  CORBA::StaticRequest __req( this, "provide_facet" );
  __req.add_in_arg( &_sa_name );
  __req.set_result( &__res );

  __req.invoke();

  mico_sii_throw( &__req, 
    _marshaller_ccmtools_corba_Components_InvalidName, "IDL:ccmtools/corba/Components/InvalidName:1.0",
    0);
  return _res;
}


#ifndef MICO_CONF_NO_POA

CORBA::Object_ptr
ccmtools::corba::Components::Navigation_stub_clp::provide_facet( const char* _par_name )
{
  PortableServer::Servant _serv = _preinvoke ();
  if (_serv) {
    POA_ccmtools::corba::Components::Navigation * _myserv = POA_ccmtools::corba::Components::Navigation::_narrow (_serv);
    if (_myserv) {
      CORBA::Object_ptr __res;

      #ifdef HAVE_EXCEPTIONS
      try {
      #endif
        __res = _myserv->provide_facet(_par_name);
      #ifdef HAVE_EXCEPTIONS
      }
      catch (...) {
        _myserv->_remove_ref();
        _postinvoke();
        throw;
      }
      #endif

      _myserv->_remove_ref();
      _postinvoke ();
      return __res;
    }
    _postinvoke ();
  }

  return ccmtools::corba::Components::Navigation_stub::provide_facet(_par_name);
}

#endif // MICO_CONF_NO_POA


#ifdef HAVE_EXPLICIT_STRUCT_OPS
ccmtools::corba::Components::InvalidConnection::InvalidConnection()
{
}

ccmtools::corba::Components::InvalidConnection::InvalidConnection( const InvalidConnection& _s )
{
}

ccmtools::corba::Components::InvalidConnection::~InvalidConnection()
{
}

ccmtools::corba::Components::InvalidConnection&
ccmtools::corba::Components::InvalidConnection::operator=( const InvalidConnection& _s )
{
  return *this;
}
#endif

class _Marshaller_ccmtools_corba_Components_InvalidConnection : public ::CORBA::StaticTypeInfo {
    typedef ::ccmtools::corba::Components::InvalidConnection _MICO_T;
  public:
    ~_Marshaller_ccmtools_corba_Components_InvalidConnection();
    StaticValueType create () const;
    void assign (StaticValueType dst, const StaticValueType src) const;
    void free (StaticValueType) const;
    ::CORBA::Boolean demarshal (::CORBA::DataDecoder&, StaticValueType) const;
    void marshal (::CORBA::DataEncoder &, StaticValueType) const;
};


_Marshaller_ccmtools_corba_Components_InvalidConnection::~_Marshaller_ccmtools_corba_Components_InvalidConnection()
{
}

::CORBA::StaticValueType _Marshaller_ccmtools_corba_Components_InvalidConnection::create() const
{
  return (StaticValueType) new _MICO_T;
}

void _Marshaller_ccmtools_corba_Components_InvalidConnection::assign( StaticValueType d, const StaticValueType s ) const
{
  *(_MICO_T*) d = *(_MICO_T*) s;
}

void _Marshaller_ccmtools_corba_Components_InvalidConnection::free( StaticValueType v ) const
{
  delete (_MICO_T*) v;
}

::CORBA::Boolean _Marshaller_ccmtools_corba_Components_InvalidConnection::demarshal( ::CORBA::DataDecoder &dc, StaticValueType v ) const
{
  string repoid;
  return
    dc.except_begin( repoid ) &&
    dc.except_end();
}

void _Marshaller_ccmtools_corba_Components_InvalidConnection::marshal( ::CORBA::DataEncoder &ec, StaticValueType v ) const
{
  ec.except_begin( "IDL:ccmtools/corba/Components/InvalidConnection:1.0" );
  ec.except_end();
}

::CORBA::StaticTypeInfo *_marshaller_ccmtools_corba_Components_InvalidConnection;

void ccmtools::corba::Components::InvalidConnection::_throwit() const
{
  #ifdef HAVE_EXCEPTIONS
  #ifdef HAVE_STD_EH
  throw *this;
  #else
  throw InvalidConnection_var( (ccmtools::corba::Components::InvalidConnection*)_clone() );
  #endif
  #else
  CORBA::Exception::_throw_failed( _clone() );
  #endif
}

const char *ccmtools::corba::Components::InvalidConnection::_repoid() const
{
  return "IDL:ccmtools/corba/Components/InvalidConnection:1.0";
}

void ccmtools::corba::Components::InvalidConnection::_encode( CORBA::DataEncoder &_en ) const
{
  _marshaller_ccmtools_corba_Components_InvalidConnection->marshal( _en, (void*) this );
}

void ccmtools::corba::Components::InvalidConnection::_encode_any( CORBA::Any & ) const
{
  // use --any to make this work!
  assert(0);
}

CORBA::Exception *ccmtools::corba::Components::InvalidConnection::_clone() const
{
  return new InvalidConnection( *this );
}

ccmtools::corba::Components::InvalidConnection *ccmtools::corba::Components::InvalidConnection::_downcast( CORBA::Exception *_ex )
{
  if( _ex && !strcmp( _ex->_repoid(), "IDL:ccmtools/corba/Components/InvalidConnection:1.0" ) )
    return (InvalidConnection *) _ex;
  return NULL;
}

const ccmtools::corba::Components::InvalidConnection *ccmtools::corba::Components::InvalidConnection::_downcast( const CORBA::Exception *_ex )
{
  if( _ex && !strcmp( _ex->_repoid(), "IDL:ccmtools/corba/Components/InvalidConnection:1.0" ) )
    return (InvalidConnection *) _ex;
  return NULL;
}


#ifdef HAVE_EXPLICIT_STRUCT_OPS
ccmtools::corba::Components::AlreadyConnected::AlreadyConnected()
{
}

ccmtools::corba::Components::AlreadyConnected::AlreadyConnected( const AlreadyConnected& _s )
{
}

ccmtools::corba::Components::AlreadyConnected::~AlreadyConnected()
{
}

ccmtools::corba::Components::AlreadyConnected&
ccmtools::corba::Components::AlreadyConnected::operator=( const AlreadyConnected& _s )
{
  return *this;
}
#endif

class _Marshaller_ccmtools_corba_Components_AlreadyConnected : public ::CORBA::StaticTypeInfo {
    typedef ::ccmtools::corba::Components::AlreadyConnected _MICO_T;
  public:
    ~_Marshaller_ccmtools_corba_Components_AlreadyConnected();
    StaticValueType create () const;
    void assign (StaticValueType dst, const StaticValueType src) const;
    void free (StaticValueType) const;
    ::CORBA::Boolean demarshal (::CORBA::DataDecoder&, StaticValueType) const;
    void marshal (::CORBA::DataEncoder &, StaticValueType) const;
};


_Marshaller_ccmtools_corba_Components_AlreadyConnected::~_Marshaller_ccmtools_corba_Components_AlreadyConnected()
{
}

::CORBA::StaticValueType _Marshaller_ccmtools_corba_Components_AlreadyConnected::create() const
{
  return (StaticValueType) new _MICO_T;
}

void _Marshaller_ccmtools_corba_Components_AlreadyConnected::assign( StaticValueType d, const StaticValueType s ) const
{
  *(_MICO_T*) d = *(_MICO_T*) s;
}

void _Marshaller_ccmtools_corba_Components_AlreadyConnected::free( StaticValueType v ) const
{
  delete (_MICO_T*) v;
}

::CORBA::Boolean _Marshaller_ccmtools_corba_Components_AlreadyConnected::demarshal( ::CORBA::DataDecoder &dc, StaticValueType v ) const
{
  string repoid;
  return
    dc.except_begin( repoid ) &&
    dc.except_end();
}

void _Marshaller_ccmtools_corba_Components_AlreadyConnected::marshal( ::CORBA::DataEncoder &ec, StaticValueType v ) const
{
  ec.except_begin( "IDL:ccmtools/corba/Components/AlreadyConnected:1.0" );
  ec.except_end();
}

::CORBA::StaticTypeInfo *_marshaller_ccmtools_corba_Components_AlreadyConnected;

void ccmtools::corba::Components::AlreadyConnected::_throwit() const
{
  #ifdef HAVE_EXCEPTIONS
  #ifdef HAVE_STD_EH
  throw *this;
  #else
  throw AlreadyConnected_var( (ccmtools::corba::Components::AlreadyConnected*)_clone() );
  #endif
  #else
  CORBA::Exception::_throw_failed( _clone() );
  #endif
}

const char *ccmtools::corba::Components::AlreadyConnected::_repoid() const
{
  return "IDL:ccmtools/corba/Components/AlreadyConnected:1.0";
}

void ccmtools::corba::Components::AlreadyConnected::_encode( CORBA::DataEncoder &_en ) const
{
  _marshaller_ccmtools_corba_Components_AlreadyConnected->marshal( _en, (void*) this );
}

void ccmtools::corba::Components::AlreadyConnected::_encode_any( CORBA::Any & ) const
{
  // use --any to make this work!
  assert(0);
}

CORBA::Exception *ccmtools::corba::Components::AlreadyConnected::_clone() const
{
  return new AlreadyConnected( *this );
}

ccmtools::corba::Components::AlreadyConnected *ccmtools::corba::Components::AlreadyConnected::_downcast( CORBA::Exception *_ex )
{
  if( _ex && !strcmp( _ex->_repoid(), "IDL:ccmtools/corba/Components/AlreadyConnected:1.0" ) )
    return (AlreadyConnected *) _ex;
  return NULL;
}

const ccmtools::corba::Components::AlreadyConnected *ccmtools::corba::Components::AlreadyConnected::_downcast( const CORBA::Exception *_ex )
{
  if( _ex && !strcmp( _ex->_repoid(), "IDL:ccmtools/corba/Components/AlreadyConnected:1.0" ) )
    return (AlreadyConnected *) _ex;
  return NULL;
}


#ifdef HAVE_EXPLICIT_STRUCT_OPS
ccmtools::corba::Components::ExceededConnectionLimit::ExceededConnectionLimit()
{
}

ccmtools::corba::Components::ExceededConnectionLimit::ExceededConnectionLimit( const ExceededConnectionLimit& _s )
{
}

ccmtools::corba::Components::ExceededConnectionLimit::~ExceededConnectionLimit()
{
}

ccmtools::corba::Components::ExceededConnectionLimit&
ccmtools::corba::Components::ExceededConnectionLimit::operator=( const ExceededConnectionLimit& _s )
{
  return *this;
}
#endif

class _Marshaller_ccmtools_corba_Components_ExceededConnectionLimit : public ::CORBA::StaticTypeInfo {
    typedef ::ccmtools::corba::Components::ExceededConnectionLimit _MICO_T;
  public:
    ~_Marshaller_ccmtools_corba_Components_ExceededConnectionLimit();
    StaticValueType create () const;
    void assign (StaticValueType dst, const StaticValueType src) const;
    void free (StaticValueType) const;
    ::CORBA::Boolean demarshal (::CORBA::DataDecoder&, StaticValueType) const;
    void marshal (::CORBA::DataEncoder &, StaticValueType) const;
};


_Marshaller_ccmtools_corba_Components_ExceededConnectionLimit::~_Marshaller_ccmtools_corba_Components_ExceededConnectionLimit()
{
}

::CORBA::StaticValueType _Marshaller_ccmtools_corba_Components_ExceededConnectionLimit::create() const
{
  return (StaticValueType) new _MICO_T;
}

void _Marshaller_ccmtools_corba_Components_ExceededConnectionLimit::assign( StaticValueType d, const StaticValueType s ) const
{
  *(_MICO_T*) d = *(_MICO_T*) s;
}

void _Marshaller_ccmtools_corba_Components_ExceededConnectionLimit::free( StaticValueType v ) const
{
  delete (_MICO_T*) v;
}

::CORBA::Boolean _Marshaller_ccmtools_corba_Components_ExceededConnectionLimit::demarshal( ::CORBA::DataDecoder &dc, StaticValueType v ) const
{
  string repoid;
  return
    dc.except_begin( repoid ) &&
    dc.except_end();
}

void _Marshaller_ccmtools_corba_Components_ExceededConnectionLimit::marshal( ::CORBA::DataEncoder &ec, StaticValueType v ) const
{
  ec.except_begin( "IDL:ccmtools/corba/Components/ExceededConnectionLimit:1.0" );
  ec.except_end();
}

::CORBA::StaticTypeInfo *_marshaller_ccmtools_corba_Components_ExceededConnectionLimit;

void ccmtools::corba::Components::ExceededConnectionLimit::_throwit() const
{
  #ifdef HAVE_EXCEPTIONS
  #ifdef HAVE_STD_EH
  throw *this;
  #else
  throw ExceededConnectionLimit_var( (ccmtools::corba::Components::ExceededConnectionLimit*)_clone() );
  #endif
  #else
  CORBA::Exception::_throw_failed( _clone() );
  #endif
}

const char *ccmtools::corba::Components::ExceededConnectionLimit::_repoid() const
{
  return "IDL:ccmtools/corba/Components/ExceededConnectionLimit:1.0";
}

void ccmtools::corba::Components::ExceededConnectionLimit::_encode( CORBA::DataEncoder &_en ) const
{
  _marshaller_ccmtools_corba_Components_ExceededConnectionLimit->marshal( _en, (void*) this );
}

void ccmtools::corba::Components::ExceededConnectionLimit::_encode_any( CORBA::Any & ) const
{
  // use --any to make this work!
  assert(0);
}

CORBA::Exception *ccmtools::corba::Components::ExceededConnectionLimit::_clone() const
{
  return new ExceededConnectionLimit( *this );
}

ccmtools::corba::Components::ExceededConnectionLimit *ccmtools::corba::Components::ExceededConnectionLimit::_downcast( CORBA::Exception *_ex )
{
  if( _ex && !strcmp( _ex->_repoid(), "IDL:ccmtools/corba/Components/ExceededConnectionLimit:1.0" ) )
    return (ExceededConnectionLimit *) _ex;
  return NULL;
}

const ccmtools::corba::Components::ExceededConnectionLimit *ccmtools::corba::Components::ExceededConnectionLimit::_downcast( const CORBA::Exception *_ex )
{
  if( _ex && !strcmp( _ex->_repoid(), "IDL:ccmtools/corba/Components/ExceededConnectionLimit:1.0" ) )
    return (ExceededConnectionLimit *) _ex;
  return NULL;
}


#ifdef HAVE_EXPLICIT_STRUCT_OPS
ccmtools::corba::Components::CookieRequired::CookieRequired()
{
}

ccmtools::corba::Components::CookieRequired::CookieRequired( const CookieRequired& _s )
{
}

ccmtools::corba::Components::CookieRequired::~CookieRequired()
{
}

ccmtools::corba::Components::CookieRequired&
ccmtools::corba::Components::CookieRequired::operator=( const CookieRequired& _s )
{
  return *this;
}
#endif

class _Marshaller_ccmtools_corba_Components_CookieRequired : public ::CORBA::StaticTypeInfo {
    typedef ::ccmtools::corba::Components::CookieRequired _MICO_T;
  public:
    ~_Marshaller_ccmtools_corba_Components_CookieRequired();
    StaticValueType create () const;
    void assign (StaticValueType dst, const StaticValueType src) const;
    void free (StaticValueType) const;
    ::CORBA::Boolean demarshal (::CORBA::DataDecoder&, StaticValueType) const;
    void marshal (::CORBA::DataEncoder &, StaticValueType) const;
};


_Marshaller_ccmtools_corba_Components_CookieRequired::~_Marshaller_ccmtools_corba_Components_CookieRequired()
{
}

::CORBA::StaticValueType _Marshaller_ccmtools_corba_Components_CookieRequired::create() const
{
  return (StaticValueType) new _MICO_T;
}

void _Marshaller_ccmtools_corba_Components_CookieRequired::assign( StaticValueType d, const StaticValueType s ) const
{
  *(_MICO_T*) d = *(_MICO_T*) s;
}

void _Marshaller_ccmtools_corba_Components_CookieRequired::free( StaticValueType v ) const
{
  delete (_MICO_T*) v;
}

::CORBA::Boolean _Marshaller_ccmtools_corba_Components_CookieRequired::demarshal( ::CORBA::DataDecoder &dc, StaticValueType v ) const
{
  string repoid;
  return
    dc.except_begin( repoid ) &&
    dc.except_end();
}

void _Marshaller_ccmtools_corba_Components_CookieRequired::marshal( ::CORBA::DataEncoder &ec, StaticValueType v ) const
{
  ec.except_begin( "IDL:ccmtools/corba/Components/CookieRequired:1.0" );
  ec.except_end();
}

::CORBA::StaticTypeInfo *_marshaller_ccmtools_corba_Components_CookieRequired;

void ccmtools::corba::Components::CookieRequired::_throwit() const
{
  #ifdef HAVE_EXCEPTIONS
  #ifdef HAVE_STD_EH
  throw *this;
  #else
  throw CookieRequired_var( (ccmtools::corba::Components::CookieRequired*)_clone() );
  #endif
  #else
  CORBA::Exception::_throw_failed( _clone() );
  #endif
}

const char *ccmtools::corba::Components::CookieRequired::_repoid() const
{
  return "IDL:ccmtools/corba/Components/CookieRequired:1.0";
}

void ccmtools::corba::Components::CookieRequired::_encode( CORBA::DataEncoder &_en ) const
{
  _marshaller_ccmtools_corba_Components_CookieRequired->marshal( _en, (void*) this );
}

void ccmtools::corba::Components::CookieRequired::_encode_any( CORBA::Any & ) const
{
  // use --any to make this work!
  assert(0);
}

CORBA::Exception *ccmtools::corba::Components::CookieRequired::_clone() const
{
  return new CookieRequired( *this );
}

ccmtools::corba::Components::CookieRequired *ccmtools::corba::Components::CookieRequired::_downcast( CORBA::Exception *_ex )
{
  if( _ex && !strcmp( _ex->_repoid(), "IDL:ccmtools/corba/Components/CookieRequired:1.0" ) )
    return (CookieRequired *) _ex;
  return NULL;
}

const ccmtools::corba::Components::CookieRequired *ccmtools::corba::Components::CookieRequired::_downcast( const CORBA::Exception *_ex )
{
  if( _ex && !strcmp( _ex->_repoid(), "IDL:ccmtools/corba/Components/CookieRequired:1.0" ) )
    return (CookieRequired *) _ex;
  return NULL;
}


#ifdef HAVE_EXPLICIT_STRUCT_OPS
ccmtools::corba::Components::NoConnection::NoConnection()
{
}

ccmtools::corba::Components::NoConnection::NoConnection( const NoConnection& _s )
{
}

ccmtools::corba::Components::NoConnection::~NoConnection()
{
}

ccmtools::corba::Components::NoConnection&
ccmtools::corba::Components::NoConnection::operator=( const NoConnection& _s )
{
  return *this;
}
#endif

class _Marshaller_ccmtools_corba_Components_NoConnection : public ::CORBA::StaticTypeInfo {
    typedef ::ccmtools::corba::Components::NoConnection _MICO_T;
  public:
    ~_Marshaller_ccmtools_corba_Components_NoConnection();
    StaticValueType create () const;
    void assign (StaticValueType dst, const StaticValueType src) const;
    void free (StaticValueType) const;
    ::CORBA::Boolean demarshal (::CORBA::DataDecoder&, StaticValueType) const;
    void marshal (::CORBA::DataEncoder &, StaticValueType) const;
};


_Marshaller_ccmtools_corba_Components_NoConnection::~_Marshaller_ccmtools_corba_Components_NoConnection()
{
}

::CORBA::StaticValueType _Marshaller_ccmtools_corba_Components_NoConnection::create() const
{
  return (StaticValueType) new _MICO_T;
}

void _Marshaller_ccmtools_corba_Components_NoConnection::assign( StaticValueType d, const StaticValueType s ) const
{
  *(_MICO_T*) d = *(_MICO_T*) s;
}

void _Marshaller_ccmtools_corba_Components_NoConnection::free( StaticValueType v ) const
{
  delete (_MICO_T*) v;
}

::CORBA::Boolean _Marshaller_ccmtools_corba_Components_NoConnection::demarshal( ::CORBA::DataDecoder &dc, StaticValueType v ) const
{
  string repoid;
  return
    dc.except_begin( repoid ) &&
    dc.except_end();
}

void _Marshaller_ccmtools_corba_Components_NoConnection::marshal( ::CORBA::DataEncoder &ec, StaticValueType v ) const
{
  ec.except_begin( "IDL:ccmtools/corba/Components/NoConnection:1.0" );
  ec.except_end();
}

::CORBA::StaticTypeInfo *_marshaller_ccmtools_corba_Components_NoConnection;

void ccmtools::corba::Components::NoConnection::_throwit() const
{
  #ifdef HAVE_EXCEPTIONS
  #ifdef HAVE_STD_EH
  throw *this;
  #else
  throw NoConnection_var( (ccmtools::corba::Components::NoConnection*)_clone() );
  #endif
  #else
  CORBA::Exception::_throw_failed( _clone() );
  #endif
}

const char *ccmtools::corba::Components::NoConnection::_repoid() const
{
  return "IDL:ccmtools/corba/Components/NoConnection:1.0";
}

void ccmtools::corba::Components::NoConnection::_encode( CORBA::DataEncoder &_en ) const
{
  _marshaller_ccmtools_corba_Components_NoConnection->marshal( _en, (void*) this );
}

void ccmtools::corba::Components::NoConnection::_encode_any( CORBA::Any & ) const
{
  // use --any to make this work!
  assert(0);
}

CORBA::Exception *ccmtools::corba::Components::NoConnection::_clone() const
{
  return new NoConnection( *this );
}

ccmtools::corba::Components::NoConnection *ccmtools::corba::Components::NoConnection::_downcast( CORBA::Exception *_ex )
{
  if( _ex && !strcmp( _ex->_repoid(), "IDL:ccmtools/corba/Components/NoConnection:1.0" ) )
    return (NoConnection *) _ex;
  return NULL;
}

const ccmtools::corba::Components::NoConnection *ccmtools::corba::Components::NoConnection::_downcast( const CORBA::Exception *_ex )
{
  if( _ex && !strcmp( _ex->_repoid(), "IDL:ccmtools/corba/Components/NoConnection:1.0" ) )
    return (NoConnection *) _ex;
  return NULL;
}


// valuetype Cookie
ccmtools::corba::Components::Cookie::Cookie ()
{
}

ccmtools::corba::Components::Cookie::~Cookie ()
{
}

void *
ccmtools::corba::Components::Cookie::_narrow_helper (const char * repoid)
{
  if (strcmp (repoid, "IDL:ccmtools/corba/Components/Cookie:1.0") == 0) {
    return (void *) this;
  }
  return NULL;
}

ccmtools::corba::Components::Cookie *
ccmtools::corba::Components::Cookie::_downcast (CORBA::ValueBase * vb) 
{
  void * p;
  if (vb && ((p = vb->_narrow_helper ("IDL:ccmtools/corba/Components/Cookie:1.0")))) {
    return (ccmtools::corba::Components::Cookie *) p;
  }
  return 0;
}

ccmtools::corba::Components::Cookie *
ccmtools::corba::Components::Cookie::_downcast (CORBA::AbstractBase * vb) 
{
  return _downcast (vb->_to_value());
}

CORBA::ValueDef_ptr
ccmtools::corba::Components::Cookie::get_value_def () 
{
  CORBA::ORB_var orb = CORBA::ORB_instance ("mico-local-orb");
  CORBA::Object_var irobj = 
    orb->resolve_initial_references ("InterfaceRepository");
  CORBA::Repository_var ifr = CORBA::Repository::_narrow (irobj);
  if (CORBA::is_nil (ifr)) {
    return CORBA::ValueDef::_nil ();
  }

  CORBA::Contained_var cv = ifr->lookup_id ("IDL:ccmtools/corba/Components/Cookie:1.0");
  return CORBA::ValueDef::_narrow (cv);
}

void
ccmtools::corba::Components::Cookie::_copy_members (const Cookie& other)
{
  CookieValue (other.CookieValue());
}

CORBA::ValueBase *
ccmtools::corba::Components::Cookie::_copy_value ()
{
  vector<string> _dummy;
  string _repo_id = "IDL:ccmtools/corba/Components/Cookie:1.0";
  Cookie * _res = _downcast (_create (_dummy, _repo_id));
  assert (_res != 0);
  _res->_copy_members (*this);
  return _res;
}

void
ccmtools::corba::Components::Cookie::_get_marshal_info (vector<string> & repoids, CORBA::Boolean & chunked)
{
  repoids.push_back ("IDL:ccmtools/corba/Components/Cookie:1.0");
  chunked = FALSE;
}

void
ccmtools::corba::Components::Cookie::_marshal_members (CORBA::DataEncoder &ec)
{
  _CookieValue_seq & _CookieValue = CookieValue ();
  CORBA::_stcseq_octet->marshal (ec, &_CookieValue);
}

CORBA::Boolean
ccmtools::corba::Components::Cookie::_demarshal_members (CORBA::DataDecoder &dc)
{
  SequenceTmpl< CORBA::Octet,MICO_TID_OCTET> _CookieValue;
  if (!CORBA::_stcseq_octet->demarshal (dc, &_CookieValue)) {
      return FALSE;
  }
  CookieValue (_CookieValue);
  return TRUE;
}


class _Marshaller_ccmtools_corba_Components_Cookie : public ::CORBA::StaticTypeInfo {
    typedef ccmtools::corba::Components::Cookie* _MICO_T;
  public:
    ~_Marshaller_ccmtools_corba_Components_Cookie();
    StaticValueType create () const;
    void assign (StaticValueType dst, const StaticValueType src) const;
    void free (StaticValueType) const;
    ::CORBA::Boolean demarshal (::CORBA::DataDecoder&, StaticValueType) const;
    void marshal (::CORBA::DataEncoder &, StaticValueType) const;
};


_Marshaller_ccmtools_corba_Components_Cookie::~_Marshaller_ccmtools_corba_Components_Cookie()
{
}

::CORBA::StaticValueType _Marshaller_ccmtools_corba_Components_Cookie::create() const
{
  return (StaticValueType) new _MICO_T( 0 );
}

void _Marshaller_ccmtools_corba_Components_Cookie::assign( StaticValueType d, const StaticValueType s ) const
{
  ::CORBA::remove_ref (*(_MICO_T*)d);
  ::CORBA::add_ref (*(_MICO_T*)s);
  *(_MICO_T*) d = *(_MICO_T*) s;
}

void _Marshaller_ccmtools_corba_Components_Cookie::free( StaticValueType v ) const
{
  ::CORBA::remove_ref (*(_MICO_T*)v);
  delete (_MICO_T*) v;
}

::CORBA::Boolean _Marshaller_ccmtools_corba_Components_Cookie::demarshal( ::CORBA::DataDecoder &dc, StaticValueType v ) const
{
  ::CORBA::ValueBase* vb = NULL;
  if (!::CORBA::ValueBase::_demarshal (dc, vb, "IDL:ccmtools/corba/Components/Cookie:1.0")) {
    return FALSE;
  }
  ::CORBA::remove_ref (*(_MICO_T *)v);
  *(_MICO_T *)v = ::ccmtools::corba::Components::Cookie::_downcast (vb);
  if (vb && !*(_MICO_T *)v) {
    ::CORBA::remove_ref (vb);
    return FALSE;
  }
  return TRUE;
}

void _Marshaller_ccmtools_corba_Components_Cookie::marshal( ::CORBA::DataEncoder &ec, StaticValueType v ) const
{
  ::CORBA::ValueBase::_marshal (ec, *(_MICO_T *)v);
}

::CORBA::StaticTypeInfo *_marshaller_ccmtools_corba_Components_Cookie;


// OBV class for valuetype Cookie
OBV_ccmtools::corba::Components::Cookie::Cookie ()
{
}

OBV_ccmtools::corba::Components::Cookie::Cookie (const _CookieValue_seq& _CookieValue)
{
  this->CookieValue(_CookieValue);
}

OBV_ccmtools::corba::Components::Cookie::~Cookie ()
{
}

void OBV_ccmtools::corba::Components::Cookie::CookieValue( const SequenceTmpl< CORBA::Octet,MICO_TID_OCTET>& _p )
{
  _m.CookieValue = _p;
}

const OBV_ccmtools::corba::Components::Cookie::_CookieValue_seq& OBV_ccmtools::corba::Components::Cookie::CookieValue() const
{
  return _m.CookieValue;
}

OBV_ccmtools::corba::Components::Cookie::_CookieValue_seq& OBV_ccmtools::corba::Components::Cookie::CookieValue()
{
  return _m.CookieValue;
}


/*
 * Base interface for class Receptacles
 */

ccmtools::corba::Components::Receptacles::~Receptacles()
{
}

void *
ccmtools::corba::Components::Receptacles::_narrow_helper( const char *_repoid )
{
  if( strcmp( _repoid, "IDL:ccmtools/corba/Components/Receptacles:1.0" ) == 0 )
    return (void *)this;
  return NULL;
}

ccmtools::corba::Components::Receptacles_ptr
ccmtools::corba::Components::Receptacles::_narrow( CORBA::Object_ptr _obj )
{
  ccmtools::corba::Components::Receptacles_ptr _o;
  if( !CORBA::is_nil( _obj ) ) {
    void *_p;
    if( (_p = _obj->_narrow_helper( "IDL:ccmtools/corba/Components/Receptacles:1.0" )))
      return _duplicate( (ccmtools::corba::Components::Receptacles_ptr) _p );
    if (!strcmp (_obj->_repoid(), "IDL:ccmtools/corba/Components/Receptacles:1.0") || _obj->_is_a_remote ("IDL:ccmtools/corba/Components/Receptacles:1.0")) {
      _o = new ccmtools::corba::Components::Receptacles_stub;
      _o->CORBA::Object::operator=( *_obj );
      return _o;
    }
  }
  return _nil();
}

ccmtools::corba::Components::Receptacles_ptr
ccmtools::corba::Components::Receptacles::_narrow( CORBA::AbstractBase_ptr _obj )
{
  return _narrow (_obj->_to_object());
}

class _Marshaller_ccmtools_corba_Components_Receptacles : public ::CORBA::StaticTypeInfo {
    typedef ccmtools::corba::Components::Receptacles_ptr _MICO_T;
  public:
    ~_Marshaller_ccmtools_corba_Components_Receptacles();
    StaticValueType create () const;
    void assign (StaticValueType dst, const StaticValueType src) const;
    void free (StaticValueType) const;
    void release (StaticValueType) const;
    ::CORBA::Boolean demarshal (::CORBA::DataDecoder&, StaticValueType) const;
    void marshal (::CORBA::DataEncoder &, StaticValueType) const;
};


_Marshaller_ccmtools_corba_Components_Receptacles::~_Marshaller_ccmtools_corba_Components_Receptacles()
{
}

::CORBA::StaticValueType _Marshaller_ccmtools_corba_Components_Receptacles::create() const
{
  return (StaticValueType) new _MICO_T( 0 );
}

void _Marshaller_ccmtools_corba_Components_Receptacles::assign( StaticValueType d, const StaticValueType s ) const
{
  *(_MICO_T*) d = ::ccmtools::corba::Components::Receptacles::_duplicate( *(_MICO_T*) s );
}

void _Marshaller_ccmtools_corba_Components_Receptacles::free( StaticValueType v ) const
{
  ::CORBA::release( *(_MICO_T *) v );
  delete (_MICO_T*) v;
}

void _Marshaller_ccmtools_corba_Components_Receptacles::release( StaticValueType v ) const
{
  ::CORBA::release( *(_MICO_T *) v );
}

::CORBA::Boolean _Marshaller_ccmtools_corba_Components_Receptacles::demarshal( ::CORBA::DataDecoder &dc, StaticValueType v ) const
{
  ::CORBA::Object_ptr obj;
  if (!::CORBA::_stc_Object->demarshal(dc, &obj))
    return FALSE;
  *(_MICO_T *) v = ::ccmtools::corba::Components::Receptacles::_narrow( obj );
  ::CORBA::Boolean ret = ::CORBA::is_nil (obj) || !::CORBA::is_nil (*(_MICO_T *)v);
  ::CORBA::release (obj);
  return ret;
}

void _Marshaller_ccmtools_corba_Components_Receptacles::marshal( ::CORBA::DataEncoder &ec, StaticValueType v ) const
{
  ::CORBA::Object_ptr obj = *(_MICO_T *) v;
  ::CORBA::_stc_Object->marshal( ec, &obj );
}

::CORBA::StaticTypeInfo *_marshaller_ccmtools_corba_Components_Receptacles;


/*
 * Stub interface for class Receptacles
 */

ccmtools::corba::Components::Receptacles_stub::~Receptacles_stub()
{
}

#ifndef MICO_CONF_NO_POA

void *
POA_ccmtools::corba::Components::Receptacles::_narrow_helper (const char * repoid)
{
  if (strcmp (repoid, "IDL:ccmtools/corba/Components/Receptacles:1.0") == 0) {
    return (void *) this;
  }
  return NULL;
}

POA_ccmtools::corba::Components::Receptacles *
POA_ccmtools::corba::Components::Receptacles::_narrow (PortableServer::Servant serv) 
{
  void * p;
  if ((p = serv->_narrow_helper ("IDL:ccmtools/corba/Components/Receptacles:1.0")) != NULL) {
    serv->_add_ref ();
    return (POA_ccmtools::corba::Components::Receptacles *) p;
  }
  return NULL;
}

ccmtools::corba::Components::Receptacles_stub_clp::Receptacles_stub_clp ()
{
}

ccmtools::corba::Components::Receptacles_stub_clp::Receptacles_stub_clp (PortableServer::POA_ptr poa, CORBA::Object_ptr obj)
  : CORBA::Object(*obj), PortableServer::StubBase(poa)
{
}

ccmtools::corba::Components::Receptacles_stub_clp::~Receptacles_stub_clp ()
{
}

#endif // MICO_CONF_NO_POA

ccmtools::corba::Components::Cookie* ccmtools::corba::Components::Receptacles_stub::connect( const char* _par_name, CORBA::Object_ptr _par_connection )
{
  CORBA::StaticAny _sa_name( CORBA::_stc_string, &_par_name );
  CORBA::StaticAny _sa_connection( CORBA::_stc_Object, &_par_connection );
  ccmtools::corba::Components::Cookie* _res = NULL;
  CORBA::StaticAny __res( _marshaller_ccmtools_corba_Components_Cookie, &_res );

  CORBA::StaticRequest __req( this, "connect" );
  __req.add_in_arg( &_sa_name );
  __req.add_in_arg( &_sa_connection );
  __req.set_result( &__res );

  __req.invoke();

  mico_sii_throw( &__req, 
    _marshaller_ccmtools_corba_Components_InvalidName, "IDL:ccmtools/corba/Components/InvalidName:1.0",
    _marshaller_ccmtools_corba_Components_InvalidConnection, "IDL:ccmtools/corba/Components/InvalidConnection:1.0",
    _marshaller_ccmtools_corba_Components_AlreadyConnected, "IDL:ccmtools/corba/Components/AlreadyConnected:1.0",
    _marshaller_ccmtools_corba_Components_ExceededConnectionLimit, "IDL:ccmtools/corba/Components/ExceededConnectionLimit:1.0",
    0);
  return _res;
}


#ifndef MICO_CONF_NO_POA

ccmtools::corba::Components::Cookie*
ccmtools::corba::Components::Receptacles_stub_clp::connect( const char* _par_name, CORBA::Object_ptr _par_connection )
{
  PortableServer::Servant _serv = _preinvoke ();
  if (_serv) {
    POA_ccmtools::corba::Components::Receptacles * _myserv = POA_ccmtools::corba::Components::Receptacles::_narrow (_serv);
    if (_myserv) {
      ccmtools::corba::Components::Cookie* __res;

      #ifdef HAVE_EXCEPTIONS
      try {
      #endif
        __res = _myserv->connect(_par_name, _par_connection);
      #ifdef HAVE_EXCEPTIONS
      }
      catch (...) {
        _myserv->_remove_ref();
        _postinvoke();
        throw;
      }
      #endif

      _myserv->_remove_ref();
      _postinvoke ();
      ccmtools::corba::Components::Cookie* __res2 = ccmtools::corba::Components::Cookie::_downcast (__res->_copy_value ());
      CORBA::remove_ref (__res);
      return __res2;
    }
    _postinvoke ();
  }

  return ccmtools::corba::Components::Receptacles_stub::connect(_par_name, _par_connection);
}

#endif // MICO_CONF_NO_POA

void ccmtools::corba::Components::Receptacles_stub::disconnect( const char* _par_name, ccmtools::corba::Components::Cookie* _par_ck )
{
  CORBA::StaticAny _sa_name( CORBA::_stc_string, &_par_name );
  CORBA::StaticAny _sa_ck( _marshaller_ccmtools_corba_Components_Cookie, &_par_ck );
  CORBA::StaticRequest __req( this, "disconnect" );
  __req.add_in_arg( &_sa_name );
  __req.add_in_arg( &_sa_ck );

  __req.invoke();

  mico_sii_throw( &__req, 
    _marshaller_ccmtools_corba_Components_InvalidName, "IDL:ccmtools/corba/Components/InvalidName:1.0",
    _marshaller_ccmtools_corba_Components_InvalidConnection, "IDL:ccmtools/corba/Components/InvalidConnection:1.0",
    _marshaller_ccmtools_corba_Components_CookieRequired, "IDL:ccmtools/corba/Components/CookieRequired:1.0",
    _marshaller_ccmtools_corba_Components_NoConnection, "IDL:ccmtools/corba/Components/NoConnection:1.0",
    0);
}


#ifndef MICO_CONF_NO_POA

void
ccmtools::corba::Components::Receptacles_stub_clp::disconnect( const char* _par_name, ccmtools::corba::Components::Cookie* _par_ck )
{
  PortableServer::Servant _serv = _preinvoke ();
  if (_serv) {
    POA_ccmtools::corba::Components::Receptacles * _myserv = POA_ccmtools::corba::Components::Receptacles::_narrow (_serv);
    if (_myserv) {
      ccmtools::corba::Components::Cookie* _copy_of_par_ck;
      _copy_of_par_ck = ccmtools::corba::Components::Cookie::_downcast (_par_ck->_copy_value());
      #ifdef HAVE_EXCEPTIONS
      try {
      #endif
        _myserv->disconnect(_par_name, _copy_of_par_ck);
      #ifdef HAVE_EXCEPTIONS
      }
      catch (...) {
        _myserv->_remove_ref();
        _postinvoke();
        throw;
      }
      #endif

      _myserv->_remove_ref();
      _postinvoke ();
      CORBA::remove_ref (_copy_of_par_ck);
      return;
    }
    _postinvoke ();
  }

  ccmtools::corba::Components::Receptacles_stub::disconnect(_par_name, _par_ck);
}

#endif // MICO_CONF_NO_POA


#ifdef HAVE_EXPLICIT_STRUCT_OPS
ccmtools::corba::Components::InvalidConfiguration::InvalidConfiguration()
{
}

ccmtools::corba::Components::InvalidConfiguration::InvalidConfiguration( const InvalidConfiguration& _s )
{
}

ccmtools::corba::Components::InvalidConfiguration::~InvalidConfiguration()
{
}

ccmtools::corba::Components::InvalidConfiguration&
ccmtools::corba::Components::InvalidConfiguration::operator=( const InvalidConfiguration& _s )
{
  return *this;
}
#endif

class _Marshaller_ccmtools_corba_Components_InvalidConfiguration : public ::CORBA::StaticTypeInfo {
    typedef ::ccmtools::corba::Components::InvalidConfiguration _MICO_T;
  public:
    ~_Marshaller_ccmtools_corba_Components_InvalidConfiguration();
    StaticValueType create () const;
    void assign (StaticValueType dst, const StaticValueType src) const;
    void free (StaticValueType) const;
    ::CORBA::Boolean demarshal (::CORBA::DataDecoder&, StaticValueType) const;
    void marshal (::CORBA::DataEncoder &, StaticValueType) const;
};


_Marshaller_ccmtools_corba_Components_InvalidConfiguration::~_Marshaller_ccmtools_corba_Components_InvalidConfiguration()
{
}

::CORBA::StaticValueType _Marshaller_ccmtools_corba_Components_InvalidConfiguration::create() const
{
  return (StaticValueType) new _MICO_T;
}

void _Marshaller_ccmtools_corba_Components_InvalidConfiguration::assign( StaticValueType d, const StaticValueType s ) const
{
  *(_MICO_T*) d = *(_MICO_T*) s;
}

void _Marshaller_ccmtools_corba_Components_InvalidConfiguration::free( StaticValueType v ) const
{
  delete (_MICO_T*) v;
}

::CORBA::Boolean _Marshaller_ccmtools_corba_Components_InvalidConfiguration::demarshal( ::CORBA::DataDecoder &dc, StaticValueType v ) const
{
  string repoid;
  return
    dc.except_begin( repoid ) &&
    dc.except_end();
}

void _Marshaller_ccmtools_corba_Components_InvalidConfiguration::marshal( ::CORBA::DataEncoder &ec, StaticValueType v ) const
{
  ec.except_begin( "IDL:ccmtools/corba/Components/InvalidConfiguration:1.0" );
  ec.except_end();
}

::CORBA::StaticTypeInfo *_marshaller_ccmtools_corba_Components_InvalidConfiguration;

void ccmtools::corba::Components::InvalidConfiguration::_throwit() const
{
  #ifdef HAVE_EXCEPTIONS
  #ifdef HAVE_STD_EH
  throw *this;
  #else
  throw InvalidConfiguration_var( (ccmtools::corba::Components::InvalidConfiguration*)_clone() );
  #endif
  #else
  CORBA::Exception::_throw_failed( _clone() );
  #endif
}

const char *ccmtools::corba::Components::InvalidConfiguration::_repoid() const
{
  return "IDL:ccmtools/corba/Components/InvalidConfiguration:1.0";
}

void ccmtools::corba::Components::InvalidConfiguration::_encode( CORBA::DataEncoder &_en ) const
{
  _marshaller_ccmtools_corba_Components_InvalidConfiguration->marshal( _en, (void*) this );
}

void ccmtools::corba::Components::InvalidConfiguration::_encode_any( CORBA::Any & ) const
{
  // use --any to make this work!
  assert(0);
}

CORBA::Exception *ccmtools::corba::Components::InvalidConfiguration::_clone() const
{
  return new InvalidConfiguration( *this );
}

ccmtools::corba::Components::InvalidConfiguration *ccmtools::corba::Components::InvalidConfiguration::_downcast( CORBA::Exception *_ex )
{
  if( _ex && !strcmp( _ex->_repoid(), "IDL:ccmtools/corba/Components/InvalidConfiguration:1.0" ) )
    return (InvalidConfiguration *) _ex;
  return NULL;
}

const ccmtools::corba::Components::InvalidConfiguration *ccmtools::corba::Components::InvalidConfiguration::_downcast( const CORBA::Exception *_ex )
{
  if( _ex && !strcmp( _ex->_repoid(), "IDL:ccmtools/corba/Components/InvalidConfiguration:1.0" ) )
    return (InvalidConfiguration *) _ex;
  return NULL;
}


#ifdef HAVE_EXPLICIT_STRUCT_OPS
ccmtools::corba::Components::RemoveFailure::RemoveFailure()
{
}

ccmtools::corba::Components::RemoveFailure::RemoveFailure( const RemoveFailure& _s )
{
}

ccmtools::corba::Components::RemoveFailure::~RemoveFailure()
{
}

ccmtools::corba::Components::RemoveFailure&
ccmtools::corba::Components::RemoveFailure::operator=( const RemoveFailure& _s )
{
  return *this;
}
#endif

class _Marshaller_ccmtools_corba_Components_RemoveFailure : public ::CORBA::StaticTypeInfo {
    typedef ::ccmtools::corba::Components::RemoveFailure _MICO_T;
  public:
    ~_Marshaller_ccmtools_corba_Components_RemoveFailure();
    StaticValueType create () const;
    void assign (StaticValueType dst, const StaticValueType src) const;
    void free (StaticValueType) const;
    ::CORBA::Boolean demarshal (::CORBA::DataDecoder&, StaticValueType) const;
    void marshal (::CORBA::DataEncoder &, StaticValueType) const;
};


_Marshaller_ccmtools_corba_Components_RemoveFailure::~_Marshaller_ccmtools_corba_Components_RemoveFailure()
{
}

::CORBA::StaticValueType _Marshaller_ccmtools_corba_Components_RemoveFailure::create() const
{
  return (StaticValueType) new _MICO_T;
}

void _Marshaller_ccmtools_corba_Components_RemoveFailure::assign( StaticValueType d, const StaticValueType s ) const
{
  *(_MICO_T*) d = *(_MICO_T*) s;
}

void _Marshaller_ccmtools_corba_Components_RemoveFailure::free( StaticValueType v ) const
{
  delete (_MICO_T*) v;
}

::CORBA::Boolean _Marshaller_ccmtools_corba_Components_RemoveFailure::demarshal( ::CORBA::DataDecoder &dc, StaticValueType v ) const
{
  string repoid;
  return
    dc.except_begin( repoid ) &&
    dc.except_end();
}

void _Marshaller_ccmtools_corba_Components_RemoveFailure::marshal( ::CORBA::DataEncoder &ec, StaticValueType v ) const
{
  ec.except_begin( "IDL:ccmtools/corba/Components/RemoveFailure:1.0" );
  ec.except_end();
}

::CORBA::StaticTypeInfo *_marshaller_ccmtools_corba_Components_RemoveFailure;

void ccmtools::corba::Components::RemoveFailure::_throwit() const
{
  #ifdef HAVE_EXCEPTIONS
  #ifdef HAVE_STD_EH
  throw *this;
  #else
  throw RemoveFailure_var( (ccmtools::corba::Components::RemoveFailure*)_clone() );
  #endif
  #else
  CORBA::Exception::_throw_failed( _clone() );
  #endif
}

const char *ccmtools::corba::Components::RemoveFailure::_repoid() const
{
  return "IDL:ccmtools/corba/Components/RemoveFailure:1.0";
}

void ccmtools::corba::Components::RemoveFailure::_encode( CORBA::DataEncoder &_en ) const
{
  _marshaller_ccmtools_corba_Components_RemoveFailure->marshal( _en, (void*) this );
}

void ccmtools::corba::Components::RemoveFailure::_encode_any( CORBA::Any & ) const
{
  // use --any to make this work!
  assert(0);
}

CORBA::Exception *ccmtools::corba::Components::RemoveFailure::_clone() const
{
  return new RemoveFailure( *this );
}

ccmtools::corba::Components::RemoveFailure *ccmtools::corba::Components::RemoveFailure::_downcast( CORBA::Exception *_ex )
{
  if( _ex && !strcmp( _ex->_repoid(), "IDL:ccmtools/corba/Components/RemoveFailure:1.0" ) )
    return (RemoveFailure *) _ex;
  return NULL;
}

const ccmtools::corba::Components::RemoveFailure *ccmtools::corba::Components::RemoveFailure::_downcast( const CORBA::Exception *_ex )
{
  if( _ex && !strcmp( _ex->_repoid(), "IDL:ccmtools/corba/Components/RemoveFailure:1.0" ) )
    return (RemoveFailure *) _ex;
  return NULL;
}


/*
 * Base interface for class CCMHome
 */

ccmtools::corba::Components::CCMHome::~CCMHome()
{
}

void *
ccmtools::corba::Components::CCMHome::_narrow_helper( const char *_repoid )
{
  if( strcmp( _repoid, "IDL:ccmtools/corba/Components/CCMHome:1.0" ) == 0 )
    return (void *)this;
  return NULL;
}

ccmtools::corba::Components::CCMHome_ptr
ccmtools::corba::Components::CCMHome::_narrow( CORBA::Object_ptr _obj )
{
  ccmtools::corba::Components::CCMHome_ptr _o;
  if( !CORBA::is_nil( _obj ) ) {
    void *_p;
    if( (_p = _obj->_narrow_helper( "IDL:ccmtools/corba/Components/CCMHome:1.0" )))
      return _duplicate( (ccmtools::corba::Components::CCMHome_ptr) _p );
    if (!strcmp (_obj->_repoid(), "IDL:ccmtools/corba/Components/CCMHome:1.0") || _obj->_is_a_remote ("IDL:ccmtools/corba/Components/CCMHome:1.0")) {
      _o = new ccmtools::corba::Components::CCMHome_stub;
      _o->CORBA::Object::operator=( *_obj );
      return _o;
    }
  }
  return _nil();
}

ccmtools::corba::Components::CCMHome_ptr
ccmtools::corba::Components::CCMHome::_narrow( CORBA::AbstractBase_ptr _obj )
{
  return _narrow (_obj->_to_object());
}

class _Marshaller_ccmtools_corba_Components_CCMHome : public ::CORBA::StaticTypeInfo {
    typedef ccmtools::corba::Components::CCMHome_ptr _MICO_T;
  public:
    ~_Marshaller_ccmtools_corba_Components_CCMHome();
    StaticValueType create () const;
    void assign (StaticValueType dst, const StaticValueType src) const;
    void free (StaticValueType) const;
    void release (StaticValueType) const;
    ::CORBA::Boolean demarshal (::CORBA::DataDecoder&, StaticValueType) const;
    void marshal (::CORBA::DataEncoder &, StaticValueType) const;
};


_Marshaller_ccmtools_corba_Components_CCMHome::~_Marshaller_ccmtools_corba_Components_CCMHome()
{
}

::CORBA::StaticValueType _Marshaller_ccmtools_corba_Components_CCMHome::create() const
{
  return (StaticValueType) new _MICO_T( 0 );
}

void _Marshaller_ccmtools_corba_Components_CCMHome::assign( StaticValueType d, const StaticValueType s ) const
{
  *(_MICO_T*) d = ::ccmtools::corba::Components::CCMHome::_duplicate( *(_MICO_T*) s );
}

void _Marshaller_ccmtools_corba_Components_CCMHome::free( StaticValueType v ) const
{
  ::CORBA::release( *(_MICO_T *) v );
  delete (_MICO_T*) v;
}

void _Marshaller_ccmtools_corba_Components_CCMHome::release( StaticValueType v ) const
{
  ::CORBA::release( *(_MICO_T *) v );
}

::CORBA::Boolean _Marshaller_ccmtools_corba_Components_CCMHome::demarshal( ::CORBA::DataDecoder &dc, StaticValueType v ) const
{
  ::CORBA::Object_ptr obj;
  if (!::CORBA::_stc_Object->demarshal(dc, &obj))
    return FALSE;
  *(_MICO_T *) v = ::ccmtools::corba::Components::CCMHome::_narrow( obj );
  ::CORBA::Boolean ret = ::CORBA::is_nil (obj) || !::CORBA::is_nil (*(_MICO_T *)v);
  ::CORBA::release (obj);
  return ret;
}

void _Marshaller_ccmtools_corba_Components_CCMHome::marshal( ::CORBA::DataEncoder &ec, StaticValueType v ) const
{
  ::CORBA::Object_ptr obj = *(_MICO_T *) v;
  ::CORBA::_stc_Object->marshal( ec, &obj );
}

::CORBA::StaticTypeInfo *_marshaller_ccmtools_corba_Components_CCMHome;


/*
 * Stub interface for class CCMHome
 */

ccmtools::corba::Components::CCMHome_stub::~CCMHome_stub()
{
}

#ifndef MICO_CONF_NO_POA

void *
POA_ccmtools::corba::Components::CCMHome::_narrow_helper (const char * repoid)
{
  if (strcmp (repoid, "IDL:ccmtools/corba/Components/CCMHome:1.0") == 0) {
    return (void *) this;
  }
  return NULL;
}

POA_ccmtools::corba::Components::CCMHome *
POA_ccmtools::corba::Components::CCMHome::_narrow (PortableServer::Servant serv) 
{
  void * p;
  if ((p = serv->_narrow_helper ("IDL:ccmtools/corba/Components/CCMHome:1.0")) != NULL) {
    serv->_add_ref ();
    return (POA_ccmtools::corba::Components::CCMHome *) p;
  }
  return NULL;
}

ccmtools::corba::Components::CCMHome_stub_clp::CCMHome_stub_clp ()
{
}

ccmtools::corba::Components::CCMHome_stub_clp::CCMHome_stub_clp (PortableServer::POA_ptr poa, CORBA::Object_ptr obj)
  : CORBA::Object(*obj), PortableServer::StubBase(poa)
{
}

ccmtools::corba::Components::CCMHome_stub_clp::~CCMHome_stub_clp ()
{
}

#endif // MICO_CONF_NO_POA

void ccmtools::corba::Components::CCMHome_stub::remove_component( ccmtools::corba::Components::CCMObject_ptr _par_comp )
{
  CORBA::StaticAny _sa_comp( _marshaller_ccmtools_corba_Components_CCMObject, &_par_comp );
  CORBA::StaticRequest __req( this, "remove_component" );
  __req.add_in_arg( &_sa_comp );

  __req.invoke();

  mico_sii_throw( &__req, 
    _marshaller_ccmtools_corba_Components_RemoveFailure, "IDL:ccmtools/corba/Components/RemoveFailure:1.0",
    0);
}


#ifndef MICO_CONF_NO_POA

void
ccmtools::corba::Components::CCMHome_stub_clp::remove_component( ccmtools::corba::Components::CCMObject_ptr _par_comp )
{
  PortableServer::Servant _serv = _preinvoke ();
  if (_serv) {
    POA_ccmtools::corba::Components::CCMHome * _myserv = POA_ccmtools::corba::Components::CCMHome::_narrow (_serv);
    if (_myserv) {
      #ifdef HAVE_EXCEPTIONS
      try {
      #endif
        _myserv->remove_component(_par_comp);
      #ifdef HAVE_EXCEPTIONS
      }
      catch (...) {
        _myserv->_remove_ref();
        _postinvoke();
        throw;
      }
      #endif

      _myserv->_remove_ref();
      _postinvoke ();
      return;
    }
    _postinvoke ();
  }

  ccmtools::corba::Components::CCMHome_stub::remove_component(_par_comp);
}

#endif // MICO_CONF_NO_POA


/*
 * Base interface for class CCMObject
 */

ccmtools::corba::Components::CCMObject::~CCMObject()
{
}

void *
ccmtools::corba::Components::CCMObject::_narrow_helper( const char *_repoid )
{
  if( strcmp( _repoid, "IDL:ccmtools/corba/Components/CCMObject:1.0" ) == 0 )
    return (void *)this;
  {
    void *_p;
    if ((_p = ccmtools::corba::Components::Navigation::_narrow_helper( _repoid )))
      return _p;
  }
  {
    void *_p;
    if ((_p = ccmtools::corba::Components::Receptacles::_narrow_helper( _repoid )))
      return _p;
  }
  return NULL;
}

ccmtools::corba::Components::CCMObject_ptr
ccmtools::corba::Components::CCMObject::_narrow( CORBA::Object_ptr _obj )
{
  ccmtools::corba::Components::CCMObject_ptr _o;
  if( !CORBA::is_nil( _obj ) ) {
    void *_p;
    if( (_p = _obj->_narrow_helper( "IDL:ccmtools/corba/Components/CCMObject:1.0" )))
      return _duplicate( (ccmtools::corba::Components::CCMObject_ptr) _p );
    if (!strcmp (_obj->_repoid(), "IDL:ccmtools/corba/Components/CCMObject:1.0") || _obj->_is_a_remote ("IDL:ccmtools/corba/Components/CCMObject:1.0")) {
      _o = new ccmtools::corba::Components::CCMObject_stub;
      _o->CORBA::Object::operator=( *_obj );
      return _o;
    }
  }
  return _nil();
}

ccmtools::corba::Components::CCMObject_ptr
ccmtools::corba::Components::CCMObject::_narrow( CORBA::AbstractBase_ptr _obj )
{
  return _narrow (_obj->_to_object());
}

class _Marshaller_ccmtools_corba_Components_CCMObject : public ::CORBA::StaticTypeInfo {
    typedef ccmtools::corba::Components::CCMObject_ptr _MICO_T;
  public:
    ~_Marshaller_ccmtools_corba_Components_CCMObject();
    StaticValueType create () const;
    void assign (StaticValueType dst, const StaticValueType src) const;
    void free (StaticValueType) const;
    void release (StaticValueType) const;
    ::CORBA::Boolean demarshal (::CORBA::DataDecoder&, StaticValueType) const;
    void marshal (::CORBA::DataEncoder &, StaticValueType) const;
};


_Marshaller_ccmtools_corba_Components_CCMObject::~_Marshaller_ccmtools_corba_Components_CCMObject()
{
}

::CORBA::StaticValueType _Marshaller_ccmtools_corba_Components_CCMObject::create() const
{
  return (StaticValueType) new _MICO_T( 0 );
}

void _Marshaller_ccmtools_corba_Components_CCMObject::assign( StaticValueType d, const StaticValueType s ) const
{
  *(_MICO_T*) d = ::ccmtools::corba::Components::CCMObject::_duplicate( *(_MICO_T*) s );
}

void _Marshaller_ccmtools_corba_Components_CCMObject::free( StaticValueType v ) const
{
  ::CORBA::release( *(_MICO_T *) v );
  delete (_MICO_T*) v;
}

void _Marshaller_ccmtools_corba_Components_CCMObject::release( StaticValueType v ) const
{
  ::CORBA::release( *(_MICO_T *) v );
}

::CORBA::Boolean _Marshaller_ccmtools_corba_Components_CCMObject::demarshal( ::CORBA::DataDecoder &dc, StaticValueType v ) const
{
  ::CORBA::Object_ptr obj;
  if (!::CORBA::_stc_Object->demarshal(dc, &obj))
    return FALSE;
  *(_MICO_T *) v = ::ccmtools::corba::Components::CCMObject::_narrow( obj );
  ::CORBA::Boolean ret = ::CORBA::is_nil (obj) || !::CORBA::is_nil (*(_MICO_T *)v);
  ::CORBA::release (obj);
  return ret;
}

void _Marshaller_ccmtools_corba_Components_CCMObject::marshal( ::CORBA::DataEncoder &ec, StaticValueType v ) const
{
  ::CORBA::Object_ptr obj = *(_MICO_T *) v;
  ::CORBA::_stc_Object->marshal( ec, &obj );
}

::CORBA::StaticTypeInfo *_marshaller_ccmtools_corba_Components_CCMObject;


/*
 * Stub interface for class CCMObject
 */

ccmtools::corba::Components::CCMObject_stub::~CCMObject_stub()
{
}

#ifndef MICO_CONF_NO_POA

void *
POA_ccmtools::corba::Components::CCMObject::_narrow_helper (const char * repoid)
{
  void * p;
  if (strcmp (repoid, "IDL:ccmtools/corba/Components/CCMObject:1.0") == 0) {
    return (void *) this;
  }
  if ((p = POA_ccmtools::corba::Components::Navigation::_narrow_helper (repoid)) != NULL) 
  {
    return p;
  }
  if ((p = POA_ccmtools::corba::Components::Receptacles::_narrow_helper (repoid)) != NULL) 
  {
    return p;
  }
  return NULL;
}

POA_ccmtools::corba::Components::CCMObject *
POA_ccmtools::corba::Components::CCMObject::_narrow (PortableServer::Servant serv) 
{
  void * p;
  if ((p = serv->_narrow_helper ("IDL:ccmtools/corba/Components/CCMObject:1.0")) != NULL) {
    serv->_add_ref ();
    return (POA_ccmtools::corba::Components::CCMObject *) p;
  }
  return NULL;
}

ccmtools::corba::Components::CCMObject_stub_clp::CCMObject_stub_clp ()
{
}

ccmtools::corba::Components::CCMObject_stub_clp::CCMObject_stub_clp (PortableServer::POA_ptr poa, CORBA::Object_ptr obj)
  : CORBA::Object(*obj), PortableServer::StubBase(poa)
{
}

ccmtools::corba::Components::CCMObject_stub_clp::~CCMObject_stub_clp ()
{
}

#endif // MICO_CONF_NO_POA

ccmtools::corba::Components::CCMHome_ptr ccmtools::corba::Components::CCMObject_stub::get_ccm_home()
{
  ccmtools::corba::Components::CCMHome_ptr _res = ccmtools::corba::Components::CCMHome::_nil();
  CORBA::StaticAny __res( _marshaller_ccmtools_corba_Components_CCMHome, &_res );

  CORBA::StaticRequest __req( this, "get_ccm_home" );
  __req.set_result( &__res );

  __req.invoke();

  mico_sii_throw( &__req, 
    0);
  return _res;
}


#ifndef MICO_CONF_NO_POA

ccmtools::corba::Components::CCMHome_ptr
ccmtools::corba::Components::CCMObject_stub_clp::get_ccm_home()
{
  PortableServer::Servant _serv = _preinvoke ();
  if (_serv) {
    POA_ccmtools::corba::Components::CCMObject * _myserv = POA_ccmtools::corba::Components::CCMObject::_narrow (_serv);
    if (_myserv) {
      ccmtools::corba::Components::CCMHome_ptr __res;

      #ifdef HAVE_EXCEPTIONS
      try {
      #endif
        __res = _myserv->get_ccm_home();
      #ifdef HAVE_EXCEPTIONS
      }
      catch (...) {
        _myserv->_remove_ref();
        _postinvoke();
        throw;
      }
      #endif

      _myserv->_remove_ref();
      _postinvoke ();
      return __res;
    }
    _postinvoke ();
  }

  return ccmtools::corba::Components::CCMObject_stub::get_ccm_home();
}

#endif // MICO_CONF_NO_POA

void ccmtools::corba::Components::CCMObject_stub::configuration_complete()
{
  CORBA::StaticRequest __req( this, "configuration_complete" );

  __req.invoke();

  mico_sii_throw( &__req, 
    _marshaller_ccmtools_corba_Components_InvalidConfiguration, "IDL:ccmtools/corba/Components/InvalidConfiguration:1.0",
    0);
}


#ifndef MICO_CONF_NO_POA

void
ccmtools::corba::Components::CCMObject_stub_clp::configuration_complete()
{
  PortableServer::Servant _serv = _preinvoke ();
  if (_serv) {
    POA_ccmtools::corba::Components::CCMObject * _myserv = POA_ccmtools::corba::Components::CCMObject::_narrow (_serv);
    if (_myserv) {
      #ifdef HAVE_EXCEPTIONS
      try {
      #endif
        _myserv->configuration_complete();
      #ifdef HAVE_EXCEPTIONS
      }
      catch (...) {
        _myserv->_remove_ref();
        _postinvoke();
        throw;
      }
      #endif

      _myserv->_remove_ref();
      _postinvoke ();
      return;
    }
    _postinvoke ();
  }

  ccmtools::corba::Components::CCMObject_stub::configuration_complete();
}

#endif // MICO_CONF_NO_POA

void ccmtools::corba::Components::CCMObject_stub::remove()
{
  CORBA::StaticRequest __req( this, "remove" );

  __req.invoke();

  mico_sii_throw( &__req, 
    _marshaller_ccmtools_corba_Components_RemoveFailure, "IDL:ccmtools/corba/Components/RemoveFailure:1.0",
    0);
}


#ifndef MICO_CONF_NO_POA

void
ccmtools::corba::Components::CCMObject_stub_clp::remove()
{
  PortableServer::Servant _serv = _preinvoke ();
  if (_serv) {
    POA_ccmtools::corba::Components::CCMObject * _myserv = POA_ccmtools::corba::Components::CCMObject::_narrow (_serv);
    if (_myserv) {
      #ifdef HAVE_EXCEPTIONS
      try {
      #endif
        _myserv->remove();
      #ifdef HAVE_EXCEPTIONS
      }
      catch (...) {
        _myserv->_remove_ref();
        _postinvoke();
        throw;
      }
      #endif

      _myserv->_remove_ref();
      _postinvoke ();
      return;
    }
    _postinvoke ();
  }

  ccmtools::corba::Components::CCMObject_stub::remove();
}

#endif // MICO_CONF_NO_POA



#ifdef HAVE_EXPLICIT_STRUCT_OPS
ccmtools::corba::Components::CreateFailure::CreateFailure()
{
}

ccmtools::corba::Components::CreateFailure::CreateFailure( const CreateFailure& _s )
{
  reason = ((CreateFailure&)_s).reason;
}

ccmtools::corba::Components::CreateFailure::~CreateFailure()
{
}

ccmtools::corba::Components::CreateFailure&
ccmtools::corba::Components::CreateFailure::operator=( const CreateFailure& _s )
{
  reason = ((CreateFailure&)_s).reason;
  return *this;
}
#endif

#ifndef HAVE_EXPLICIT_STRUCT_OPS
ccmtools::corba::Components::CreateFailure::CreateFailure()
{
}

#endif

ccmtools::corba::Components::CreateFailure::CreateFailure( ccmtools::corba::Components::FailureReason _m0 )
{
  reason = _m0;
}

class _Marshaller_ccmtools_corba_Components_CreateFailure : public ::CORBA::StaticTypeInfo {
    typedef ::ccmtools::corba::Components::CreateFailure _MICO_T;
  public:
    ~_Marshaller_ccmtools_corba_Components_CreateFailure();
    StaticValueType create () const;
    void assign (StaticValueType dst, const StaticValueType src) const;
    void free (StaticValueType) const;
    ::CORBA::Boolean demarshal (::CORBA::DataDecoder&, StaticValueType) const;
    void marshal (::CORBA::DataEncoder &, StaticValueType) const;
};


_Marshaller_ccmtools_corba_Components_CreateFailure::~_Marshaller_ccmtools_corba_Components_CreateFailure()
{
}

::CORBA::StaticValueType _Marshaller_ccmtools_corba_Components_CreateFailure::create() const
{
  return (StaticValueType) new _MICO_T;
}

void _Marshaller_ccmtools_corba_Components_CreateFailure::assign( StaticValueType d, const StaticValueType s ) const
{
  *(_MICO_T*) d = *(_MICO_T*) s;
}

void _Marshaller_ccmtools_corba_Components_CreateFailure::free( StaticValueType v ) const
{
  delete (_MICO_T*) v;
}

::CORBA::Boolean _Marshaller_ccmtools_corba_Components_CreateFailure::demarshal( ::CORBA::DataDecoder &dc, StaticValueType v ) const
{
  string repoid;
  return
    dc.except_begin( repoid ) &&
    CORBA::_stc_ulong->demarshal( dc, &((_MICO_T*)v)->reason ) &&
    dc.except_end();
}

void _Marshaller_ccmtools_corba_Components_CreateFailure::marshal( ::CORBA::DataEncoder &ec, StaticValueType v ) const
{
  ec.except_begin( "IDL:ccmtools/corba/Components/CreateFailure:1.0" );
  CORBA::_stc_ulong->marshal( ec, &((_MICO_T*)v)->reason );
  ec.except_end();
}

::CORBA::StaticTypeInfo *_marshaller_ccmtools_corba_Components_CreateFailure;

void ccmtools::corba::Components::CreateFailure::_throwit() const
{
  #ifdef HAVE_EXCEPTIONS
  #ifdef HAVE_STD_EH
  throw *this;
  #else
  throw CreateFailure_var( (ccmtools::corba::Components::CreateFailure*)_clone() );
  #endif
  #else
  CORBA::Exception::_throw_failed( _clone() );
  #endif
}

const char *ccmtools::corba::Components::CreateFailure::_repoid() const
{
  return "IDL:ccmtools/corba/Components/CreateFailure:1.0";
}

void ccmtools::corba::Components::CreateFailure::_encode( CORBA::DataEncoder &_en ) const
{
  _marshaller_ccmtools_corba_Components_CreateFailure->marshal( _en, (void*) this );
}

void ccmtools::corba::Components::CreateFailure::_encode_any( CORBA::Any & ) const
{
  // use --any to make this work!
  assert(0);
}

CORBA::Exception *ccmtools::corba::Components::CreateFailure::_clone() const
{
  return new CreateFailure( *this );
}

ccmtools::corba::Components::CreateFailure *ccmtools::corba::Components::CreateFailure::_downcast( CORBA::Exception *_ex )
{
  if( _ex && !strcmp( _ex->_repoid(), "IDL:ccmtools/corba/Components/CreateFailure:1.0" ) )
    return (CreateFailure *) _ex;
  return NULL;
}

const ccmtools::corba::Components::CreateFailure *ccmtools::corba::Components::CreateFailure::_downcast( const CORBA::Exception *_ex )
{
  if( _ex && !strcmp( _ex->_repoid(), "IDL:ccmtools/corba/Components/CreateFailure:1.0" ) )
    return (CreateFailure *) _ex;
  return NULL;
}



/*
 * Base interface for class KeylessCCMHome
 */

ccmtools::corba::Components::KeylessCCMHome::~KeylessCCMHome()
{
}

void *
ccmtools::corba::Components::KeylessCCMHome::_narrow_helper( const char *_repoid )
{
  if( strcmp( _repoid, "IDL:ccmtools/corba/Components/KeylessCCMHome:1.0" ) == 0 )
    return (void *)this;
  return NULL;
}

ccmtools::corba::Components::KeylessCCMHome_ptr
ccmtools::corba::Components::KeylessCCMHome::_narrow( CORBA::Object_ptr _obj )
{
  ccmtools::corba::Components::KeylessCCMHome_ptr _o;
  if( !CORBA::is_nil( _obj ) ) {
    void *_p;
    if( (_p = _obj->_narrow_helper( "IDL:ccmtools/corba/Components/KeylessCCMHome:1.0" )))
      return _duplicate( (ccmtools::corba::Components::KeylessCCMHome_ptr) _p );
    if (!strcmp (_obj->_repoid(), "IDL:ccmtools/corba/Components/KeylessCCMHome:1.0") || _obj->_is_a_remote ("IDL:ccmtools/corba/Components/KeylessCCMHome:1.0")) {
      _o = new ccmtools::corba::Components::KeylessCCMHome_stub;
      _o->CORBA::Object::operator=( *_obj );
      return _o;
    }
  }
  return _nil();
}

ccmtools::corba::Components::KeylessCCMHome_ptr
ccmtools::corba::Components::KeylessCCMHome::_narrow( CORBA::AbstractBase_ptr _obj )
{
  return _narrow (_obj->_to_object());
}

class _Marshaller_ccmtools_corba_Components_KeylessCCMHome : public ::CORBA::StaticTypeInfo {
    typedef ccmtools::corba::Components::KeylessCCMHome_ptr _MICO_T;
  public:
    ~_Marshaller_ccmtools_corba_Components_KeylessCCMHome();
    StaticValueType create () const;
    void assign (StaticValueType dst, const StaticValueType src) const;
    void free (StaticValueType) const;
    void release (StaticValueType) const;
    ::CORBA::Boolean demarshal (::CORBA::DataDecoder&, StaticValueType) const;
    void marshal (::CORBA::DataEncoder &, StaticValueType) const;
};


_Marshaller_ccmtools_corba_Components_KeylessCCMHome::~_Marshaller_ccmtools_corba_Components_KeylessCCMHome()
{
}

::CORBA::StaticValueType _Marshaller_ccmtools_corba_Components_KeylessCCMHome::create() const
{
  return (StaticValueType) new _MICO_T( 0 );
}

void _Marshaller_ccmtools_corba_Components_KeylessCCMHome::assign( StaticValueType d, const StaticValueType s ) const
{
  *(_MICO_T*) d = ::ccmtools::corba::Components::KeylessCCMHome::_duplicate( *(_MICO_T*) s );
}

void _Marshaller_ccmtools_corba_Components_KeylessCCMHome::free( StaticValueType v ) const
{
  ::CORBA::release( *(_MICO_T *) v );
  delete (_MICO_T*) v;
}

void _Marshaller_ccmtools_corba_Components_KeylessCCMHome::release( StaticValueType v ) const
{
  ::CORBA::release( *(_MICO_T *) v );
}

::CORBA::Boolean _Marshaller_ccmtools_corba_Components_KeylessCCMHome::demarshal( ::CORBA::DataDecoder &dc, StaticValueType v ) const
{
  ::CORBA::Object_ptr obj;
  if (!::CORBA::_stc_Object->demarshal(dc, &obj))
    return FALSE;
  *(_MICO_T *) v = ::ccmtools::corba::Components::KeylessCCMHome::_narrow( obj );
  ::CORBA::Boolean ret = ::CORBA::is_nil (obj) || !::CORBA::is_nil (*(_MICO_T *)v);
  ::CORBA::release (obj);
  return ret;
}

void _Marshaller_ccmtools_corba_Components_KeylessCCMHome::marshal( ::CORBA::DataEncoder &ec, StaticValueType v ) const
{
  ::CORBA::Object_ptr obj = *(_MICO_T *) v;
  ::CORBA::_stc_Object->marshal( ec, &obj );
}

::CORBA::StaticTypeInfo *_marshaller_ccmtools_corba_Components_KeylessCCMHome;


/*
 * Stub interface for class KeylessCCMHome
 */

ccmtools::corba::Components::KeylessCCMHome_stub::~KeylessCCMHome_stub()
{
}

#ifndef MICO_CONF_NO_POA

void *
POA_ccmtools::corba::Components::KeylessCCMHome::_narrow_helper (const char * repoid)
{
  if (strcmp (repoid, "IDL:ccmtools/corba/Components/KeylessCCMHome:1.0") == 0) {
    return (void *) this;
  }
  return NULL;
}

POA_ccmtools::corba::Components::KeylessCCMHome *
POA_ccmtools::corba::Components::KeylessCCMHome::_narrow (PortableServer::Servant serv) 
{
  void * p;
  if ((p = serv->_narrow_helper ("IDL:ccmtools/corba/Components/KeylessCCMHome:1.0")) != NULL) {
    serv->_add_ref ();
    return (POA_ccmtools::corba::Components::KeylessCCMHome *) p;
  }
  return NULL;
}

ccmtools::corba::Components::KeylessCCMHome_stub_clp::KeylessCCMHome_stub_clp ()
{
}

ccmtools::corba::Components::KeylessCCMHome_stub_clp::KeylessCCMHome_stub_clp (PortableServer::POA_ptr poa, CORBA::Object_ptr obj)
  : CORBA::Object(*obj), PortableServer::StubBase(poa)
{
}

ccmtools::corba::Components::KeylessCCMHome_stub_clp::~KeylessCCMHome_stub_clp ()
{
}

#endif // MICO_CONF_NO_POA

ccmtools::corba::Components::CCMObject_ptr ccmtools::corba::Components::KeylessCCMHome_stub::create_component()
{
  ccmtools::corba::Components::CCMObject_ptr _res = ccmtools::corba::Components::CCMObject::_nil();
  CORBA::StaticAny __res( _marshaller_ccmtools_corba_Components_CCMObject, &_res );

  CORBA::StaticRequest __req( this, "create_component" );
  __req.set_result( &__res );

  __req.invoke();

  mico_sii_throw( &__req, 
    _marshaller_ccmtools_corba_Components_CreateFailure, "IDL:ccmtools/corba/Components/CreateFailure:1.0",
    0);
  return _res;
}


#ifndef MICO_CONF_NO_POA

ccmtools::corba::Components::CCMObject_ptr
ccmtools::corba::Components::KeylessCCMHome_stub_clp::create_component()
{
  PortableServer::Servant _serv = _preinvoke ();
  if (_serv) {
    POA_ccmtools::corba::Components::KeylessCCMHome * _myserv = POA_ccmtools::corba::Components::KeylessCCMHome::_narrow (_serv);
    if (_myserv) {
      ccmtools::corba::Components::CCMObject_ptr __res;

      #ifdef HAVE_EXCEPTIONS
      try {
      #endif
        __res = _myserv->create_component();
      #ifdef HAVE_EXCEPTIONS
      }
      catch (...) {
        _myserv->_remove_ref();
        _postinvoke();
        throw;
      }
      #endif

      _myserv->_remove_ref();
      _postinvoke ();
      return __res;
    }
    _postinvoke ();
  }

  return ccmtools::corba::Components::KeylessCCMHome_stub::create_component();
}

#endif // MICO_CONF_NO_POA


#ifdef HAVE_EXPLICIT_STRUCT_OPS
ccmtools::corba::Components::HomeNotFound::HomeNotFound()
{
}

ccmtools::corba::Components::HomeNotFound::HomeNotFound( const HomeNotFound& _s )
{
}

ccmtools::corba::Components::HomeNotFound::~HomeNotFound()
{
}

ccmtools::corba::Components::HomeNotFound&
ccmtools::corba::Components::HomeNotFound::operator=( const HomeNotFound& _s )
{
  return *this;
}
#endif

class _Marshaller_ccmtools_corba_Components_HomeNotFound : public ::CORBA::StaticTypeInfo {
    typedef ::ccmtools::corba::Components::HomeNotFound _MICO_T;
  public:
    ~_Marshaller_ccmtools_corba_Components_HomeNotFound();
    StaticValueType create () const;
    void assign (StaticValueType dst, const StaticValueType src) const;
    void free (StaticValueType) const;
    ::CORBA::Boolean demarshal (::CORBA::DataDecoder&, StaticValueType) const;
    void marshal (::CORBA::DataEncoder &, StaticValueType) const;
};


_Marshaller_ccmtools_corba_Components_HomeNotFound::~_Marshaller_ccmtools_corba_Components_HomeNotFound()
{
}

::CORBA::StaticValueType _Marshaller_ccmtools_corba_Components_HomeNotFound::create() const
{
  return (StaticValueType) new _MICO_T;
}

void _Marshaller_ccmtools_corba_Components_HomeNotFound::assign( StaticValueType d, const StaticValueType s ) const
{
  *(_MICO_T*) d = *(_MICO_T*) s;
}

void _Marshaller_ccmtools_corba_Components_HomeNotFound::free( StaticValueType v ) const
{
  delete (_MICO_T*) v;
}

::CORBA::Boolean _Marshaller_ccmtools_corba_Components_HomeNotFound::demarshal( ::CORBA::DataDecoder &dc, StaticValueType v ) const
{
  string repoid;
  return
    dc.except_begin( repoid ) &&
    dc.except_end();
}

void _Marshaller_ccmtools_corba_Components_HomeNotFound::marshal( ::CORBA::DataEncoder &ec, StaticValueType v ) const
{
  ec.except_begin( "IDL:ccmtools/corba/Components/HomeNotFound:1.0" );
  ec.except_end();
}

::CORBA::StaticTypeInfo *_marshaller_ccmtools_corba_Components_HomeNotFound;

void ccmtools::corba::Components::HomeNotFound::_throwit() const
{
  #ifdef HAVE_EXCEPTIONS
  #ifdef HAVE_STD_EH
  throw *this;
  #else
  throw HomeNotFound_var( (ccmtools::corba::Components::HomeNotFound*)_clone() );
  #endif
  #else
  CORBA::Exception::_throw_failed( _clone() );
  #endif
}

const char *ccmtools::corba::Components::HomeNotFound::_repoid() const
{
  return "IDL:ccmtools/corba/Components/HomeNotFound:1.0";
}

void ccmtools::corba::Components::HomeNotFound::_encode( CORBA::DataEncoder &_en ) const
{
  _marshaller_ccmtools_corba_Components_HomeNotFound->marshal( _en, (void*) this );
}

void ccmtools::corba::Components::HomeNotFound::_encode_any( CORBA::Any & ) const
{
  // use --any to make this work!
  assert(0);
}

CORBA::Exception *ccmtools::corba::Components::HomeNotFound::_clone() const
{
  return new HomeNotFound( *this );
}

ccmtools::corba::Components::HomeNotFound *ccmtools::corba::Components::HomeNotFound::_downcast( CORBA::Exception *_ex )
{
  if( _ex && !strcmp( _ex->_repoid(), "IDL:ccmtools/corba/Components/HomeNotFound:1.0" ) )
    return (HomeNotFound *) _ex;
  return NULL;
}

const ccmtools::corba::Components::HomeNotFound *ccmtools::corba::Components::HomeNotFound::_downcast( const CORBA::Exception *_ex )
{
  if( _ex && !strcmp( _ex->_repoid(), "IDL:ccmtools/corba/Components/HomeNotFound:1.0" ) )
    return (HomeNotFound *) _ex;
  return NULL;
}


/*
 * Base interface for class HomeFinder
 */

ccmtools::corba::Components::HomeFinder::~HomeFinder()
{
}

void *
ccmtools::corba::Components::HomeFinder::_narrow_helper( const char *_repoid )
{
  if( strcmp( _repoid, "IDL:ccmtools/corba/Components/HomeFinder:1.0" ) == 0 )
    return (void *)this;
  return NULL;
}

ccmtools::corba::Components::HomeFinder_ptr
ccmtools::corba::Components::HomeFinder::_narrow( CORBA::Object_ptr _obj )
{
  ccmtools::corba::Components::HomeFinder_ptr _o;
  if( !CORBA::is_nil( _obj ) ) {
    void *_p;
    if( (_p = _obj->_narrow_helper( "IDL:ccmtools/corba/Components/HomeFinder:1.0" )))
      return _duplicate( (ccmtools::corba::Components::HomeFinder_ptr) _p );
    if (!strcmp (_obj->_repoid(), "IDL:ccmtools/corba/Components/HomeFinder:1.0") || _obj->_is_a_remote ("IDL:ccmtools/corba/Components/HomeFinder:1.0")) {
      _o = new ccmtools::corba::Components::HomeFinder_stub;
      _o->CORBA::Object::operator=( *_obj );
      return _o;
    }
  }
  return _nil();
}

ccmtools::corba::Components::HomeFinder_ptr
ccmtools::corba::Components::HomeFinder::_narrow( CORBA::AbstractBase_ptr _obj )
{
  return _narrow (_obj->_to_object());
}

class _Marshaller_ccmtools_corba_Components_HomeFinder : public ::CORBA::StaticTypeInfo {
    typedef ccmtools::corba::Components::HomeFinder_ptr _MICO_T;
  public:
    ~_Marshaller_ccmtools_corba_Components_HomeFinder();
    StaticValueType create () const;
    void assign (StaticValueType dst, const StaticValueType src) const;
    void free (StaticValueType) const;
    void release (StaticValueType) const;
    ::CORBA::Boolean demarshal (::CORBA::DataDecoder&, StaticValueType) const;
    void marshal (::CORBA::DataEncoder &, StaticValueType) const;
};


_Marshaller_ccmtools_corba_Components_HomeFinder::~_Marshaller_ccmtools_corba_Components_HomeFinder()
{
}

::CORBA::StaticValueType _Marshaller_ccmtools_corba_Components_HomeFinder::create() const
{
  return (StaticValueType) new _MICO_T( 0 );
}

void _Marshaller_ccmtools_corba_Components_HomeFinder::assign( StaticValueType d, const StaticValueType s ) const
{
  *(_MICO_T*) d = ::ccmtools::corba::Components::HomeFinder::_duplicate( *(_MICO_T*) s );
}

void _Marshaller_ccmtools_corba_Components_HomeFinder::free( StaticValueType v ) const
{
  ::CORBA::release( *(_MICO_T *) v );
  delete (_MICO_T*) v;
}

void _Marshaller_ccmtools_corba_Components_HomeFinder::release( StaticValueType v ) const
{
  ::CORBA::release( *(_MICO_T *) v );
}

::CORBA::Boolean _Marshaller_ccmtools_corba_Components_HomeFinder::demarshal( ::CORBA::DataDecoder &dc, StaticValueType v ) const
{
  ::CORBA::Object_ptr obj;
  if (!::CORBA::_stc_Object->demarshal(dc, &obj))
    return FALSE;
  *(_MICO_T *) v = ::ccmtools::corba::Components::HomeFinder::_narrow( obj );
  ::CORBA::Boolean ret = ::CORBA::is_nil (obj) || !::CORBA::is_nil (*(_MICO_T *)v);
  ::CORBA::release (obj);
  return ret;
}

void _Marshaller_ccmtools_corba_Components_HomeFinder::marshal( ::CORBA::DataEncoder &ec, StaticValueType v ) const
{
  ::CORBA::Object_ptr obj = *(_MICO_T *) v;
  ::CORBA::_stc_Object->marshal( ec, &obj );
}

::CORBA::StaticTypeInfo *_marshaller_ccmtools_corba_Components_HomeFinder;


/*
 * Stub interface for class HomeFinder
 */

ccmtools::corba::Components::HomeFinder_stub::~HomeFinder_stub()
{
}

#ifndef MICO_CONF_NO_POA

void *
POA_ccmtools::corba::Components::HomeFinder::_narrow_helper (const char * repoid)
{
  if (strcmp (repoid, "IDL:ccmtools/corba/Components/HomeFinder:1.0") == 0) {
    return (void *) this;
  }
  return NULL;
}

POA_ccmtools::corba::Components::HomeFinder *
POA_ccmtools::corba::Components::HomeFinder::_narrow (PortableServer::Servant serv) 
{
  void * p;
  if ((p = serv->_narrow_helper ("IDL:ccmtools/corba/Components/HomeFinder:1.0")) != NULL) {
    serv->_add_ref ();
    return (POA_ccmtools::corba::Components::HomeFinder *) p;
  }
  return NULL;
}

ccmtools::corba::Components::HomeFinder_stub_clp::HomeFinder_stub_clp ()
{
}

ccmtools::corba::Components::HomeFinder_stub_clp::HomeFinder_stub_clp (PortableServer::POA_ptr poa, CORBA::Object_ptr obj)
  : CORBA::Object(*obj), PortableServer::StubBase(poa)
{
}

ccmtools::corba::Components::HomeFinder_stub_clp::~HomeFinder_stub_clp ()
{
}

#endif // MICO_CONF_NO_POA

ccmtools::corba::Components::CCMHome_ptr ccmtools::corba::Components::HomeFinder_stub::find_home_by_component_type( const char* _par_comp_repid )
{
  CORBA::StaticAny _sa_comp_repid( CORBA::_stc_string, &_par_comp_repid );
  ccmtools::corba::Components::CCMHome_ptr _res = ccmtools::corba::Components::CCMHome::_nil();
  CORBA::StaticAny __res( _marshaller_ccmtools_corba_Components_CCMHome, &_res );

  CORBA::StaticRequest __req( this, "find_home_by_component_type" );
  __req.add_in_arg( &_sa_comp_repid );
  __req.set_result( &__res );

  __req.invoke();

  mico_sii_throw( &__req, 
    _marshaller_ccmtools_corba_Components_HomeNotFound, "IDL:ccmtools/corba/Components/HomeNotFound:1.0",
    0);
  return _res;
}


#ifndef MICO_CONF_NO_POA

ccmtools::corba::Components::CCMHome_ptr
ccmtools::corba::Components::HomeFinder_stub_clp::find_home_by_component_type( const char* _par_comp_repid )
{
  PortableServer::Servant _serv = _preinvoke ();
  if (_serv) {
    POA_ccmtools::corba::Components::HomeFinder * _myserv = POA_ccmtools::corba::Components::HomeFinder::_narrow (_serv);
    if (_myserv) {
      ccmtools::corba::Components::CCMHome_ptr __res;

      #ifdef HAVE_EXCEPTIONS
      try {
      #endif
        __res = _myserv->find_home_by_component_type(_par_comp_repid);
      #ifdef HAVE_EXCEPTIONS
      }
      catch (...) {
        _myserv->_remove_ref();
        _postinvoke();
        throw;
      }
      #endif

      _myserv->_remove_ref();
      _postinvoke ();
      return __res;
    }
    _postinvoke ();
  }

  return ccmtools::corba::Components::HomeFinder_stub::find_home_by_component_type(_par_comp_repid);
}

#endif // MICO_CONF_NO_POA

ccmtools::corba::Components::CCMHome_ptr ccmtools::corba::Components::HomeFinder_stub::find_home_by_home_type( const char* _par_home_repid )
{
  CORBA::StaticAny _sa_home_repid( CORBA::_stc_string, &_par_home_repid );
  ccmtools::corba::Components::CCMHome_ptr _res = ccmtools::corba::Components::CCMHome::_nil();
  CORBA::StaticAny __res( _marshaller_ccmtools_corba_Components_CCMHome, &_res );

  CORBA::StaticRequest __req( this, "find_home_by_home_type" );
  __req.add_in_arg( &_sa_home_repid );
  __req.set_result( &__res );

  __req.invoke();

  mico_sii_throw( &__req, 
    _marshaller_ccmtools_corba_Components_HomeNotFound, "IDL:ccmtools/corba/Components/HomeNotFound:1.0",
    0);
  return _res;
}


#ifndef MICO_CONF_NO_POA

ccmtools::corba::Components::CCMHome_ptr
ccmtools::corba::Components::HomeFinder_stub_clp::find_home_by_home_type( const char* _par_home_repid )
{
  PortableServer::Servant _serv = _preinvoke ();
  if (_serv) {
    POA_ccmtools::corba::Components::HomeFinder * _myserv = POA_ccmtools::corba::Components::HomeFinder::_narrow (_serv);
    if (_myserv) {
      ccmtools::corba::Components::CCMHome_ptr __res;

      #ifdef HAVE_EXCEPTIONS
      try {
      #endif
        __res = _myserv->find_home_by_home_type(_par_home_repid);
      #ifdef HAVE_EXCEPTIONS
      }
      catch (...) {
        _myserv->_remove_ref();
        _postinvoke();
        throw;
      }
      #endif

      _myserv->_remove_ref();
      _postinvoke ();
      return __res;
    }
    _postinvoke ();
  }

  return ccmtools::corba::Components::HomeFinder_stub::find_home_by_home_type(_par_home_repid);
}

#endif // MICO_CONF_NO_POA

ccmtools::corba::Components::CCMHome_ptr ccmtools::corba::Components::HomeFinder_stub::find_home_by_name( const char* _par_home_name )
{
  CORBA::StaticAny _sa_home_name( CORBA::_stc_string, &_par_home_name );
  ccmtools::corba::Components::CCMHome_ptr _res = ccmtools::corba::Components::CCMHome::_nil();
  CORBA::StaticAny __res( _marshaller_ccmtools_corba_Components_CCMHome, &_res );

  CORBA::StaticRequest __req( this, "find_home_by_name" );
  __req.add_in_arg( &_sa_home_name );
  __req.set_result( &__res );

  __req.invoke();

  mico_sii_throw( &__req, 
    _marshaller_ccmtools_corba_Components_HomeNotFound, "IDL:ccmtools/corba/Components/HomeNotFound:1.0",
    0);
  return _res;
}


#ifndef MICO_CONF_NO_POA

ccmtools::corba::Components::CCMHome_ptr
ccmtools::corba::Components::HomeFinder_stub_clp::find_home_by_name( const char* _par_home_name )
{
  PortableServer::Servant _serv = _preinvoke ();
  if (_serv) {
    POA_ccmtools::corba::Components::HomeFinder * _myserv = POA_ccmtools::corba::Components::HomeFinder::_narrow (_serv);
    if (_myserv) {
      ccmtools::corba::Components::CCMHome_ptr __res;

      #ifdef HAVE_EXCEPTIONS
      try {
      #endif
        __res = _myserv->find_home_by_name(_par_home_name);
      #ifdef HAVE_EXCEPTIONS
      }
      catch (...) {
        _myserv->_remove_ref();
        _postinvoke();
        throw;
      }
      #endif

      _myserv->_remove_ref();
      _postinvoke ();
      return __res;
    }
    _postinvoke ();
  }

  return ccmtools::corba::Components::HomeFinder_stub::find_home_by_name(_par_home_name);
}

#endif // MICO_CONF_NO_POA

class _Marshaller__seq_ccmtools_corba_Components_CCMHome : public ::CORBA::StaticTypeInfo {
    typedef IfaceSequenceTmpl< ccmtools::corba::Components::CCMHome_var,ccmtools::corba::Components::CCMHome_ptr> _MICO_T;
  public:
    ~_Marshaller__seq_ccmtools_corba_Components_CCMHome();
    StaticValueType create () const;
    void assign (StaticValueType dst, const StaticValueType src) const;
    void free (StaticValueType) const;
    ::CORBA::Boolean demarshal (::CORBA::DataDecoder&, StaticValueType) const;
    void marshal (::CORBA::DataEncoder &, StaticValueType) const;
};


_Marshaller__seq_ccmtools_corba_Components_CCMHome::~_Marshaller__seq_ccmtools_corba_Components_CCMHome()
{
}

::CORBA::StaticValueType _Marshaller__seq_ccmtools_corba_Components_CCMHome::create() const
{
  return (StaticValueType) new _MICO_T;
}

void _Marshaller__seq_ccmtools_corba_Components_CCMHome::assign( StaticValueType d, const StaticValueType s ) const
{
  *(_MICO_T*) d = *(_MICO_T*) s;
}

void _Marshaller__seq_ccmtools_corba_Components_CCMHome::free( StaticValueType v ) const
{
  delete (_MICO_T*) v;
}

::CORBA::Boolean _Marshaller__seq_ccmtools_corba_Components_CCMHome::demarshal( ::CORBA::DataDecoder &dc, StaticValueType v ) const
{
  ::CORBA::ULong len;
  if( !dc.seq_begin( len ) )
    return FALSE;
  ((_MICO_T *) v)->length( len );
  for( ::CORBA::ULong i = 0; i < len; i++ ) {
    if( !_marshaller_ccmtools_corba_Components_CCMHome->demarshal( dc, &(*(_MICO_T*)v)[i]._for_demarshal() ) )
      return FALSE;
  }
  return dc.seq_end();
}

void _Marshaller__seq_ccmtools_corba_Components_CCMHome::marshal( ::CORBA::DataEncoder &ec, StaticValueType v ) const
{
  ::CORBA::ULong len = ((_MICO_T *) v)->length();
  ec.seq_begin( len );
  for( ::CORBA::ULong i = 0; i < len; i++ )
    _marshaller_ccmtools_corba_Components_CCMHome->marshal( ec, &(*(_MICO_T*)v)[i].inout() );
  ec.seq_end();
}

::CORBA::StaticTypeInfo *_marshaller__seq_ccmtools_corba_Components_CCMHome;

struct __tc_init_CCMTOOLS {
  __tc_init_CCMTOOLS()
  {
    _marshaller_ccmtools_corba_Components_InvalidName = new _Marshaller_ccmtools_corba_Components_InvalidName;
    _marshaller_ccmtools_corba_Components_Navigation = new _Marshaller_ccmtools_corba_Components_Navigation;
    _marshaller_ccmtools_corba_Components_InvalidConnection = new _Marshaller_ccmtools_corba_Components_InvalidConnection;
    _marshaller_ccmtools_corba_Components_AlreadyConnected = new _Marshaller_ccmtools_corba_Components_AlreadyConnected;
    _marshaller_ccmtools_corba_Components_ExceededConnectionLimit = new _Marshaller_ccmtools_corba_Components_ExceededConnectionLimit;
    _marshaller_ccmtools_corba_Components_CookieRequired = new _Marshaller_ccmtools_corba_Components_CookieRequired;
    _marshaller_ccmtools_corba_Components_NoConnection = new _Marshaller_ccmtools_corba_Components_NoConnection;
    _marshaller_ccmtools_corba_Components_Cookie = new _Marshaller_ccmtools_corba_Components_Cookie;
    _marshaller_ccmtools_corba_Components_Receptacles = new _Marshaller_ccmtools_corba_Components_Receptacles;
    _marshaller_ccmtools_corba_Components_InvalidConfiguration = new _Marshaller_ccmtools_corba_Components_InvalidConfiguration;
    _marshaller_ccmtools_corba_Components_RemoveFailure = new _Marshaller_ccmtools_corba_Components_RemoveFailure;
    _marshaller_ccmtools_corba_Components_CCMHome = new _Marshaller_ccmtools_corba_Components_CCMHome;
    _marshaller_ccmtools_corba_Components_CCMObject = new _Marshaller_ccmtools_corba_Components_CCMObject;
    _marshaller_ccmtools_corba_Components_CreateFailure = new _Marshaller_ccmtools_corba_Components_CreateFailure;
    _marshaller_ccmtools_corba_Components_KeylessCCMHome = new _Marshaller_ccmtools_corba_Components_KeylessCCMHome;
    _marshaller_ccmtools_corba_Components_HomeNotFound = new _Marshaller_ccmtools_corba_Components_HomeNotFound;
    _marshaller_ccmtools_corba_Components_HomeFinder = new _Marshaller_ccmtools_corba_Components_HomeFinder;
    _marshaller__seq_ccmtools_corba_Components_CCMHome = new _Marshaller__seq_ccmtools_corba_Components_CCMHome;
  }

  ~__tc_init_CCMTOOLS()
  {
    delete static_cast<_Marshaller_ccmtools_corba_Components_InvalidName*>(_marshaller_ccmtools_corba_Components_InvalidName);
    delete static_cast<_Marshaller_ccmtools_corba_Components_Navigation*>(_marshaller_ccmtools_corba_Components_Navigation);
    delete static_cast<_Marshaller_ccmtools_corba_Components_InvalidConnection*>(_marshaller_ccmtools_corba_Components_InvalidConnection);
    delete static_cast<_Marshaller_ccmtools_corba_Components_AlreadyConnected*>(_marshaller_ccmtools_corba_Components_AlreadyConnected);
    delete static_cast<_Marshaller_ccmtools_corba_Components_ExceededConnectionLimit*>(_marshaller_ccmtools_corba_Components_ExceededConnectionLimit);
    delete static_cast<_Marshaller_ccmtools_corba_Components_CookieRequired*>(_marshaller_ccmtools_corba_Components_CookieRequired);
    delete static_cast<_Marshaller_ccmtools_corba_Components_NoConnection*>(_marshaller_ccmtools_corba_Components_NoConnection);
    delete static_cast<_Marshaller_ccmtools_corba_Components_Cookie*>(_marshaller_ccmtools_corba_Components_Cookie);
    delete static_cast<_Marshaller_ccmtools_corba_Components_Receptacles*>(_marshaller_ccmtools_corba_Components_Receptacles);
    delete static_cast<_Marshaller_ccmtools_corba_Components_InvalidConfiguration*>(_marshaller_ccmtools_corba_Components_InvalidConfiguration);
    delete static_cast<_Marshaller_ccmtools_corba_Components_RemoveFailure*>(_marshaller_ccmtools_corba_Components_RemoveFailure);
    delete static_cast<_Marshaller_ccmtools_corba_Components_CCMHome*>(_marshaller_ccmtools_corba_Components_CCMHome);
    delete static_cast<_Marshaller_ccmtools_corba_Components_CCMObject*>(_marshaller_ccmtools_corba_Components_CCMObject);
    delete static_cast<_Marshaller_ccmtools_corba_Components_CreateFailure*>(_marshaller_ccmtools_corba_Components_CreateFailure);
    delete static_cast<_Marshaller_ccmtools_corba_Components_KeylessCCMHome*>(_marshaller_ccmtools_corba_Components_KeylessCCMHome);
    delete static_cast<_Marshaller_ccmtools_corba_Components_HomeNotFound*>(_marshaller_ccmtools_corba_Components_HomeNotFound);
    delete static_cast<_Marshaller_ccmtools_corba_Components_HomeFinder*>(_marshaller_ccmtools_corba_Components_HomeFinder);
    delete static_cast<_Marshaller__seq_ccmtools_corba_Components_CCMHome*>(_marshaller__seq_ccmtools_corba_Components_CCMHome);
  }
};

static __tc_init_CCMTOOLS __init_CCMTOOLS;

//--------------------------------------------------------
//  Implementation of skeletons
//--------------------------------------------------------

// PortableServer Skeleton Class for interface ccmtools::corba::Components::Navigation
POA_ccmtools::corba::Components::Navigation::~Navigation()
{
}

::ccmtools::corba::Components::Navigation_ptr
POA_ccmtools::corba::Components::Navigation::_this ()
{
  CORBA::Object_var obj = PortableServer::ServantBase::_this();
  return ::ccmtools::corba::Components::Navigation::_narrow (obj);
}

CORBA::Boolean
POA_ccmtools::corba::Components::Navigation::_is_a (const char * repoid)
{
  if (strcmp (repoid, "IDL:ccmtools/corba/Components/Navigation:1.0") == 0) {
    return TRUE;
  }
  return FALSE;
}

CORBA::InterfaceDef_ptr
POA_ccmtools::corba::Components::Navigation::_get_interface ()
{
  CORBA::InterfaceDef_ptr ifd = PortableServer::ServantBase::_get_interface ("IDL:ccmtools/corba/Components/Navigation:1.0");

  if (CORBA::is_nil (ifd)) {
    mico_throw (CORBA::OBJ_ADAPTER (0, CORBA::COMPLETED_NO));
  }

  return ifd;
}

CORBA::RepositoryId
POA_ccmtools::corba::Components::Navigation::_primary_interface (const PortableServer::ObjectId &, PortableServer::POA_ptr)
{
  return CORBA::string_dup ("IDL:ccmtools/corba/Components/Navigation:1.0");
}

CORBA::Object_ptr
POA_ccmtools::corba::Components::Navigation::_make_stub (PortableServer::POA_ptr poa, CORBA::Object_ptr obj)
{
  return new ::ccmtools::corba::Components::Navigation_stub_clp (poa, obj);
}

bool
POA_ccmtools::corba::Components::Navigation::dispatch (CORBA::StaticServerRequest_ptr __req)
{
  #ifdef HAVE_EXCEPTIONS
  try {
  #endif
    if( strcmp( __req->op_name(), "provide_facet" ) == 0 ) {
      ::ccmtools::corba::Components::FeatureName_var _par_name;
      CORBA::StaticAny _sa_name( CORBA::_stc_string, &_par_name._for_demarshal() );

      CORBA::Object_ptr _res;
      CORBA::StaticAny __res( CORBA::_stc_Object, &_res );
      __req->add_in_arg( &_sa_name );
      __req->set_result( &__res );

      if( !__req->read_args() )
        return true;

      #ifdef HAVE_EXCEPTIONS
      try {
      #endif
        _res = provide_facet( _par_name.inout() );
      #ifdef HAVE_EXCEPTIONS
      } catch( ::ccmtools::corba::Components::InvalidName_catch &_ex ) {
        __req->set_exception( _ex->_clone() );
        __req->write_results();
        return true;
      }
      #endif
      __req->write_results();
      CORBA::release( _res );
      return true;
    }
  #ifdef HAVE_EXCEPTIONS
  } catch( CORBA::SystemException_catch &_ex ) {
    __req->set_exception( _ex->_clone() );
    __req->write_results();
    return true;
  } catch( ... ) {
    CORBA::UNKNOWN _ex (CORBA::OMGVMCID | 1, CORBA::COMPLETED_MAYBE);
    __req->set_exception (_ex->_clone());
    __req->write_results ();
    return true;
  }
  #endif

  return false;
}

void
POA_ccmtools::corba::Components::Navigation::invoke (CORBA::StaticServerRequest_ptr __req)
{
  if (dispatch (__req)) {
      return;
  }

  CORBA::Exception * ex = 
    new CORBA::BAD_OPERATION (0, CORBA::COMPLETED_NO);
  __req->set_exception (ex);
  __req->write_results();
}


// PortableServer Skeleton Class for interface ccmtools::corba::Components::Receptacles
POA_ccmtools::corba::Components::Receptacles::~Receptacles()
{
}

::ccmtools::corba::Components::Receptacles_ptr
POA_ccmtools::corba::Components::Receptacles::_this ()
{
  CORBA::Object_var obj = PortableServer::ServantBase::_this();
  return ::ccmtools::corba::Components::Receptacles::_narrow (obj);
}

CORBA::Boolean
POA_ccmtools::corba::Components::Receptacles::_is_a (const char * repoid)
{
  if (strcmp (repoid, "IDL:ccmtools/corba/Components/Receptacles:1.0") == 0) {
    return TRUE;
  }
  return FALSE;
}

CORBA::InterfaceDef_ptr
POA_ccmtools::corba::Components::Receptacles::_get_interface ()
{
  CORBA::InterfaceDef_ptr ifd = PortableServer::ServantBase::_get_interface ("IDL:ccmtools/corba/Components/Receptacles:1.0");

  if (CORBA::is_nil (ifd)) {
    mico_throw (CORBA::OBJ_ADAPTER (0, CORBA::COMPLETED_NO));
  }

  return ifd;
}

CORBA::RepositoryId
POA_ccmtools::corba::Components::Receptacles::_primary_interface (const PortableServer::ObjectId &, PortableServer::POA_ptr)
{
  return CORBA::string_dup ("IDL:ccmtools/corba/Components/Receptacles:1.0");
}

CORBA::Object_ptr
POA_ccmtools::corba::Components::Receptacles::_make_stub (PortableServer::POA_ptr poa, CORBA::Object_ptr obj)
{
  return new ::ccmtools::corba::Components::Receptacles_stub_clp (poa, obj);
}

bool
POA_ccmtools::corba::Components::Receptacles::dispatch (CORBA::StaticServerRequest_ptr __req)
{
  #ifdef HAVE_EXCEPTIONS
  try {
  #endif
    if( strcmp( __req->op_name(), "connect" ) == 0 ) {
      ::ccmtools::corba::Components::FeatureName_var _par_name;
      CORBA::StaticAny _sa_name( CORBA::_stc_string, &_par_name._for_demarshal() );
      CORBA::Object_var _par_connection;
      CORBA::StaticAny _sa_connection( CORBA::_stc_Object, &_par_connection._for_demarshal() );

      ::ccmtools::corba::Components::Cookie* _res;
      CORBA::StaticAny __res( _marshaller_ccmtools_corba_Components_Cookie, &_res );
      __req->add_in_arg( &_sa_name );
      __req->add_in_arg( &_sa_connection );
      __req->set_result( &__res );

      if( !__req->read_args() )
        return true;

      #ifdef HAVE_EXCEPTIONS
      try {
      #endif
        _res = connect( _par_name.inout(), _par_connection.inout() );
      #ifdef HAVE_EXCEPTIONS
      } catch( ::ccmtools::corba::Components::InvalidName_catch &_ex ) {
        __req->set_exception( _ex->_clone() );
        __req->write_results();
        return true;
      } catch( ::ccmtools::corba::Components::InvalidConnection_catch &_ex ) {
        __req->set_exception( _ex->_clone() );
        __req->write_results();
        return true;
      } catch( ::ccmtools::corba::Components::AlreadyConnected_catch &_ex ) {
        __req->set_exception( _ex->_clone() );
        __req->write_results();
        return true;
      } catch( ::ccmtools::corba::Components::ExceededConnectionLimit_catch &_ex ) {
        __req->set_exception( _ex->_clone() );
        __req->write_results();
        return true;
      }
      #endif
      __req->write_results();
      CORBA::remove_ref( _res );
      return true;
    }
    if( strcmp( __req->op_name(), "disconnect" ) == 0 ) {
      ::ccmtools::corba::Components::FeatureName_var _par_name;
      CORBA::StaticAny _sa_name( CORBA::_stc_string, &_par_name._for_demarshal() );
      ::ccmtools::corba::Components::Cookie_var _par_ck;
      CORBA::StaticAny _sa_ck( _marshaller_ccmtools_corba_Components_Cookie, &_par_ck._for_demarshal() );

      __req->add_in_arg( &_sa_name );
      __req->add_in_arg( &_sa_ck );

      if( !__req->read_args() )
        return true;

      #ifdef HAVE_EXCEPTIONS
      try {
      #endif
        disconnect( _par_name.inout(), _par_ck.inout() );
      #ifdef HAVE_EXCEPTIONS
      } catch( ::ccmtools::corba::Components::InvalidName_catch &_ex ) {
        __req->set_exception( _ex->_clone() );
        __req->write_results();
        return true;
      } catch( ::ccmtools::corba::Components::InvalidConnection_catch &_ex ) {
        __req->set_exception( _ex->_clone() );
        __req->write_results();
        return true;
      } catch( ::ccmtools::corba::Components::CookieRequired_catch &_ex ) {
        __req->set_exception( _ex->_clone() );
        __req->write_results();
        return true;
      } catch( ::ccmtools::corba::Components::NoConnection_catch &_ex ) {
        __req->set_exception( _ex->_clone() );
        __req->write_results();
        return true;
      }
      #endif
      __req->write_results();
      return true;
    }
  #ifdef HAVE_EXCEPTIONS
  } catch( CORBA::SystemException_catch &_ex ) {
    __req->set_exception( _ex->_clone() );
    __req->write_results();
    return true;
  } catch( ... ) {
    CORBA::UNKNOWN _ex (CORBA::OMGVMCID | 1, CORBA::COMPLETED_MAYBE);
    __req->set_exception (_ex->_clone());
    __req->write_results ();
    return true;
  }
  #endif

  return false;
}

void
POA_ccmtools::corba::Components::Receptacles::invoke (CORBA::StaticServerRequest_ptr __req)
{
  if (dispatch (__req)) {
      return;
  }

  CORBA::Exception * ex = 
    new CORBA::BAD_OPERATION (0, CORBA::COMPLETED_NO);
  __req->set_exception (ex);
  __req->write_results();
}


// PortableServer Skeleton Class for interface ccmtools::corba::Components::CCMHome
POA_ccmtools::corba::Components::CCMHome::~CCMHome()
{
}

::ccmtools::corba::Components::CCMHome_ptr
POA_ccmtools::corba::Components::CCMHome::_this ()
{
  CORBA::Object_var obj = PortableServer::ServantBase::_this();
  return ::ccmtools::corba::Components::CCMHome::_narrow (obj);
}

CORBA::Boolean
POA_ccmtools::corba::Components::CCMHome::_is_a (const char * repoid)
{
  if (strcmp (repoid, "IDL:ccmtools/corba/Components/CCMHome:1.0") == 0) {
    return TRUE;
  }
  return FALSE;
}

CORBA::InterfaceDef_ptr
POA_ccmtools::corba::Components::CCMHome::_get_interface ()
{
  CORBA::InterfaceDef_ptr ifd = PortableServer::ServantBase::_get_interface ("IDL:ccmtools/corba/Components/CCMHome:1.0");

  if (CORBA::is_nil (ifd)) {
    mico_throw (CORBA::OBJ_ADAPTER (0, CORBA::COMPLETED_NO));
  }

  return ifd;
}

CORBA::RepositoryId
POA_ccmtools::corba::Components::CCMHome::_primary_interface (const PortableServer::ObjectId &, PortableServer::POA_ptr)
{
  return CORBA::string_dup ("IDL:ccmtools/corba/Components/CCMHome:1.0");
}

CORBA::Object_ptr
POA_ccmtools::corba::Components::CCMHome::_make_stub (PortableServer::POA_ptr poa, CORBA::Object_ptr obj)
{
  return new ::ccmtools::corba::Components::CCMHome_stub_clp (poa, obj);
}

bool
POA_ccmtools::corba::Components::CCMHome::dispatch (CORBA::StaticServerRequest_ptr __req)
{
  #ifdef HAVE_EXCEPTIONS
  try {
  #endif
    if( strcmp( __req->op_name(), "remove_component" ) == 0 ) {
      ::ccmtools::corba::Components::CCMObject_var _par_comp;
      CORBA::StaticAny _sa_comp( _marshaller_ccmtools_corba_Components_CCMObject, &_par_comp._for_demarshal() );

      __req->add_in_arg( &_sa_comp );

      if( !__req->read_args() )
        return true;

      #ifdef HAVE_EXCEPTIONS
      try {
      #endif
        remove_component( _par_comp.inout() );
      #ifdef HAVE_EXCEPTIONS
      } catch( ::ccmtools::corba::Components::RemoveFailure_catch &_ex ) {
        __req->set_exception( _ex->_clone() );
        __req->write_results();
        return true;
      }
      #endif
      __req->write_results();
      return true;
    }
  #ifdef HAVE_EXCEPTIONS
  } catch( CORBA::SystemException_catch &_ex ) {
    __req->set_exception( _ex->_clone() );
    __req->write_results();
    return true;
  } catch( ... ) {
    CORBA::UNKNOWN _ex (CORBA::OMGVMCID | 1, CORBA::COMPLETED_MAYBE);
    __req->set_exception (_ex->_clone());
    __req->write_results ();
    return true;
  }
  #endif

  return false;
}

void
POA_ccmtools::corba::Components::CCMHome::invoke (CORBA::StaticServerRequest_ptr __req)
{
  if (dispatch (__req)) {
      return;
  }

  CORBA::Exception * ex = 
    new CORBA::BAD_OPERATION (0, CORBA::COMPLETED_NO);
  __req->set_exception (ex);
  __req->write_results();
}


// PortableServer Skeleton Class for interface ccmtools::corba::Components::CCMObject
POA_ccmtools::corba::Components::CCMObject::~CCMObject()
{
}

::ccmtools::corba::Components::CCMObject_ptr
POA_ccmtools::corba::Components::CCMObject::_this ()
{
  CORBA::Object_var obj = PortableServer::ServantBase::_this();
  return ::ccmtools::corba::Components::CCMObject::_narrow (obj);
}

CORBA::Boolean
POA_ccmtools::corba::Components::CCMObject::_is_a (const char * repoid)
{
  if (strcmp (repoid, "IDL:ccmtools/corba/Components/CCMObject:1.0") == 0) {
    return TRUE;
  }
  if (POA_ccmtools::corba::Components::Navigation::_is_a (repoid)) {
    return TRUE;
  }
  if (POA_ccmtools::corba::Components::Receptacles::_is_a (repoid)) {
    return TRUE;
  }
  return FALSE;
}

CORBA::InterfaceDef_ptr
POA_ccmtools::corba::Components::CCMObject::_get_interface ()
{
  CORBA::InterfaceDef_ptr ifd = PortableServer::ServantBase::_get_interface ("IDL:ccmtools/corba/Components/CCMObject:1.0");

  if (CORBA::is_nil (ifd)) {
    mico_throw (CORBA::OBJ_ADAPTER (0, CORBA::COMPLETED_NO));
  }

  return ifd;
}

CORBA::RepositoryId
POA_ccmtools::corba::Components::CCMObject::_primary_interface (const PortableServer::ObjectId &, PortableServer::POA_ptr)
{
  return CORBA::string_dup ("IDL:ccmtools/corba/Components/CCMObject:1.0");
}

CORBA::Object_ptr
POA_ccmtools::corba::Components::CCMObject::_make_stub (PortableServer::POA_ptr poa, CORBA::Object_ptr obj)
{
  return new ::ccmtools::corba::Components::CCMObject_stub_clp (poa, obj);
}

bool
POA_ccmtools::corba::Components::CCMObject::dispatch (CORBA::StaticServerRequest_ptr __req)
{
  #ifdef HAVE_EXCEPTIONS
  try {
  #endif
    if( strcmp( __req->op_name(), "get_ccm_home" ) == 0 ) {
      ::ccmtools::corba::Components::CCMHome_ptr _res;
      CORBA::StaticAny __res( _marshaller_ccmtools_corba_Components_CCMHome, &_res );
      __req->set_result( &__res );

      if( !__req->read_args() )
        return true;

      _res = get_ccm_home();
      __req->write_results();
      CORBA::release( _res );
      return true;
    }
    if( strcmp( __req->op_name(), "configuration_complete" ) == 0 ) {

      if( !__req->read_args() )
        return true;

      #ifdef HAVE_EXCEPTIONS
      try {
      #endif
        configuration_complete();
      #ifdef HAVE_EXCEPTIONS
      } catch( ::ccmtools::corba::Components::InvalidConfiguration_catch &_ex ) {
        __req->set_exception( _ex->_clone() );
        __req->write_results();
        return true;
      }
      #endif
      __req->write_results();
      return true;
    }
    if( strcmp( __req->op_name(), "remove" ) == 0 ) {

      if( !__req->read_args() )
        return true;

      #ifdef HAVE_EXCEPTIONS
      try {
      #endif
        remove();
      #ifdef HAVE_EXCEPTIONS
      } catch( ::ccmtools::corba::Components::RemoveFailure_catch &_ex ) {
        __req->set_exception( _ex->_clone() );
        __req->write_results();
        return true;
      }
      #endif
      __req->write_results();
      return true;
    }
  #ifdef HAVE_EXCEPTIONS
  } catch( CORBA::SystemException_catch &_ex ) {
    __req->set_exception( _ex->_clone() );
    __req->write_results();
    return true;
  } catch( ... ) {
    CORBA::UNKNOWN _ex (CORBA::OMGVMCID | 1, CORBA::COMPLETED_MAYBE);
    __req->set_exception (_ex->_clone());
    __req->write_results ();
    return true;
  }
  #endif

  if (POA_ccmtools::corba::Components::Navigation::dispatch (__req)) {
    return true;
  }

  if (POA_ccmtools::corba::Components::Receptacles::dispatch (__req)) {
    return true;
  }

  return false;
}

void
POA_ccmtools::corba::Components::CCMObject::invoke (CORBA::StaticServerRequest_ptr __req)
{
  if (dispatch (__req)) {
      return;
  }

  CORBA::Exception * ex = 
    new CORBA::BAD_OPERATION (0, CORBA::COMPLETED_NO);
  __req->set_exception (ex);
  __req->write_results();
}


// PortableServer Skeleton Class for interface ccmtools::corba::Components::KeylessCCMHome
POA_ccmtools::corba::Components::KeylessCCMHome::~KeylessCCMHome()
{
}

::ccmtools::corba::Components::KeylessCCMHome_ptr
POA_ccmtools::corba::Components::KeylessCCMHome::_this ()
{
  CORBA::Object_var obj = PortableServer::ServantBase::_this();
  return ::ccmtools::corba::Components::KeylessCCMHome::_narrow (obj);
}

CORBA::Boolean
POA_ccmtools::corba::Components::KeylessCCMHome::_is_a (const char * repoid)
{
  if (strcmp (repoid, "IDL:ccmtools/corba/Components/KeylessCCMHome:1.0") == 0) {
    return TRUE;
  }
  return FALSE;
}

CORBA::InterfaceDef_ptr
POA_ccmtools::corba::Components::KeylessCCMHome::_get_interface ()
{
  CORBA::InterfaceDef_ptr ifd = PortableServer::ServantBase::_get_interface ("IDL:ccmtools/corba/Components/KeylessCCMHome:1.0");

  if (CORBA::is_nil (ifd)) {
    mico_throw (CORBA::OBJ_ADAPTER (0, CORBA::COMPLETED_NO));
  }

  return ifd;
}

CORBA::RepositoryId
POA_ccmtools::corba::Components::KeylessCCMHome::_primary_interface (const PortableServer::ObjectId &, PortableServer::POA_ptr)
{
  return CORBA::string_dup ("IDL:ccmtools/corba/Components/KeylessCCMHome:1.0");
}

CORBA::Object_ptr
POA_ccmtools::corba::Components::KeylessCCMHome::_make_stub (PortableServer::POA_ptr poa, CORBA::Object_ptr obj)
{
  return new ::ccmtools::corba::Components::KeylessCCMHome_stub_clp (poa, obj);
}

bool
POA_ccmtools::corba::Components::KeylessCCMHome::dispatch (CORBA::StaticServerRequest_ptr __req)
{
  #ifdef HAVE_EXCEPTIONS
  try {
  #endif
    if( strcmp( __req->op_name(), "create_component" ) == 0 ) {
      ::ccmtools::corba::Components::CCMObject_ptr _res;
      CORBA::StaticAny __res( _marshaller_ccmtools_corba_Components_CCMObject, &_res );
      __req->set_result( &__res );

      if( !__req->read_args() )
        return true;

      #ifdef HAVE_EXCEPTIONS
      try {
      #endif
        _res = create_component();
      #ifdef HAVE_EXCEPTIONS
      } catch( ::ccmtools::corba::Components::CreateFailure_catch &_ex ) {
        __req->set_exception( _ex->_clone() );
        __req->write_results();
        return true;
      }
      #endif
      __req->write_results();
      CORBA::release( _res );
      return true;
    }
  #ifdef HAVE_EXCEPTIONS
  } catch( CORBA::SystemException_catch &_ex ) {
    __req->set_exception( _ex->_clone() );
    __req->write_results();
    return true;
  } catch( ... ) {
    CORBA::UNKNOWN _ex (CORBA::OMGVMCID | 1, CORBA::COMPLETED_MAYBE);
    __req->set_exception (_ex->_clone());
    __req->write_results ();
    return true;
  }
  #endif

  return false;
}

void
POA_ccmtools::corba::Components::KeylessCCMHome::invoke (CORBA::StaticServerRequest_ptr __req)
{
  if (dispatch (__req)) {
      return;
  }

  CORBA::Exception * ex = 
    new CORBA::BAD_OPERATION (0, CORBA::COMPLETED_NO);
  __req->set_exception (ex);
  __req->write_results();
}


// PortableServer Skeleton Class for interface ccmtools::corba::Components::HomeFinder
POA_ccmtools::corba::Components::HomeFinder::~HomeFinder()
{
}

::ccmtools::corba::Components::HomeFinder_ptr
POA_ccmtools::corba::Components::HomeFinder::_this ()
{
  CORBA::Object_var obj = PortableServer::ServantBase::_this();
  return ::ccmtools::corba::Components::HomeFinder::_narrow (obj);
}

CORBA::Boolean
POA_ccmtools::corba::Components::HomeFinder::_is_a (const char * repoid)
{
  if (strcmp (repoid, "IDL:ccmtools/corba/Components/HomeFinder:1.0") == 0) {
    return TRUE;
  }
  return FALSE;
}

CORBA::InterfaceDef_ptr
POA_ccmtools::corba::Components::HomeFinder::_get_interface ()
{
  CORBA::InterfaceDef_ptr ifd = PortableServer::ServantBase::_get_interface ("IDL:ccmtools/corba/Components/HomeFinder:1.0");

  if (CORBA::is_nil (ifd)) {
    mico_throw (CORBA::OBJ_ADAPTER (0, CORBA::COMPLETED_NO));
  }

  return ifd;
}

CORBA::RepositoryId
POA_ccmtools::corba::Components::HomeFinder::_primary_interface (const PortableServer::ObjectId &, PortableServer::POA_ptr)
{
  return CORBA::string_dup ("IDL:ccmtools/corba/Components/HomeFinder:1.0");
}

CORBA::Object_ptr
POA_ccmtools::corba::Components::HomeFinder::_make_stub (PortableServer::POA_ptr poa, CORBA::Object_ptr obj)
{
  return new ::ccmtools::corba::Components::HomeFinder_stub_clp (poa, obj);
}

bool
POA_ccmtools::corba::Components::HomeFinder::dispatch (CORBA::StaticServerRequest_ptr __req)
{
  #ifdef HAVE_EXCEPTIONS
  try {
  #endif
    if( strcmp( __req->op_name(), "find_home_by_component_type" ) == 0 ) {
      CORBA::String_var _par_comp_repid;
      CORBA::StaticAny _sa_comp_repid( CORBA::_stc_string, &_par_comp_repid._for_demarshal() );

      ::ccmtools::corba::Components::CCMHome_ptr _res;
      CORBA::StaticAny __res( _marshaller_ccmtools_corba_Components_CCMHome, &_res );
      __req->add_in_arg( &_sa_comp_repid );
      __req->set_result( &__res );

      if( !__req->read_args() )
        return true;

      #ifdef HAVE_EXCEPTIONS
      try {
      #endif
        _res = find_home_by_component_type( _par_comp_repid.inout() );
      #ifdef HAVE_EXCEPTIONS
      } catch( ::ccmtools::corba::Components::HomeNotFound_catch &_ex ) {
        __req->set_exception( _ex->_clone() );
        __req->write_results();
        return true;
      }
      #endif
      __req->write_results();
      CORBA::release( _res );
      return true;
    }
    if( strcmp( __req->op_name(), "find_home_by_home_type" ) == 0 ) {
      CORBA::String_var _par_home_repid;
      CORBA::StaticAny _sa_home_repid( CORBA::_stc_string, &_par_home_repid._for_demarshal() );

      ::ccmtools::corba::Components::CCMHome_ptr _res;
      CORBA::StaticAny __res( _marshaller_ccmtools_corba_Components_CCMHome, &_res );
      __req->add_in_arg( &_sa_home_repid );
      __req->set_result( &__res );

      if( !__req->read_args() )
        return true;

      #ifdef HAVE_EXCEPTIONS
      try {
      #endif
        _res = find_home_by_home_type( _par_home_repid.inout() );
      #ifdef HAVE_EXCEPTIONS
      } catch( ::ccmtools::corba::Components::HomeNotFound_catch &_ex ) {
        __req->set_exception( _ex->_clone() );
        __req->write_results();
        return true;
      }
      #endif
      __req->write_results();
      CORBA::release( _res );
      return true;
    }
    if( strcmp( __req->op_name(), "find_home_by_name" ) == 0 ) {
      CORBA::String_var _par_home_name;
      CORBA::StaticAny _sa_home_name( CORBA::_stc_string, &_par_home_name._for_demarshal() );

      ::ccmtools::corba::Components::CCMHome_ptr _res;
      CORBA::StaticAny __res( _marshaller_ccmtools_corba_Components_CCMHome, &_res );
      __req->add_in_arg( &_sa_home_name );
      __req->set_result( &__res );

      if( !__req->read_args() )
        return true;

      #ifdef HAVE_EXCEPTIONS
      try {
      #endif
        _res = find_home_by_name( _par_home_name.inout() );
      #ifdef HAVE_EXCEPTIONS
      } catch( ::ccmtools::corba::Components::HomeNotFound_catch &_ex ) {
        __req->set_exception( _ex->_clone() );
        __req->write_results();
        return true;
      }
      #endif
      __req->write_results();
      CORBA::release( _res );
      return true;
    }
  #ifdef HAVE_EXCEPTIONS
  } catch( CORBA::SystemException_catch &_ex ) {
    __req->set_exception( _ex->_clone() );
    __req->write_results();
    return true;
  } catch( ... ) {
    CORBA::UNKNOWN _ex (CORBA::OMGVMCID | 1, CORBA::COMPLETED_MAYBE);
    __req->set_exception (_ex->_clone());
    __req->write_results ();
    return true;
  }
  #endif

  return false;
}

void
POA_ccmtools::corba::Components::HomeFinder::invoke (CORBA::StaticServerRequest_ptr __req)
{
  if (dispatch (__req)) {
      return;
  }

  CORBA::Exception * ex = 
    new CORBA::BAD_OPERATION (0, CORBA::COMPLETED_NO);
  __req->set_exception (ex);
  __req->write_results();
}

