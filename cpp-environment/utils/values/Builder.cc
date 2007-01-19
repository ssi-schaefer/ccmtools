#include "Builder.h"

namespace wamas {
namespace platform {
namespace utils {


const std::string& Builder::typestr() const
{
    static const std::string null_value("(null builder)");
    static const std::string null_type("(null type)");
    if(!this)
        return null_value;
    SmartType t = this->type();
    if(!t)
        return null_type;
    return t->typestr();
}


SmartPtr<Builder> Builder::safe_clone(SmartPtr<Builder> b)
{
    if(b)
        return b->clone();
    else
        return SmartBuilder();
}


} // /namespace
} // /namespace
} // /namespace
