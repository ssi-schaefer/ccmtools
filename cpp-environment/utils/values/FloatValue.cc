#include "FloatValue.h"
#include "FloatType.h"
#include "FloatBuilder.h"

namespace wamas {
namespace platform {
namespace utils {


SmartType FloatValue::type() const
{
    static const SmartType singleton(new FloatType);
    return singleton;
}

std::string const& FloatValue::value_typestr()
{
    return FloatType::value_typestr();
}

std::size_t FloatValue::hash_value() const
{
    return static_cast<std::size_t>(value_);
}

int FloatValue::compare(const Value& that) const
{
    return compare(*this, narrow(that));
}

int FloatValue::compare(const FloatValue& a, const FloatValue& b)
{
    if(a.value_ == b.value_)
        return 0;
    return a.value_<b.value_ ? -1 : +1;
}

const FloatValue& FloatValue::narrow(const Value& v)
{
    const FloatValue* p = dynamic_cast<const FloatValue*>(&v);
    if(!p)
        THROW_ERROR_MSG(TypeError, "invalid cast from " << v.typestr() << " to FloatValue");
    return *p;
}

SmartPtr<Value> FloatValue::clone() const
{
    return SmartValue(new FloatValue(*this));
}

SmartBuilder FloatValue::create_builder() const
{
    return SmartBuilder(new FloatBuilder(*this));
}


} // /namespace
} // /namespace
} // /namespace
