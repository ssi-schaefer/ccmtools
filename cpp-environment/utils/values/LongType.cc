#include "LongType.h"
#include "LongBuilder.h"

namespace wamas {
namespace platform {
namespace utils {


const std::string& LongType::typestr() const
{
    return value_typestr();
}

std::string const& LongType::value_typestr()
{
    static const std::string str("long");
    return str;
}

SmartValue LongType::default_value() const
{
    static const SmartValue singleton(new LongValue);
    return singleton;
}

SmartBuilder LongType::create() const
{
    return SmartBuilder(new LongBuilder);
}

SmartValue LongType::fit(const SmartValue& value) const
{
    return generic_fit<LongValue>(value);
}


} // /namespace
} // /namespace
} // /namespace
