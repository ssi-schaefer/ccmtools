#include "BooleanType.h"
#include "BooleanBuilder.h"

namespace wamas {
namespace platform {
namespace utils {


const std::string& BooleanType::typestr() const
{
    return value_typestr();
}

std::string const& BooleanType::value_typestr()
{
    static const std::string str("boolean");
    return str;
}

SmartValue BooleanType::default_value() const
{
    static const SmartValue singleton(new BooleanValue);
    return singleton;
}

SmartBuilder BooleanType::create() const
{
    return SmartBuilder(new BooleanBuilder);
}

SmartValue BooleanType::fit(const SmartValue& value) const
{
    return generic_fit<BooleanValue>(value);
}


} // /namespace
} // /namespace
} // /namespace
