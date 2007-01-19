#include "BooleanValue.h"
#include "BooleanType.h"
#include "BooleanBuilder.h"

namespace wamas {
namespace platform {
namespace utils {


SmartType BooleanValue::type() const
{
    static const SmartType singleton(new BooleanType);
    return singleton;
}

std::string const& BooleanValue::value_typestr()
{
    return BooleanType::value_typestr();
}

std::size_t BooleanValue::hash_value() const
{
    return static_cast<std::size_t>(value_);
}

int BooleanValue::compare(const Value& that) const
{
    return compare(*this, narrow(that));
}

int BooleanValue::compare(const BooleanValue& a, const BooleanValue& b)
{
    if(a.value_)
        return b.value_ ? 0 : +1;
    else
        return b.value_ ? -1 : 0;
}

const BooleanValue& BooleanValue::narrow(const Value& v)
{
    const BooleanValue* p = dynamic_cast<const BooleanValue*>(&v);
    if(!p)
        THROW_ERROR_MSG(TypeError, "invalid cast from " << v.typestr() << " to BooleanValue");
    return *p;
}

SmartPtr<Value> BooleanValue::clone() const
{
    return SmartValue(new BooleanValue(*this));
}

SmartBuilder BooleanValue::create_builder() const
{
    return SmartBuilder(new BooleanBuilder(*this));
}


} // /namespace
} // /namespace
} // /namespace
