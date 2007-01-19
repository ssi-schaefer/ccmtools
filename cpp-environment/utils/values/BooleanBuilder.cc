#include "BooleanBuilder.h"

namespace wamas {
namespace platform {
namespace utils {


const BooleanBuilder& BooleanBuilder::narrow(const Builder& b)
{
    const BooleanBuilder* p = dynamic_cast<const BooleanBuilder*>(&b);
    if(!p)
        THROW_ERROR_MSG(TypeError, "invalid cast from " << b.typestr() << " to BooleanBuilder");
    return *p;
}


SmartType BooleanBuilder::type() const
{
    return value_.type();
}

bool BooleanBuilder::can_assign_value(const Value& v) const
{
    return dynamic_cast<const BooleanValue*>(&v)!=0;
}

void BooleanBuilder::assign_value(const Value& that)
{
    value_ = BooleanValue::narrow(that);
}

std::size_t BooleanBuilder::hash_value() const
{
    return value_.hash_value();
}

SmartValue BooleanBuilder::new_value() const
{
    return SmartValue(new BooleanValue(value_));
}

SmartPtr<Builder> BooleanBuilder::clone() const
{
    return SmartBuilder(new BooleanBuilder(*this));
}

const Value& BooleanBuilder::the_value() const
{
    return value_;
}


} // /namespace
} // /namespace
} // /namespace
