#ifndef __MEASUREMENT__H__
#define __MEASUREMENT__H__

#include <cstdlib> 
#include <string>

class Measurement
{
 public:
  Measurement();
  virtual ~Measurement();

  virtual void startClock();
  virtual void stopClock();
  virtual void reportResult(long loops, long size);
  
 private:
  clock_t clockStart_;
  clock_t clockStop_;
};

#endif
