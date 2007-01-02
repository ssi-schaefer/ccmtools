#include <sstream>
#include <iostream>
#include <sys/times.h>
#include <unistd.h>

#include "Timer.h" 

#if defined(_WIN32) || defined(__INTERIX)
#define CLK_TCK 1000
#else
#define CLK_TCK sysconf(_SC_CLK_TCK)
#endif

namespace ccm {
namespace utils {

using namespace std;

Timer::Timer()
{
	realTimeStart = realTimeStop = 0;
	userTimeStart = userTimeStop = 0;
	systemTimeStart = systemTimeStop =0;
}

void
Timer::start()
{
	tms timeStructure;
	realTimeStart = times(&timeStructure);
	userTimeStart = timeStructure.tms_utime;
	systemTimeStart = timeStructure.tms_stime;
}

void 
Timer::stop()
{
	tms timeStructure;
	realTimeStop = times(&timeStructure);
	userTimeStop = timeStructure.tms_utime;
	systemTimeStop = timeStructure.tms_stime;
}

double 
Timer::getRealTime() const
{
	return double(realTimeStop - realTimeStart) / CLK_TCK;
}

double 
Timer::getUserTime() const
{
	return double(userTimeStop - userTimeStart) / CLK_TCK;
}

double 
Timer::getSystemTime() const
{
	return double(systemTimeStop - systemTimeStart) / CLK_TCK;
}

string 
Timer::toString() const
{
	ostringstream os;
	os << "real(" << getRealTime() << ")s ";
	os << "user(" << getUserTime() << ")s ";
	os << "sys(" << getSystemTime() << ")s ";
	return os.str();
}


ostream&
operator<<(std::ostream& o, const Timer& value)
{
	o << value.toString();
	return o;
}

} // /namespace utils
} // /namespace ccm
