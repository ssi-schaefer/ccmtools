
#ifndef __MY_OBJECT__H__
#define __MY_OBJECT__H__

#include <iostream>
#include <ccm/local/Console.h>

class MyObject 
  : virtual public ccm::local::CCM_Console
{
public:
  MyObject();

  const std::string prompt() const 
    throw(ccm::local::Components::CCMException);

  void prompt(const std::string value) 
    throw(ccm::local::Components::CCMException);

  long println(const std::string& msg)
    throw(ccm::local::Components::CCMException);
  
private: 
  std::string prompt_;
};


#endif
