#include <sstream>
#include <iostream>
#include <sys/times.h>

#include "TimerEvaluation.h" 

namespace ccm {
namespace utils {

using namespace std;

string
TimerEvaluation::getTimerResult(const Timer& timer,	const long loops, const long size)
{
	ostringstream os;
	os << "loops(" << loops << ") "
		<< "size("  << size << ") "
		<< "real("  << timer.getRealTime() << ")s "
		<< "call("  << (timer.getRealTime()/loops)*1.0e6 << ")us"
		<< endl;  
	return os.str();
}

} // /namespace utils
} // /namespace ccm
