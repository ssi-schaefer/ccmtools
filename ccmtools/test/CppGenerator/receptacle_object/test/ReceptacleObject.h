
#include<ccm/local/IFace.h>

class ReceptacleObject
: virtual public ccm::local::CCM_IFace
{
 public:
  ReceptacleObject();
  virtual ~ReceptacleObject();

  virtual long op_b1(const long p1, long& p2, long& p3);
  virtual std::string op_b2(const std::string& p1, std::string& p2, std::string& p3);
};
