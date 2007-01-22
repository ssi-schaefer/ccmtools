#ifndef wx__utils__atomic_count
#define wx__utils__atomic_count

#ifdef HAVE_CONFIG_H
#include "config.h"
#endif

#ifdef HAVE_BOOST
#ifndef BOOST_DISABLE_THREADS
#include <boost/detail/atomic_count.hpp>
#ifdef BOOST_HAS_THREADS
#define WX_ATOMIC_COUNT_WITH_BOOST 1
#endif  // BOOST_HAS_THREADS
#endif  // ! BOOST_DISABLE_THREADS
#endif  // HAVE_BOOST


#ifdef WX_ATOMIC_COUNT_WITH_BOOST

namespace wamas {
namespace platform {
namespace utils {

typedef boost::detail::atomic_count  atomic_count;

} // /namespace
} // /namespace
} // /namespace

#else   // ! WX_ATOMIC_COUNT_WITH_BOOST

#if defined(_WIN32) || defined(__WIN32__) || defined(WIN32)

#include <windows.h>

namespace wamas
{namespace platform
{namespace utils
{

// stolen from boost/detail/atomic_count_win32.hpp
class atomic_count
{
public:

    explicit atomic_count( long v )
    : value_( v )
    {}

    long operator++()
    {
        return ::InterlockedIncrement( &value_ );
    }

    long operator--()
    {
        return ::InterlockedDecrement( &value_ );
    }

    operator long() const
    {
        return static_cast<long const volatile &>( value_ );
    }

private:

    atomic_count( atomic_count const & );
    atomic_count & operator=( atomic_count const & );

    long value_;
};

}}}

#else   // !WIN32

namespace wamas
{namespace platform
{namespace utils
{

typedef long atomic_count;

}}}

#endif  // WIN32

#endif  // WX_ATOMIC_COUNT_WITH_BOOST

#endif  // wx__utils__atomic_count
