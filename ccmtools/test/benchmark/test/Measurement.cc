
#include <iostream>

#include "Measurement.h" 


using namespace std;

Measurement::Measurement()
{

}

Measurement::~Measurement()
{

}

void
Measurement::startClock()
{
  clockStart_ = clock();
}


void 
Measurement::stopClock()
{
  clockStop_ = clock();
}


void 
Measurement::reportResult(long loops, long size) 
{
  cout << "loops(" << loops << ") size(" << size << ") ";
  
  double cpu_time_used = 
    double(clockStop_ - clockStart_) / CLOCKS_PER_SEC * 1000.0;
 
  cout << "time(" << cpu_time_used << ")ms" << endl;	
}
