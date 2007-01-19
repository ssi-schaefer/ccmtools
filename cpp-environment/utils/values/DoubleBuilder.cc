#include "DoubleBuilder.h"

namespace wamas {
namespace platform {
namespace utils {


const DoubleBuilder& DoubleBuilder::narrow(const Builder& b)
{
    const DoubleBuilder* p = dynamic_cast<const DoubleBuilder*>(&b);
    if(!p)
        THROW_ERROR_MSG(TypeError, "invalid cast from " << b.typestr() << " to DoubleBuilder");
    return *p;
}


SmartType DoubleBuilder::type() const
{
    return value_.type();
}

bool DoubleBuilder::can_assign_value(const Value& v) const
{
    return value_.can_assign(v);
}

void DoubleBuilder::assign_value(const Value& that)
{
    value_.assign(that);
}

std::size_t DoubleBuilder::hash_value() const
{
    return value_.hash_value();
}

SmartValue DoubleBuilder::new_value() const
{
    return SmartValue(new DoubleValue(value_));
}

SmartPtr<Builder> DoubleBuilder::clone() const
{
    return SmartBuilder(new DoubleBuilder(*this));
}

const Value& DoubleBuilder::the_value() const
{
    return value_;
}


} // /namespace
} // /namespace
} // /namespace
