#include "LongBuilder.h"

namespace wamas {
namespace platform {
namespace utils {


const LongBuilder& LongBuilder::narrow(const Builder& b)
{
    const LongBuilder* p = dynamic_cast<const LongBuilder*>(&b);
    if(!p)
        THROW_ERROR_MSG(TypeError, "invalid cast from " << b.typestr() << " to LongBuilder");
    return *p;
}


SmartType LongBuilder::type() const
{
    return value_.type();
}

bool LongBuilder::can_assign_value(const Value& v) const
{
    return value_.can_assign(v);
}

void LongBuilder::assign_value(const Value& that)
{
    value_.assign(that);
}

std::size_t LongBuilder::hash_value() const
{
    return value_.hash_value();
}

SmartValue LongBuilder::new_value() const
{
    return SmartValue(new LongValue(value_));
}

SmartPtr<Builder> LongBuilder::clone() const
{
    return SmartBuilder(new LongBuilder(*this));
}

const Value& LongBuilder::the_value() const
{
    return value_;
}


} // /namespace
} // /namespace
} // /namespace
