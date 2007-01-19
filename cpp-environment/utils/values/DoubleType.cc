#include "DoubleType.h"
#include "DoubleBuilder.h"

namespace wamas {
namespace platform {
namespace utils {


const std::string& DoubleType::typestr() const
{
    return value_typestr();
}

std::string const& DoubleType::value_typestr()
{
    static const std::string str("double");
    return str;
}

SmartValue DoubleType::default_value() const
{
    static const SmartValue singleton(new DoubleValue);
    return singleton;
}

SmartBuilder DoubleType::create() const
{
    return SmartBuilder(new DoubleBuilder);
}

SmartValue DoubleType::fit(const SmartValue& value) const
{
    return generic_fit<DoubleValue>(value);
}


} // /namespace
} // /namespace
} // /namespace
