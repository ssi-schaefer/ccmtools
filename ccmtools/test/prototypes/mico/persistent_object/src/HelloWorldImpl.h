/* -*- mode: C++; c-basic-offset: 4 -*-
 *
 * Implementation of the HelloWorld CORBA object.
 *
 **/

#ifndef __HELLO_WORLD_IMP_H__
#define __HELLO_WORLD_IMP_H__

#include"HelloWorld.h"

class HelloWorldImpl 
    : virtual public POA_HelloWorld
{
public:
    void hello(const char* msg);
};

#endif

