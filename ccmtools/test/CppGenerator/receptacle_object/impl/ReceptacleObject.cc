#include "ReceptacleObject.h"

using namespace std;
using namespace ccm::local;

ReceptacleObject::ReceptacleObject()
{
}

ReceptacleObject::~ReceptacleObject()
{
}

long 
ReceptacleObject::op_b1(const long p1, long& p2, long& p3)
  throw (::Components::ccm::local::CCMException)
{
  cout << " ReceptacleObject::op_b1()" << endl;
  p3=p2;
  p2=p1;
  return p3+p1;
}

string 
ReceptacleObject::op_b2(const std::string& p1, std::string& p2, std::string& p3)
  throw (::Components::ccm::local::CCMException)
{
  cout << " ReceptacleObject::op_b2()" << endl;
  p3=p2;
  p2=p1;
  return p3+p1;
}
