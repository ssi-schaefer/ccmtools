
#include<iostream>

#include "MyObject.h"

using namespace std;

MyObject::MyObject() 
{
    prompt_="-=> ";
}

const std::string 
MyObject::prompt() const
  throw(::Components::ccm::local::CCMException) 
{
  return prompt_;
}

void 
MyObject::prompt(const std::string value) 
  throw(::Components::ccm::local::CCMException) {
  prompt_=value;
}

long 
MyObject::println(const std::string& msg) 
throw (::Components::ccm::local::CCMException)
{
  cout << prompt_ << msg << endl;
  return msg.length();
}
