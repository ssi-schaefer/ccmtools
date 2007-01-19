#include "ShortType.h"
#include "ShortBuilder.h"

namespace wamas {
namespace platform {
namespace utils {


const std::string& ShortType::typestr() const
{
    return value_typestr();
}

std::string const& ShortType::value_typestr()
{
    static const std::string str("short");
    return str;
}

SmartValue ShortType::default_value() const
{
    static const SmartValue singleton(new ShortValue);
    return singleton;
}

SmartBuilder ShortType::create() const
{
    return SmartBuilder(new ShortBuilder);
}

SmartValue ShortType::fit(const SmartValue& value) const
{
    return generic_fit<ShortValue>(value);
}


} // /namespace
} // /namespace
} // /namespace
