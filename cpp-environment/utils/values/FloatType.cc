#include "FloatType.h"
#include "FloatBuilder.h"

namespace wamas {
namespace platform {
namespace utils {


const std::string& FloatType::typestr() const
{
    return value_typestr();
}

std::string const& FloatType::value_typestr()
{
    static const std::string str("float");
    return str;
}

SmartValue FloatType::default_value() const
{
    static const SmartValue singleton(new FloatValue);
    return singleton;
}

SmartBuilder FloatType::create() const
{
    return SmartBuilder(new FloatBuilder);
}

SmartValue FloatType::fit(const SmartValue& value) const
{
    return generic_fit<FloatValue>(value);
}


} // /namespace
} // /namespace
} // /namespace
