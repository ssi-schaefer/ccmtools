#include "StructType.h"
#include "StructValue.h"
#include "StructBuilder.h"

namespace wamas {
namespace platform {
namespace utils {


StructType::StructType(const std::string& name)
: name_(name)
{
    typestr_ = typestr_prefix();
    typestr_ += name;
}


StructType::StructType(const std::string& name, const Members& m)
: name_(name)
, members_(m)
{
    typestr_ = typestr_prefix();
    typestr_ += name;
    for(Members::iterator it=members_.begin(); it!=members_.end(); ++it)
    {
        names_.push_back(it->first);
    }
}


const char* StructType::typestr_prefix()
{
    return "struct ";
}


const std::string& StructType::typestr() const
{
    return typestr_;
}


bool StructType::has_member(const std::string& name) const
{
    return members_.find(name)!=members_.end();
}


void StructType::add_member(const std::string& name, SmartType type)
{
    if(has_member(name))
        THROW_ERROR_MSG(TypeError, "duplicated member \"" << name << '"');
    members_[name] = type;
    names_.push_back(name);
}


SmartType StructType::get_member(const std::string& name) const
{
    Members::const_iterator pos = members_.find(name);
    if(pos==members_.end())
        THROW_ERROR_MSG(TypeError, "unknown member \"" << name << '"');
    return pos->second;
}


SmartValue StructType::default_value() const
{
    if(!default_value_)
    {
        SmartStructType st(new StructType(*this));
        default_value_.eat(new StructValue(st));
    }
    return default_value_;
}


SmartBuilder StructType::create() const
{
    SmartStructType st(default_value()->type());
    return SmartBuilder(new StructBuilder(st));
}

SmartValue StructType::fit(const SmartValue& value) const
{
    const Value* pv = value.cptr();
    if(!pv)
        return value;

    SmartStructType st(default_value()->type());
    return SmartValue(new StructValue(st, StructValue::narrow(*pv)));
}


} // /namespace
} // /namespace
} // /namespace
