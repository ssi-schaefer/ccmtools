
#include<iostream>

#include "MyObject.h"

using namespace std;

MyObject::MyObject() 
{
    prompt_="-=> ";
}

const std::string 
MyObject::prompt() const
  throw(::Components::CCMException) 
{
  return prompt_;
}

void 
MyObject::prompt(const std::string value) 
  throw(::Components::CCMException) {
  prompt_=value;
}

long 
MyObject::println(const std::string& msg) 
throw (::Components::CCMException)
{
  cout << prompt_ << msg << endl;
  return msg.length();
}
