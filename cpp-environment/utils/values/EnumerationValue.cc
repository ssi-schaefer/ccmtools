#include "EnumerationValue.h"
#include "EnumerationBuilder.h"
#include "LongValue.h"
#include "ShortValue.h"
#include "StringValue.h"

namespace wamas {
namespace platform {
namespace utils {


EnumerationValue::EnumerationValue(const SmartEnumerationType& t, int i)
: type_(t)
{
    if(t)
        index_ = t->member_index(i);
    else
        index_ = i;
}

EnumerationValue::EnumerationValue(const SmartEnumerationType& t, const std::string& literal)
: type_(t)
{
    if(!t)
        THROW_ERROR_MSG(TypeError, "cannot find literal: no type");
    index_ = t->member_index(literal);
}

SmartType EnumerationValue::type() const
{
    return type_;
}

std::size_t EnumerationValue::hash_value() const
{
    return static_cast<std::size_t>(index_);
}

int EnumerationValue::compare(const Value& that) const
{
    EnumerationValue dummy(that);
    return compare(*this, dummy);
}

int EnumerationValue::compare(const EnumerationValue& a, const EnumerationValue& b)
{
    if(a.index_==b.index_)
        return 0;
    return a.index_<b.index_ ? -1 : +1;
}

const EnumerationValue& EnumerationValue::narrow(const Value& v)
{
    const EnumerationValue* p = dynamic_cast<const EnumerationValue*>(&v);
    if(!p)
        THROW_ERROR_MSG(TypeError, "invalid cast from " << v.typestr() << " to EnumerationValue");
    return *p;
}

void EnumerationValue::assign(const Value& v)
{
    const EnumerationValue* pev = dynamic_cast<const EnumerationValue*>(&v);
    if(pev)
    {
        operator=(*pev);
        return;
    }

    const LongValue* plv = dynamic_cast<const LongValue*>(&v);
    if(plv)
    {
        if(type_)
            index_ = type_->member_index(plv->value());
        else
            index_ = plv->value();
        return;
    }

    const ShortValue* psv = dynamic_cast<const ShortValue*>(&v);
    if(psv)
    {
        if(type_)
            index_ = type_->member_index(psv->value());
        else
            index_ = psv->value();
        return;
    }

    const StringValue* pstr = dynamic_cast<const StringValue*>(&v);
    if(pstr)
    {
        if(type_)
        {
            index_ = type_->member_index(pstr->value());
            return;
        }
        THROW_ERROR_MSG(TypeError, "enumeration without type");
    }

    THROW_ERROR_MSG(TypeError, "invalid assignment from " <<
        v.typestr() << " to EnumerationValue");
}

bool EnumerationValue::can_assign(const Value& v) const
{
    try
    {
        EnumerationValue dummy(*this);
        dummy.assign(v);
        return true;
    }
    catch(...)
    {
        return false;
    }
}

const std::string& EnumerationValue::get_as_string() const
{
    if(!type_)
        THROW_ERROR_MSG(TypeError, "enumeration without type");
    return type_->get_as_string(index_);
}

SmartPtr<Value> EnumerationValue::clone() const
{
    return SmartValue(new EnumerationValue(*this));
}

SmartBuilder EnumerationValue::create_builder() const
{
    return SmartBuilder(new EnumerationBuilder(*this));
}


} // /namespace
} // /namespace
} // /namespace
