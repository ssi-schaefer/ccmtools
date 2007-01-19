#include "ShortBuilder.h"

namespace wamas {
namespace platform {
namespace utils {


const ShortBuilder& ShortBuilder::narrow(const Builder& b)
{
    const ShortBuilder* p = dynamic_cast<const ShortBuilder*>(&b);
    if(!p)
        THROW_ERROR_MSG(TypeError, "invalid cast from " << b.typestr() << " to ShortBuilder");
    return *p;
}


SmartType ShortBuilder::type() const
{
    return value_.type();
}

bool ShortBuilder::can_assign_value(const Value& v) const
{
    return dynamic_cast<const ShortValue*>(&v)!=0;
}

void ShortBuilder::assign_value(const Value& that)
{
    value_ = ShortValue::narrow(that);
}

std::size_t ShortBuilder::hash_value() const
{
    return value_.hash_value();
}

SmartValue ShortBuilder::new_value() const
{
    return SmartValue(new ShortValue(value_));
}

SmartPtr<Builder> ShortBuilder::clone() const
{
    return SmartBuilder(new ShortBuilder(*this));
}

const Value& ShortBuilder::the_value() const
{
    return value_;
}


} // /namespace
} // /namespace
} // /namespace
