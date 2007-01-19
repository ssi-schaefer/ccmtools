#include "Struct.h"

namespace wamas {
namespace platform {
namespace utils {


Struct& Struct::operator=(const Struct& src)
{
    StructValue::operator=(src);
    return *this;
}

SmartValue Struct::getP(const std::string& name) const
{
    Members::const_iterator pos = members_.find(name);
    if(pos!=members_.end())
        return pos->second;
    else
        return SmartValue();
}

bool Struct::getP(const std::string& name, SmartValue& result) const
{
    Members::const_iterator pos = members_.find(name);
    if(pos!=members_.end())
    {
        result = pos->second;
        return true;
    }
    return false;
}

void Struct::setP(const std::string& name, const SmartValue& value)
{
    members_[name] = value;
    is_hash_value_ok_ = false;
}

void Struct::clear()
{
    members_.clear();
    is_hash_value_ok_ = false;
}


} // /namespace
} // /namespace
} // /namespace
