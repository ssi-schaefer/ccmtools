/* -*- mode: C++; c-basic-offset: 4 -*-
 *
 * Implementation of the HelloWorld CORBA object.
 *
 **/

#include"HelloWorldImpl.h"

using namespace std;


void HelloWorldImpl::hello(const char* msg)
{
    cout <<  "HelloWorld_impl->hello(" << msg << ")" << endl;
}
