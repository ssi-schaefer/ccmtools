#include "smartptr.h"

namespace wamas {
namespace platform {
namespace utils {

LTA_MEMDEF(RefCounted, 20061107, "$Id$");


void RefCounted::ref()
{
    ++refcount_;
}

void RefCounted::unref()
{
    if (!--refcount_) delete this;
}


#ifdef WX_REFCOUNTED_COLLECT_NEW

RefCounted::NewStatistics RefCounted::new_statistics_;

void* RefCounted::operator new (std::size_t s)
{
    NewStatistics::iterator pos = new_statistics_.find(s);
    if(pos==new_statistics_.end())
        new_statistics_[s] = 1;
    else
        ++(pos->second);
    return malloc(s);
}

void RefCounted::operator delete (void* p)
{
    free(p);
}

#endif  // WX_REFCOUNTED_COLLECT_NEW


} // /namespace
} // /namespace
} // /namespace
