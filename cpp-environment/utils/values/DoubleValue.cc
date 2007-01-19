#include "DoubleValue.h"
#include "DoubleType.h"
#include "DoubleBuilder.h"
#include "FloatValue.h"
#include "LongValue.h"

namespace wamas {
namespace platform {
namespace utils {


SmartType DoubleValue::type() const
{
    static const SmartType singleton(new DoubleType);
    return singleton;
}

std::string const& DoubleValue::value_typestr()
{
    return DoubleType::value_typestr();
}

std::size_t DoubleValue::hash_value() const
{
    return static_cast<std::size_t>(value_);
}

int DoubleValue::compare(const Value& that) const
{
    DoubleValue dummy(that);
    return compare(*this, dummy);
}

int DoubleValue::compare(const DoubleValue& a, const DoubleValue& b)
{
    if(a.value_ == b.value_)
        return 0;
    return a.value_<b.value_ ? -1 : +1;
}

const DoubleValue& DoubleValue::narrow(const Value& v)
{
    const DoubleValue* p = dynamic_cast<const DoubleValue*>(&v);
    if(!p)
        THROW_ERROR_MSG(TypeError, "invalid cast from " << v.typestr() << " to DoubleValue");
    return *p;
}

SmartPtr<Value> DoubleValue::clone() const
{
    return SmartValue(new DoubleValue(*this));
}

SmartBuilder DoubleValue::create_builder() const
{
    return SmartBuilder(new DoubleBuilder(*this));
}


void DoubleValue::assign(const Value& v)
{
    {
        const DoubleValue* pv = dynamic_cast<const DoubleValue*>(&v);
        if(pv)
        {
            value_ = pv->value_;
            return;
        }
    }
    {
        const FloatValue* pv = dynamic_cast<const FloatValue*>(&v);
        if(pv)
        {
            value_ = pv->value();
            return;
        }
    }
    try
    {
        LongValue dummy(v);
        value_ = dummy.value();
        return;
    }
    catch(...)
    {
    }
    THROW_ERROR_MSG(TypeError, "invalid assignment from " <<
        v.typestr() << " to DoubleValue");
}

bool DoubleValue::can_assign(const Value& v) const
{
    try
    {
        DoubleValue dummy(*this);
        dummy.assign(v);
        return true;
    }
    catch(...)
    {
        return false;
    }
}


} // /namespace
} // /namespace
} // /namespace
