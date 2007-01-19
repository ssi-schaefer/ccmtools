#include "ShortValue.h"
#include "ShortType.h"
#include "ShortBuilder.h"

namespace wamas {
namespace platform {
namespace utils {


SmartType ShortValue::type() const
{
    static const SmartType singleton(new ShortType);
    return singleton;
}

std::string const& ShortValue::value_typestr()
{
    return ShortType::value_typestr();
}

std::size_t ShortValue::hash_value() const
{
    return static_cast<std::size_t>(value_);
}

int ShortValue::compare(const Value& that) const
{
    return compare(*this, narrow(that));
}

int ShortValue::compare(const ShortValue& a, const ShortValue& b)
{
    if(a.value_ == b.value_)
        return 0;
    return a.value_<b.value_ ? -1 : +1;
}

const ShortValue& ShortValue::narrow(const Value& v)
{
    const ShortValue* p = dynamic_cast<const ShortValue*>(&v);
    if(!p)
        THROW_ERROR_MSG(TypeError, "invalid cast from " << v.typestr() << " to ShortValue");
    return *p;
}

SmartPtr<Value> ShortValue::clone() const
{
    return SmartValue(new ShortValue(*this));
}

SmartBuilder ShortValue::create_builder() const
{
    return SmartBuilder(new ShortBuilder(*this));
}


} // /namespace
} // /namespace
} // /namespace
