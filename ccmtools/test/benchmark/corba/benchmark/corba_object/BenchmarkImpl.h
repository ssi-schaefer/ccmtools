/* -*- mode: C++; c-basic-offset: 4 -*-
 *
 * Implementation of the Benchmark CORBA object.
 *
 **/

#ifndef __BENCHMARK_IMP_H__
#define __BENCHMARK_IMP_H__

#include"Benchmark.h"

class BenchmarkImpl
    : virtual public POA_Benchmark
{
public:
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
};

#endif
