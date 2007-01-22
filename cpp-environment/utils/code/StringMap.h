//
// $Id$
//

#ifndef wx_utils_code_stringmap_h
#define wx_utils_code_stringmap_h


#include "HashMap.h"
#include <string>


namespace wamas {
namespace platform {
namespace utils {


struct StringHash
{
    std::size_t operator()(std::string const& s) const;
};


/**
@brief A hash-map with keys of type std::string.
*/
template< typename T >
class StringMap : public HashMap<std::string, T, StringHash>
{
public:
    explicit StringMap(std::size_t n)
    : HashMap<std::string, T, StringHash>(n)
    {}
    
    StringMap() {}
};


} // /namespace
} // /namespace
} // /namespace

#endif
