#include "StructValue.h"
#include "StructBuilder.h"

namespace wamas {
namespace platform {
namespace utils {


void StructValue::operator=(const StructValue& src)
{
    type_ = src.type_;
    members_ = src.members_;
    the_hash_value_ = src.the_hash_value_;
    is_hash_value_ok_ = src.is_hash_value_ok_;
}


StructValue::StructValue(const SmartStructType& t)
: type_(t)
, is_hash_value_ok_(false)
{
    if(t)
    {
        const StructType::Names& names = t->all_member_names();
        for(std::size_t i=0; i<names.size(); ++i)
        {
            const std::string& n = names[i];
            members_[n] = Value::default_value(t->get_member(n));
        }
    }
}


SmartType StructValue::type() const
{
    return type_;
}


std::size_t StructValue::hash_value() const
{
    if(!is_hash_value_ok_)
    {
        the_hash_value_ = 0;
        if(type_)
        {
            const StructType::Names& names = type_->all_member_names();
            for(std::size_t i=0; i<names.size(); ++i)
            {
                SmartValue v = get(names[i]);
                if(v)
                {
                    the_hash_value_ = 5*the_hash_value_ + v->hash_value();
                }
            }
        }
        else
        {
            Members::const_iterator it = members_.begin();
            while(it!=members_.end())
            {
                SmartValue v = it->second;
                if(v)
                {
                    the_hash_value_ = 5*the_hash_value_ + v->hash_value();
                }
                ++it;
            }
        }
        is_hash_value_ok_ = true;
    }
    return the_hash_value_;
}


bool StructValue::has_member(const std::string& name) const
{
    return members_.find(name)!=members_.end();
}


SmartValue StructValue::get(const std::string& name) const
{
    Members::const_iterator pos = members_.find(name);
    if(pos!=members_.end())
        return pos->second;

    if(type_)
        return Value::default_value(type_->get_member(name));

    return SmartValue();
}


const StructValue& StructValue::narrow(const Value& v)
{
    const StructValue* p = dynamic_cast<const StructValue*>(&v);
    if(!p)
        THROW_ERROR_MSG(TypeError, "invalid cast from " << v.typestr() << " to StructValue");
    return *p;
}


int StructValue::compare(const Value& that) const
{
    return compare_struct(narrow(that));
}

int StructValue::compare_struct(const StructValue& other) const
{
    if(type_ && other.type_)
    {
        if(type_!=other.type_)
            THROW_ERROR_MSG(TypeError, "structure type mismatch");
        return compare_typed(type_, this, other);
    }

    std::size_t this_size = members_.size();
    std::size_t that_size = other.members_.size();
    if(this_size<that_size)
        return -1;
    if(this_size>that_size)
        return 1;

    // they have the same number of members
    Members::const_iterator this_it = members_.begin();
    Members::const_iterator that_it = other.members_.begin();
    while(this_it!=members_.end())
    {
        if(this_it->first < that_it->first)
            return -1;
        if(this_it->first > that_it->first)
            return 1;

        // same member name: compare the values
        int x = Value::compare(this_it->second, that_it->second);
        if(x!=0)
            return x;

        ++this_it;
        ++that_it;
    }
    return 0;
}


int StructValue::compare_typed(const SmartStructType& t, const StructValue* a, const StructValue& b)
{
    const StructType::Names& names = t->all_member_names();
    for(std::size_t i=0; i<names.size(); ++i)
    {
        const std::string& n = names[i];
        int x = Value::compare(a->get(n), b.get(n));
        if(x!=0)
            return x;
    }
    return 0;
}


SmartPtr<Value> StructValue::clone() const
{
    StructValue* pv = new StructValue(type_);
    SmartValue result(pv);
    Members::const_iterator it = members_.begin();
    while(it!=members_.end())
    {
        if(it->second)
            pv->members_[it->first] = it->second->clone();
        ++it;
    }
    return result;
}


SmartBuilder StructValue::create_builder() const
{
    return SmartBuilder(new StructBuilder(*this));
}


StructValue::StructValue(const SmartStructType& t, const StructValue& v)
: type_(t)
, is_hash_value_ok_(false)
{
    if(t)
    {
        Members::const_iterator it = v.members_.begin();
        while(it!=v.members_.end())
        {
            std::string name = it->first;
            SmartValue value = it->second;
            members_[name] = t->get_member(name)->fit(value);
            ++it;
        }
    }
    else
    {
        members_ = v.members_;
    }
}


} // /namespace
} // /namespace
} // /namespace
