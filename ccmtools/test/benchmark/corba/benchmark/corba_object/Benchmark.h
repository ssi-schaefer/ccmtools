/*
 *  MICO --- an Open Source CORBA implementation
 *  Copyright (c) 1997-2003 by The Mico Team
 *
 *  This file was automatically generated. DO NOT EDIT!
 */

#include <CORBA.h>
#include <mico/throw.h>

#ifndef __BENCHMARK_H__
#define __BENCHMARK_H__




class Benchmark;
typedef Benchmark *Benchmark_ptr;
typedef Benchmark_ptr BenchmarkRef;
typedef ObjVar< Benchmark > Benchmark_var;
typedef ObjOut< Benchmark > Benchmark_out;



typedef SequenceTmpl< CORBA::Long,MICO_TID_DEF> LongList;
typedef TSeqVar< SequenceTmpl< CORBA::Long,MICO_TID_DEF> > LongList_var;
typedef TSeqOut< SequenceTmpl< CORBA::Long,MICO_TID_DEF> > LongList_out;


/*
 * Base class and common definitions for interface Benchmark
 */

class Benchmark : 
  virtual public CORBA::Object
{
  public:
    virtual ~Benchmark();

    #ifdef HAVE_TYPEDEF_OVERLOAD
    typedef Benchmark_ptr _ptr_type;
    typedef Benchmark_var _var_type;
    #endif

    static Benchmark_ptr _narrow( CORBA::Object_ptr obj );
    static Benchmark_ptr _narrow( CORBA::AbstractBase_ptr obj );
    static Benchmark_ptr _duplicate( Benchmark_ptr _obj )
    {
      CORBA::Object::_duplicate (_obj);
      return _obj;
    }

    static Benchmark_ptr _nil()
    {
      return 0;
    }

    virtual void *_narrow_helper( const char *repoid );

    virtual void f0() = 0;
    virtual void f_in1( CORBA::Long l1 ) = 0;
    virtual void f_in2( const char* s1 ) = 0;
    virtual void f_in3( const ::LongList& ll1 ) = 0;
    virtual void f_inout1( CORBA::Long& l1 ) = 0;
    virtual void f_inout2( char*& s1 ) = 0;
    virtual void f_inout3( ::LongList& ll1 ) = 0;
    virtual void f_out1( CORBA::Long_out l1 ) = 0;
    virtual void f_out2( CORBA::String_out s1 ) = 0;
    virtual void f_out3( ::LongList_out ll1 ) = 0;
    virtual CORBA::Long f_ret1() = 0;
    virtual char* f_ret2() = 0;
    virtual ::LongList* f_ret3() = 0;

  protected:
    Benchmark() {};
  private:
    Benchmark( const Benchmark& );
    void operator=( const Benchmark& );
};

// Stub for interface Benchmark
class Benchmark_stub:
  virtual public Benchmark
{
  public:
    virtual ~Benchmark_stub();
    void f0();
    void f_in1( CORBA::Long l1 );
    void f_in2( const char* s1 );
    void f_in3( const ::LongList& ll1 );
    void f_inout1( CORBA::Long& l1 );
    void f_inout2( char*& s1 );
    void f_inout3( ::LongList& ll1 );
    void f_out1( CORBA::Long_out l1 );
    void f_out2( CORBA::String_out s1 );
    void f_out3( ::LongList_out ll1 );
    CORBA::Long f_ret1();
    char* f_ret2();
    ::LongList* f_ret3();

  private:
    void operator=( const Benchmark_stub& );
};

#ifndef MICO_CONF_NO_POA

class Benchmark_stub_clp :
  virtual public Benchmark_stub,
  virtual public PortableServer::StubBase
{
  public:
    Benchmark_stub_clp (PortableServer::POA_ptr, CORBA::Object_ptr);
    virtual ~Benchmark_stub_clp ();
    void f0();
    void f_in1( CORBA::Long l1 );
    void f_in2( const char* s1 );
    void f_in3( const ::LongList& ll1 );
    void f_inout1( CORBA::Long& l1 );
    void f_inout2( char*& s1 );
    void f_inout3( ::LongList& ll1 );
    void f_out1( CORBA::Long_out l1 );
    void f_out2( CORBA::String_out s1 );
    void f_out3( ::LongList_out ll1 );
    CORBA::Long f_ret1();
    char* f_ret2();
    ::LongList* f_ret3();

  protected:
    Benchmark_stub_clp ();
  private:
    void operator=( const Benchmark_stub_clp & );
};

#endif // MICO_CONF_NO_POA

#ifndef MICO_CONF_NO_POA

class POA_Benchmark : virtual public PortableServer::StaticImplementation
{
  public:
    virtual ~POA_Benchmark ();
    Benchmark_ptr _this ();
    bool dispatch (CORBA::StaticServerRequest_ptr);
    virtual void invoke (CORBA::StaticServerRequest_ptr);
    virtual CORBA::Boolean _is_a (const char *);
    virtual CORBA::InterfaceDef_ptr _get_interface ();
    virtual CORBA::RepositoryId _primary_interface (const PortableServer::ObjectId &, PortableServer::POA_ptr);

    virtual void * _narrow_helper (const char *);
    static POA_Benchmark * _narrow (PortableServer::Servant);
    virtual CORBA::Object_ptr _make_stub (PortableServer::POA_ptr, CORBA::Object_ptr);

    virtual void f0() = 0;
    virtual void f_in1( CORBA::Long l1 ) = 0;
    virtual void f_in2( const char* s1 ) = 0;
    virtual void f_in3( const ::LongList& ll1 ) = 0;
    virtual void f_inout1( CORBA::Long& l1 ) = 0;
    virtual void f_inout2( char*& s1 ) = 0;
    virtual void f_inout3( ::LongList& ll1 ) = 0;
    virtual void f_out1( CORBA::Long_out l1 ) = 0;
    virtual void f_out2( CORBA::String_out s1 ) = 0;
    virtual void f_out3( ::LongList_out ll1 ) = 0;
    virtual CORBA::Long f_ret1() = 0;
    virtual char* f_ret2() = 0;
    virtual ::LongList* f_ret3() = 0;

  protected:
    POA_Benchmark () {};

  private:
    POA_Benchmark (const POA_Benchmark &);
    void operator= (const POA_Benchmark &);
};

#endif // MICO_CONF_NO_POA

extern CORBA::StaticTypeInfo *_marshaller_Benchmark;

#endif
