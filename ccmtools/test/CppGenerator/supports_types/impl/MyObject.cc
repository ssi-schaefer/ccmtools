
#include<iostream>

#include "MyObject.h"

using namespace std;

MyObject::MyObject() 
{
    prompt_="-=> ";
}

std::string 
MyObject::prompt() 
  throw(LocalComponents::CCMException) 
{
  return prompt_;
}

void 
MyObject::prompt(const std::string value) 
  throw(LocalComponents::CCMException) {
  prompt_=value;
}

long 
MyObject::println(const std::string& msg) {
  cout << prompt_ << msg << endl;
  return msg.length();
}
