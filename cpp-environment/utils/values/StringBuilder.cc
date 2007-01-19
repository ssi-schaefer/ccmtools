#include "StringBuilder.h"

namespace wamas {
namespace platform {
namespace utils {


const StringBuilder& StringBuilder::narrow(const Builder& b)
{
    const StringBuilder* p = dynamic_cast<const StringBuilder*>(&b);
    if(!p)
        THROW_ERROR_MSG(TypeError, "invalid cast from " << b.typestr() << " to StringBuilder");
    return *p;
}


SmartType StringBuilder::type() const
{
    return value_.type();
}

bool StringBuilder::can_assign_value(const Value& v) const
{
    return dynamic_cast<const StringValue*>(&v)!=0;
}

void StringBuilder::assign_value(const Value& that)
{
    value_ = StringValue::narrow(that);
}

std::size_t StringBuilder::hash_value() const
{
    return value_.hash_value();
}

SmartValue StringBuilder::new_value() const
{
    return SmartValue(new StringValue(value_));
}

SmartPtr<Builder> StringBuilder::clone() const
{
    return SmartBuilder(new StringBuilder(*this));
}

const Value& StringBuilder::the_value() const
{
    return value_;
}


} // /namespace
} // /namespace
} // /namespace
