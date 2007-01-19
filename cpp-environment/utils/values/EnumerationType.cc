#include "EnumerationType.h"
#include "EnumerationValue.h"
#include "EnumerationBuilder.h"

namespace wamas {
namespace platform {
namespace utils {


EnumerationType::EnumerationType(const std::string& name, const Members& members)
: name_(name), members_(members)
{
    typestr_ = typestr_prefix();
    typestr_ += name;
}

const char* EnumerationType::typestr_prefix()
{
    return "enum ";
}

const std::string& EnumerationType::typestr() const
{
    return typestr_;
}

int EnumerationType::member_index(const std::string& literal) const
{
    for(std::size_t i=0; i<members_.size(); ++i)
    {
        if(members_[i]==literal)
            return i;
    }
    THROW_ERROR_MSG(LiteralError, typestr_ << ": unknown literal \"" << literal << '"');
}

int EnumerationType::member_index(int index) const
{
    int s = members_.size();
    if(index>=0 && index<s)
        return index;
    THROW_ERROR_MSG(RangeError, typestr_ << ": index " << index << " is out of range");
}

const std::string& EnumerationType::get_as_string(int index) const
{
    return members_[member_index(index)];
}

SmartValue EnumerationType::default_value() const
{
    if(!default_value_)
    {
        SmartEnumerationType et(new EnumerationType(*this));
        default_value_.eat(new EnumerationValue(et, 0));
    }
    return default_value_;
}

SmartBuilder EnumerationType::create() const
{
    SmartEnumerationType et(default_value()->type());
    return SmartBuilder(new EnumerationBuilder(et, 0));
}

SmartValue EnumerationType::fit(const SmartValue& value) const
{
    if(!value)
        return value;

    SmartEnumerationType et(default_value()->type());
    return SmartValue(new EnumerationValue(et, *value.cptr()));
}


} // /namespace
} // /namespace
} // /namespace
