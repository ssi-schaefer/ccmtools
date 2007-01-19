#include "StringValue.h"
#include "StringType.h"
#include "StringBuilder.h"

#include <wamas/platform/utils/StringMap.h>

namespace wamas {
namespace platform {
namespace utils {


SmartType StringValue::type() const
{
    static const SmartType singleton(new StringType);
    return singleton;
}

std::string const& StringValue::value_typestr()
{
    return StringType::value_typestr();
}

std::size_t StringValue::hash_value() const
{
    if( !is_hash_value_ok_ )
    {
        wamas::platform::utils::StringHash H;
        the_hash_value_ = H(value_);
        is_hash_value_ok_ = true;
    }
    return the_hash_value_;
}

int StringValue::compare(const Value& that) const
{
    return compare(*this, narrow(that));
}

int StringValue::compare(const StringValue& a, const StringValue& b)
{
    if(a.value_ == b.value_)
        return 0;
    return a.value_<b.value_ ? -1 : +1;
}

const StringValue& StringValue::narrow(const Value& v)
{
    const StringValue* p = dynamic_cast<const StringValue*>(&v);
    if(!p)
        THROW_ERROR_MSG(TypeError, "invalid cast from " << v.typestr() << " to StringValue");
    return *p;
}

SmartPtr<Value> StringValue::clone() const
{
    return SmartValue(new StringValue(*this));
}

SmartBuilder StringValue::create_builder() const
{
    return SmartBuilder(new StringBuilder(*this));
}


} // /namespace
} // /namespace
} // /namespace
