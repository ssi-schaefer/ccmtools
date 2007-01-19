#include "EnumerationBuilder.h"

namespace wamas {
namespace platform {
namespace utils {


const EnumerationBuilder& EnumerationBuilder::narrow(const Builder& b)
{
    const EnumerationBuilder* p = dynamic_cast<const EnumerationBuilder*>(&b);
    if(!p)
        THROW_ERROR_MSG(TypeError, "invalid cast from " << b.typestr() << " to EnumerationBuilder");
    return *p;
}


SmartType EnumerationBuilder::type() const
{
    return value_.type();
}

bool EnumerationBuilder::can_assign_value(const Value& v) const
{
    return value_.can_assign(v);
}

void EnumerationBuilder::assign_value(const Value& that)
{
    value_.assign(that);
}

std::size_t EnumerationBuilder::hash_value() const
{
    return value_.hash_value();
}

SmartValue EnumerationBuilder::new_value() const
{
    return SmartValue(new EnumerationValue(value_));
}


void EnumerationBuilder::set_as_int(int index)
{
    if(value_.type_)
        value_.index_ = value_.type_->member_index(index);
    else
        value_.index_ = index;
}

void EnumerationBuilder::set_as_string(const std::string& literal)
{
    if(value_.type_)
        value_.index_ = value_.type_->member_index(literal);
    else
        THROW_ERROR_MSG(TypeError, "enumeration without type");
}

SmartPtr<Builder> EnumerationBuilder::clone() const
{
    return SmartBuilder(new EnumerationBuilder(*this));
}

const Value& EnumerationBuilder::the_value() const
{
    return value_;
}


} // /namespace
} // /namespace
} // /namespace
