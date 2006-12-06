
#ifndef __MY_OBJECT__H__
#define __MY_OBJECT__H__

#include <iostream>
#include <Console.h>

class MyObject 
  : virtual public CCM_Console
{
public:
  MyObject();

  const std::string prompt() const
    throw(::Components::ccm::local::CCMException);

  void prompt(const std::string value) 
    throw(::Components::ccm::local::CCMException);

  long println(const std::string& msg)
    throw(::Components::ccm::local::CCMException);
private: 
  std::string prompt_;
};


#endif
