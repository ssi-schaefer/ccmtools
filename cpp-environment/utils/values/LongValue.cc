#include "LongValue.h"
#include "LongType.h"
#include "LongBuilder.h"
#include "ShortValue.h"
#include "EnumerationValue.h"

namespace wamas {
namespace platform {
namespace utils {


SmartType LongValue::type() const
{
    static const SmartType singleton(new LongType);
    return singleton;
}

std::string const& LongValue::value_typestr()
{
    return LongType::value_typestr();
}

std::size_t LongValue::hash_value() const
{
    return static_cast<std::size_t>(value_);
}

int LongValue::compare(const Value& that) const
{
    LongValue dummy(that);
    return compare(*this, dummy);
}

int LongValue::compare(const LongValue& a, const LongValue& b)
{
    if(a.value_ == b.value_)
        return 0;
    return a.value_<b.value_ ? -1 : +1;
}

const LongValue& LongValue::narrow(const Value& v)
{
    const LongValue* p = dynamic_cast<const LongValue*>(&v);
    if(!p)
        THROW_ERROR_MSG(TypeError, "invalid cast from " << v.typestr() << " to LongValue");
    return *p;
}

SmartPtr<Value> LongValue::clone() const
{
    return SmartValue(new LongValue(*this));
}

SmartBuilder LongValue::create_builder() const
{
    return SmartBuilder(new LongBuilder(*this));
}


void LongValue::assign(const Value& v)
{
    {
        const LongValue* pv = dynamic_cast<const LongValue*>(&v);
        if(pv)
        {
            value_ = pv->value_;
            return;
        }
    }
    {
        const ShortValue* pv = dynamic_cast<const ShortValue*>(&v);
        if(pv)
        {
            value_ = pv->value();
            return;
        }
    }
    {
        const EnumerationValue* pv = dynamic_cast<const EnumerationValue*>(&v);
        if(pv)
        {
            value_ = pv->get_as_int();
            return;
        }
    }
    THROW_ERROR_MSG(TypeError, "invalid assignment from " <<
        v.typestr() << " to LongValue");
}

bool LongValue::can_assign(const Value& v) const
{
    return
        dynamic_cast<const LongValue*>(&v)!=0 ||
        dynamic_cast<const ShortValue*>(&v)!=0 ||
        dynamic_cast<const EnumerationValue*>(&v)!=0;
}


} // /namespace
} // /namespace
} // /namespace
