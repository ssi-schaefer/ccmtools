/*
 *  MICO --- an Open Source CORBA implementation
 *  Copyright (c) 1997-2003 by The Mico Team
 *
 *  This file was automatically generated. DO NOT EDIT!
 */

#include "Benchmark.h"


using namespace std;

//--------------------------------------------------------
//  Implementation of stubs
//--------------------------------------------------------


/*
 * Base interface for class Benchmark
 */

Benchmark::~Benchmark()
{
}

void *
Benchmark::_narrow_helper( const char *_repoid )
{
  if( strcmp( _repoid, "IDL:Benchmark:1.0" ) == 0 )
    return (void *)this;
  return NULL;
}

Benchmark_ptr
Benchmark::_narrow( CORBA::Object_ptr _obj )
{
  Benchmark_ptr _o;
  if( !CORBA::is_nil( _obj ) ) {
    void *_p;
    if( (_p = _obj->_narrow_helper( "IDL:Benchmark:1.0" )))
      return _duplicate( (Benchmark_ptr) _p );
    if (!strcmp (_obj->_repoid(), "IDL:Benchmark:1.0") || _obj->_is_a_remote ("IDL:Benchmark:1.0")) {
      _o = new Benchmark_stub;
      _o->CORBA::Object::operator=( *_obj );
      return _o;
    }
  }
  return _nil();
}

Benchmark_ptr
Benchmark::_narrow( CORBA::AbstractBase_ptr _obj )
{
  return _narrow (_obj->_to_object());
}

class _Marshaller_Benchmark : public ::CORBA::StaticTypeInfo {
    typedef Benchmark_ptr _MICO_T;
  public:
    ~_Marshaller_Benchmark();
    StaticValueType create () const;
    void assign (StaticValueType dst, const StaticValueType src) const;
    void free (StaticValueType) const;
    void release (StaticValueType) const;
    ::CORBA::Boolean demarshal (::CORBA::DataDecoder&, StaticValueType) const;
    void marshal (::CORBA::DataEncoder &, StaticValueType) const;
};


_Marshaller_Benchmark::~_Marshaller_Benchmark()
{
}

::CORBA::StaticValueType _Marshaller_Benchmark::create() const
{
  return (StaticValueType) new _MICO_T( 0 );
}

void _Marshaller_Benchmark::assign( StaticValueType d, const StaticValueType s ) const
{
  *(_MICO_T*) d = ::Benchmark::_duplicate( *(_MICO_T*) s );
}

void _Marshaller_Benchmark::free( StaticValueType v ) const
{
  ::CORBA::release( *(_MICO_T *) v );
  delete (_MICO_T*) v;
}

void _Marshaller_Benchmark::release( StaticValueType v ) const
{
  ::CORBA::release( *(_MICO_T *) v );
}

::CORBA::Boolean _Marshaller_Benchmark::demarshal( ::CORBA::DataDecoder &dc, StaticValueType v ) const
{
  ::CORBA::Object_ptr obj;
  if (!::CORBA::_stc_Object->demarshal(dc, &obj))
    return FALSE;
  *(_MICO_T *) v = ::Benchmark::_narrow( obj );
  ::CORBA::Boolean ret = ::CORBA::is_nil (obj) || !::CORBA::is_nil (*(_MICO_T *)v);
  ::CORBA::release (obj);
  return ret;
}

void _Marshaller_Benchmark::marshal( ::CORBA::DataEncoder &ec, StaticValueType v ) const
{
  ::CORBA::Object_ptr obj = *(_MICO_T *) v;
  ::CORBA::_stc_Object->marshal( ec, &obj );
}

::CORBA::StaticTypeInfo *_marshaller_Benchmark;


/*
 * Stub interface for class Benchmark
 */

Benchmark_stub::~Benchmark_stub()
{
}

#ifndef MICO_CONF_NO_POA

void *
POA_Benchmark::_narrow_helper (const char * repoid)
{
  if (strcmp (repoid, "IDL:Benchmark:1.0") == 0) {
    return (void *) this;
  }
  return NULL;
}

POA_Benchmark *
POA_Benchmark::_narrow (PortableServer::Servant serv) 
{
  void * p;
  if ((p = serv->_narrow_helper ("IDL:Benchmark:1.0")) != NULL) {
    serv->_add_ref ();
    return (POA_Benchmark *) p;
  }
  return NULL;
}

Benchmark_stub_clp::Benchmark_stub_clp ()
{
}

Benchmark_stub_clp::Benchmark_stub_clp (PortableServer::POA_ptr poa, CORBA::Object_ptr obj)
  : CORBA::Object(*obj), PortableServer::StubBase(poa)
{
}

Benchmark_stub_clp::~Benchmark_stub_clp ()
{
}

#endif // MICO_CONF_NO_POA

void Benchmark_stub::f0()
{
  CORBA::StaticRequest __req( this, "f0" );

  __req.invoke();

  mico_sii_throw( &__req, 
    0);
}


#ifndef MICO_CONF_NO_POA

void
Benchmark_stub_clp::f0()
{
  PortableServer::Servant _serv = _preinvoke ();
  if (_serv) {
    POA_Benchmark * _myserv = POA_Benchmark::_narrow (_serv);
    if (_myserv) {
      #ifdef HAVE_EXCEPTIONS
      try {
      #endif
        _myserv->f0();
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

  Benchmark_stub::f0();
}

#endif // MICO_CONF_NO_POA

void Benchmark_stub::f_in1( CORBA::Long _par_l1 )
{
  CORBA::StaticAny _sa_l1( CORBA::_stc_long, &_par_l1 );
  CORBA::StaticRequest __req( this, "f_in1" );
  __req.add_in_arg( &_sa_l1 );

  __req.invoke();

  mico_sii_throw( &__req, 
    0);
}


#ifndef MICO_CONF_NO_POA

void
Benchmark_stub_clp::f_in1( CORBA::Long _par_l1 )
{
  PortableServer::Servant _serv = _preinvoke ();
  if (_serv) {
    POA_Benchmark * _myserv = POA_Benchmark::_narrow (_serv);
    if (_myserv) {
      #ifdef HAVE_EXCEPTIONS
      try {
      #endif
        _myserv->f_in1(_par_l1);
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

  Benchmark_stub::f_in1(_par_l1);
}

#endif // MICO_CONF_NO_POA

void Benchmark_stub::f_in2( const char* _par_s1 )
{
  CORBA::StaticAny _sa_s1( CORBA::_stc_string, &_par_s1 );
  CORBA::StaticRequest __req( this, "f_in2" );
  __req.add_in_arg( &_sa_s1 );

  __req.invoke();

  mico_sii_throw( &__req, 
    0);
}


#ifndef MICO_CONF_NO_POA

void
Benchmark_stub_clp::f_in2( const char* _par_s1 )
{
  PortableServer::Servant _serv = _preinvoke ();
  if (_serv) {
    POA_Benchmark * _myserv = POA_Benchmark::_narrow (_serv);
    if (_myserv) {
      #ifdef HAVE_EXCEPTIONS
      try {
      #endif
        _myserv->f_in2(_par_s1);
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

  Benchmark_stub::f_in2(_par_s1);
}

#endif // MICO_CONF_NO_POA

void Benchmark_stub::f_in3( const LongList& _par_ll1 )
{
  CORBA::StaticAny _sa_ll1( CORBA::_stcseq_long, &_par_ll1 );
  CORBA::StaticRequest __req( this, "f_in3" );
  __req.add_in_arg( &_sa_ll1 );

  __req.invoke();

  mico_sii_throw( &__req, 
    0);
}


#ifndef MICO_CONF_NO_POA

void
Benchmark_stub_clp::f_in3( const LongList& _par_ll1 )
{
  PortableServer::Servant _serv = _preinvoke ();
  if (_serv) {
    POA_Benchmark * _myserv = POA_Benchmark::_narrow (_serv);
    if (_myserv) {
      #ifdef HAVE_EXCEPTIONS
      try {
      #endif
        _myserv->f_in3(_par_ll1);
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

  Benchmark_stub::f_in3(_par_ll1);
}

#endif // MICO_CONF_NO_POA

void Benchmark_stub::f_inout1( CORBA::Long& _par_l1 )
{
  CORBA::StaticAny _sa_l1( CORBA::_stc_long, &_par_l1 );
  CORBA::StaticRequest __req( this, "f_inout1" );
  __req.add_inout_arg( &_sa_l1 );

  __req.invoke();

  mico_sii_throw( &__req, 
    0);
}


#ifndef MICO_CONF_NO_POA

void
Benchmark_stub_clp::f_inout1( CORBA::Long& _par_l1 )
{
  PortableServer::Servant _serv = _preinvoke ();
  if (_serv) {
    POA_Benchmark * _myserv = POA_Benchmark::_narrow (_serv);
    if (_myserv) {
      #ifdef HAVE_EXCEPTIONS
      try {
      #endif
        _myserv->f_inout1(_par_l1);
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

  Benchmark_stub::f_inout1(_par_l1);
}

#endif // MICO_CONF_NO_POA

void Benchmark_stub::f_inout2( char*& _par_s1 )
{
  CORBA::StaticAny _sa_s1( CORBA::_stc_string, &_par_s1 );
  CORBA::StaticRequest __req( this, "f_inout2" );
  __req.add_inout_arg( &_sa_s1 );

  __req.invoke();

  mico_sii_throw( &__req, 
    0);
}


#ifndef MICO_CONF_NO_POA

void
Benchmark_stub_clp::f_inout2( char*& _par_s1 )
{
  PortableServer::Servant _serv = _preinvoke ();
  if (_serv) {
    POA_Benchmark * _myserv = POA_Benchmark::_narrow (_serv);
    if (_myserv) {
      #ifdef HAVE_EXCEPTIONS
      try {
      #endif
        _myserv->f_inout2(_par_s1);
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

  Benchmark_stub::f_inout2(_par_s1);
}

#endif // MICO_CONF_NO_POA

void Benchmark_stub::f_inout3( LongList& _par_ll1 )
{
  CORBA::StaticAny _sa_ll1( CORBA::_stcseq_long, &_par_ll1 );
  CORBA::StaticRequest __req( this, "f_inout3" );
  __req.add_inout_arg( &_sa_ll1 );

  __req.invoke();

  mico_sii_throw( &__req, 
    0);
}


#ifndef MICO_CONF_NO_POA

void
Benchmark_stub_clp::f_inout3( LongList& _par_ll1 )
{
  PortableServer::Servant _serv = _preinvoke ();
  if (_serv) {
    POA_Benchmark * _myserv = POA_Benchmark::_narrow (_serv);
    if (_myserv) {
      #ifdef HAVE_EXCEPTIONS
      try {
      #endif
        _myserv->f_inout3(_par_ll1);
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

  Benchmark_stub::f_inout3(_par_ll1);
}

#endif // MICO_CONF_NO_POA

void Benchmark_stub::f_out1( CORBA::Long_out _par_l1 )
{
  CORBA::StaticAny _sa_l1( CORBA::_stc_long, &_par_l1 );
  CORBA::StaticRequest __req( this, "f_out1" );
  __req.add_out_arg( &_sa_l1 );

  __req.invoke();

  mico_sii_throw( &__req, 
    0);
}


#ifndef MICO_CONF_NO_POA

void
Benchmark_stub_clp::f_out1( CORBA::Long_out _par_l1 )
{
  PortableServer::Servant _serv = _preinvoke ();
  if (_serv) {
    POA_Benchmark * _myserv = POA_Benchmark::_narrow (_serv);
    if (_myserv) {
      #ifdef HAVE_EXCEPTIONS
      try {
      #endif
        _myserv->f_out1(_par_l1);
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

  Benchmark_stub::f_out1(_par_l1);
}

#endif // MICO_CONF_NO_POA

void Benchmark_stub::f_out2( CORBA::String_out _par_s1 )
{
  CORBA::StaticAny _sa_s1( CORBA::_stc_string, &_par_s1.ptr() );
  CORBA::StaticRequest __req( this, "f_out2" );
  __req.add_out_arg( &_sa_s1 );

  __req.invoke();

  mico_sii_throw( &__req, 
    0);
}


#ifndef MICO_CONF_NO_POA

void
Benchmark_stub_clp::f_out2( CORBA::String_out _par_s1 )
{
  PortableServer::Servant _serv = _preinvoke ();
  if (_serv) {
    POA_Benchmark * _myserv = POA_Benchmark::_narrow (_serv);
    if (_myserv) {
      #ifdef HAVE_EXCEPTIONS
      try {
      #endif
        _myserv->f_out2(_par_s1);
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

  Benchmark_stub::f_out2(_par_s1);
}

#endif // MICO_CONF_NO_POA

void Benchmark_stub::f_out3( LongList_out _par_ll1 )
{
  CORBA::StaticAny _sa_ll1( CORBA::_stcseq_long );
  CORBA::StaticRequest __req( this, "f_out3" );
  __req.add_out_arg( &_sa_ll1 );

  __req.invoke();

  mico_sii_throw( &__req, 
    0);
  _par_ll1 = (LongList*) _sa_ll1._retn();
}


#ifndef MICO_CONF_NO_POA

void
Benchmark_stub_clp::f_out3( LongList_out _par_ll1 )
{
  PortableServer::Servant _serv = _preinvoke ();
  if (_serv) {
    POA_Benchmark * _myserv = POA_Benchmark::_narrow (_serv);
    if (_myserv) {
      #ifdef HAVE_EXCEPTIONS
      try {
      #endif
        _myserv->f_out3(_par_ll1);
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

  Benchmark_stub::f_out3(_par_ll1);
}

#endif // MICO_CONF_NO_POA

CORBA::Long Benchmark_stub::f_ret1()
{
  CORBA::Long _res;
  CORBA::StaticAny __res( CORBA::_stc_long, &_res );

  CORBA::StaticRequest __req( this, "f_ret1" );
  __req.set_result( &__res );

  __req.invoke();

  mico_sii_throw( &__req, 
    0);
  return _res;
}


#ifndef MICO_CONF_NO_POA

CORBA::Long
Benchmark_stub_clp::f_ret1()
{
  PortableServer::Servant _serv = _preinvoke ();
  if (_serv) {
    POA_Benchmark * _myserv = POA_Benchmark::_narrow (_serv);
    if (_myserv) {
      CORBA::Long __res;

      #ifdef HAVE_EXCEPTIONS
      try {
      #endif
        __res = _myserv->f_ret1();
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

  return Benchmark_stub::f_ret1();
}

#endif // MICO_CONF_NO_POA

char* Benchmark_stub::f_ret2()
{
  char* _res = NULL;
  CORBA::StaticAny __res( CORBA::_stc_string, &_res );

  CORBA::StaticRequest __req( this, "f_ret2" );
  __req.set_result( &__res );

  __req.invoke();

  mico_sii_throw( &__req, 
    0);
  return _res;
}


#ifndef MICO_CONF_NO_POA

char*
Benchmark_stub_clp::f_ret2()
{
  PortableServer::Servant _serv = _preinvoke ();
  if (_serv) {
    POA_Benchmark * _myserv = POA_Benchmark::_narrow (_serv);
    if (_myserv) {
      char* __res;

      #ifdef HAVE_EXCEPTIONS
      try {
      #endif
        __res = _myserv->f_ret2();
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

  return Benchmark_stub::f_ret2();
}

#endif // MICO_CONF_NO_POA

LongList* Benchmark_stub::f_ret3()
{
  CORBA::StaticAny __res( CORBA::_stcseq_long );

  CORBA::StaticRequest __req( this, "f_ret3" );
  __req.set_result( &__res );

  __req.invoke();

  mico_sii_throw( &__req, 
    0);
  return (LongList*) __res._retn();
}


#ifndef MICO_CONF_NO_POA

LongList*
Benchmark_stub_clp::f_ret3()
{
  PortableServer::Servant _serv = _preinvoke ();
  if (_serv) {
    POA_Benchmark * _myserv = POA_Benchmark::_narrow (_serv);
    if (_myserv) {
      LongList* __res;

      #ifdef HAVE_EXCEPTIONS
      try {
      #endif
        __res = _myserv->f_ret3();
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

  return Benchmark_stub::f_ret3();
}

#endif // MICO_CONF_NO_POA

struct __tc_init_BENCHMARK {
  __tc_init_BENCHMARK()
  {
    _marshaller_Benchmark = new _Marshaller_Benchmark;
  }

  ~__tc_init_BENCHMARK()
  {
    delete static_cast<_Marshaller_Benchmark*>(_marshaller_Benchmark);
  }
};

static __tc_init_BENCHMARK __init_BENCHMARK;

//--------------------------------------------------------
//  Implementation of skeletons
//--------------------------------------------------------

// PortableServer Skeleton Class for interface Benchmark
POA_Benchmark::~POA_Benchmark()
{
}

::Benchmark_ptr
POA_Benchmark::_this ()
{
  CORBA::Object_var obj = PortableServer::ServantBase::_this();
  return ::Benchmark::_narrow (obj);
}

CORBA::Boolean
POA_Benchmark::_is_a (const char * repoid)
{
  if (strcmp (repoid, "IDL:Benchmark:1.0") == 0) {
    return TRUE;
  }
  return FALSE;
}

CORBA::InterfaceDef_ptr
POA_Benchmark::_get_interface ()
{
  CORBA::InterfaceDef_ptr ifd = PortableServer::ServantBase::_get_interface ("IDL:Benchmark:1.0");

  if (CORBA::is_nil (ifd)) {
    mico_throw (CORBA::OBJ_ADAPTER (0, CORBA::COMPLETED_NO));
  }

  return ifd;
}

CORBA::RepositoryId
POA_Benchmark::_primary_interface (const PortableServer::ObjectId &, PortableServer::POA_ptr)
{
  return CORBA::string_dup ("IDL:Benchmark:1.0");
}

CORBA::Object_ptr
POA_Benchmark::_make_stub (PortableServer::POA_ptr poa, CORBA::Object_ptr obj)
{
  return new ::Benchmark_stub_clp (poa, obj);
}

bool
POA_Benchmark::dispatch (CORBA::StaticServerRequest_ptr __req)
{
  #ifdef HAVE_EXCEPTIONS
  try {
  #endif
    switch (mico_string_hash (__req->op_name(), 19)) {
    case 1:
      if( strcmp( __req->op_name(), "f_inout1" ) == 0 ) {
        CORBA::Long _par_l1;
        CORBA::StaticAny _sa_l1( CORBA::_stc_long, &_par_l1 );

        __req->add_inout_arg( &_sa_l1 );

        if( !__req->read_args() )
          return true;

        f_inout1( _par_l1 );
        __req->write_results();
        return true;
      }
      break;
    case 2:
      if( strcmp( __req->op_name(), "f_inout2" ) == 0 ) {
        CORBA::String_var _par_s1;
        CORBA::StaticAny _sa_s1( CORBA::_stc_string, &_par_s1._for_demarshal() );

        __req->add_inout_arg( &_sa_s1 );

        if( !__req->read_args() )
          return true;

        f_inout2( _par_s1.inout() );
        __req->write_results();
        return true;
      }
      break;
    case 3:
      if( strcmp( __req->op_name(), "f_inout3" ) == 0 ) {
        ::LongList _par_ll1;
        CORBA::StaticAny _sa_ll1( CORBA::_stcseq_long, &_par_ll1 );

        __req->add_inout_arg( &_sa_ll1 );

        if( !__req->read_args() )
          return true;

        f_inout3( _par_ll1 );
        __req->write_results();
        return true;
      }
      break;
    case 8:
      if( strcmp( __req->op_name(), "f0" ) == 0 ) {

        if( !__req->read_args() )
          return true;

        f0();
        __req->write_results();
        return true;
      }
      if( strcmp( __req->op_name(), "f_out1" ) == 0 ) {
        CORBA::Long _par_l1;
        CORBA::StaticAny _sa_l1( CORBA::_stc_long, &_par_l1 );

        __req->add_out_arg( &_sa_l1 );

        if( !__req->read_args() )
          return true;

        f_out1( _par_l1 );
        __req->write_results();
        return true;
      }
      break;
    case 9:
      if( strcmp( __req->op_name(), "f_out2" ) == 0 ) {
        char* _par_s1;
        CORBA::StaticAny _sa_s1( CORBA::_stc_string, &_par_s1 );

        __req->add_out_arg( &_sa_s1 );

        if( !__req->read_args() )
          return true;

        f_out2( _par_s1 );
        __req->write_results();
        CORBA::string_free( _par_s1 );
        return true;
      }
      break;
    case 10:
      if( strcmp( __req->op_name(), "f_out3" ) == 0 ) {
        ::LongList* _par_ll1;
        CORBA::StaticAny _sa_ll1( CORBA::_stcseq_long );

        __req->add_out_arg( &_sa_ll1 );

        if( !__req->read_args() )
          return true;

        f_out3( _par_ll1 );
        _sa_ll1.value( CORBA::_stcseq_long, _par_ll1 );
        __req->write_results();
        delete _par_ll1;
        return true;
      }
      break;
    case 11:
      if( strcmp( __req->op_name(), "f_ret1" ) == 0 ) {
        CORBA::Long _res;
        CORBA::StaticAny __res( CORBA::_stc_long, &_res );
        __req->set_result( &__res );

        if( !__req->read_args() )
          return true;

        _res = f_ret1();
        __req->write_results();
        return true;
      }
      break;
    case 12:
      if( strcmp( __req->op_name(), "f_ret2" ) == 0 ) {
        char* _res;
        CORBA::StaticAny __res( CORBA::_stc_string, &_res );
        __req->set_result( &__res );

        if( !__req->read_args() )
          return true;

        _res = f_ret2();
        __req->write_results();
        CORBA::string_free( _res );
        return true;
      }
      break;
    case 13:
      if( strcmp( __req->op_name(), "f_ret3" ) == 0 ) {
        ::LongList* _res;
        CORBA::StaticAny __res( CORBA::_stcseq_long );
        __req->set_result( &__res );

        if( !__req->read_args() )
          return true;

        _res = f_ret3();
        __res.value( CORBA::_stcseq_long, _res );
        __req->write_results();
        delete _res;
        return true;
      }
      break;
    case 15:
      if( strcmp( __req->op_name(), "f_in1" ) == 0 ) {
        CORBA::Long _par_l1;
        CORBA::StaticAny _sa_l1( CORBA::_stc_long, &_par_l1 );

        __req->add_in_arg( &_sa_l1 );

        if( !__req->read_args() )
          return true;

        f_in1( _par_l1 );
        __req->write_results();
        return true;
      }
      break;
    case 16:
      if( strcmp( __req->op_name(), "f_in2" ) == 0 ) {
        CORBA::String_var _par_s1;
        CORBA::StaticAny _sa_s1( CORBA::_stc_string, &_par_s1._for_demarshal() );

        __req->add_in_arg( &_sa_s1 );

        if( !__req->read_args() )
          return true;

        f_in2( _par_s1.inout() );
        __req->write_results();
        return true;
      }
      break;
    case 17:
      if( strcmp( __req->op_name(), "f_in3" ) == 0 ) {
        ::LongList _par_ll1;
        CORBA::StaticAny _sa_ll1( CORBA::_stcseq_long, &_par_ll1 );

        __req->add_in_arg( &_sa_ll1 );

        if( !__req->read_args() )
          return true;

        f_in3( _par_ll1 );
        __req->write_results();
        return true;
      }
      break;
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
POA_Benchmark::invoke (CORBA::StaticServerRequest_ptr __req)
{
  if (dispatch (__req)) {
      return;
  }

  CORBA::Exception * ex = 
    new CORBA::BAD_OPERATION (0, CORBA::COMPLETED_NO);
  __req->set_exception (ex);
  __req->write_results();
}

