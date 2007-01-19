#include "Value.h"
#include "Builder.h"

namespace wamas {
namespace platform {
namespace utils {


const std::string& Value::typestr() const
{
    static const std::string null_value("(null value)");
    static const std::string null_type("(null type)");
    if(!this)
        return null_value;
    SmartType t = this->type();
    if(!t)
        return null_type;
    return t->typestr();
}


SmartValue Value::default_value(SmartType t)
{
    if(t)
        return t->default_value();
    else
        return SmartValue();
}


SmartBuilder Value::new_builder(SmartPtr<Value> v)
{
    if(v)
        return v->create_builder();
    else
        return SmartBuilder();
}


int Value::compare(SmartPtr<Value> a, SmartPtr<Value> b)
{
    const Value* pa = a.cptr();
    const Value* pb = b.cptr();
    if(pa)
    {
        if(pb)
            return pa->compare(*pb);
        else
            return 1;
    }
    else
        return pb ? -1 : 0;
}


SmartPtr<Value> Value::get_from_builder(const SmartBuilder& sb)
{
    if(sb)
        return sb->new_value();
    else
        return SmartValue();
}


} // /namespace
} // /namespace
} // /namespace
