/* -*- mode: C++; c-basic-offset: 4 -*-
 *
 * Implementation of the Benchmark CORBA object.
 *
 **/

#include"BenchmarkImpl.h"

using namespace std;


void 
BenchmarkImpl::f0()
{

}

void 
BenchmarkImpl::f_in1( CORBA::Long l1 )
{

}


void 
BenchmarkImpl::f_in2( const char* s1 )
{

}

void 
BenchmarkImpl::f_in3( const ::LongList& ll1 )
{

}


void 
BenchmarkImpl::f_inout1(CORBA::Long& l1 )
{

}

void 
BenchmarkImpl::f_inout2( char*& s1 )
{

}

void 
BenchmarkImpl::f_inout3( ::LongList& ll1 )
{

}


void 
BenchmarkImpl::f_out1(CORBA::Long_out l1 )
{

}

void 
BenchmarkImpl::f_out2( CORBA::String_out s1 )
{

}

void 
BenchmarkImpl::f_out3( ::LongList_out ll1 )
{

}


CORBA::Long 
BenchmarkImpl::f_ret1()
{

}

char*
BenchmarkImpl::f_ret2()
{

}

::LongList*
BenchmarkImpl::f_ret3()
{

}
