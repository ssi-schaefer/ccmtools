#include "StringType.h"
#include "StringBuilder.h"

namespace wamas {
namespace platform {
namespace utils {


const std::string& StringType::typestr() const
{
    return value_typestr();
}

std::string const& StringType::value_typestr()
{
    static const std::string str("string");
    return str;
}

SmartValue StringType::default_value() const
{
    static const SmartValue singleton(new StringValue);
    return singleton;
}

SmartBuilder StringType::create() const
{
    return SmartBuilder(new StringBuilder);
}

SmartValue StringType::fit(const SmartValue& value) const
{
    return generic_fit<StringValue>(value);
}


} // /namespace
} // /namespace
} // /namespace
