#include "FloatBuilder.h"

namespace wamas {
namespace platform {
namespace utils {


const FloatBuilder& FloatBuilder::narrow(const Builder& b)
{
    const FloatBuilder* p = dynamic_cast<const FloatBuilder*>(&b);
    if(!p)
        THROW_ERROR_MSG(TypeError, "invalid cast from " << b.typestr() << " to FloatBuilder");
    return *p;
}


SmartType FloatBuilder::type() const
{
    return value_.type();
}

bool FloatBuilder::can_assign_value(const Value& v) const
{
    return dynamic_cast<const FloatValue*>(&v)!=0;
}

void FloatBuilder::assign_value(const Value& that)
{
    value_ = FloatValue::narrow(that);
}

std::size_t FloatBuilder::hash_value() const
{
    return value_.hash_value();
}

SmartValue FloatBuilder::new_value() const
{
    return SmartValue(new FloatValue(value_));
}

SmartPtr<Builder> FloatBuilder::clone() const
{
    return SmartBuilder(new FloatBuilder(*this));
}

const Value& FloatBuilder::the_value() const
{
    return value_;
}


} // /namespace
} // /namespace
} // /namespace
