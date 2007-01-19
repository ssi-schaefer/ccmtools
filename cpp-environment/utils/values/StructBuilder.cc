#include "StructBuilder.h"

namespace wamas {
namespace platform {
namespace utils {


StructBuilder::StructBuilder(const SmartStructType& t)
{
    StructValue default_value(t);
    operator=(default_value);
}


const StructBuilder& StructBuilder::narrow(const Builder& b)
{
    const StructBuilder* p = dynamic_cast<const StructBuilder*>(&b);
    if(!p)
        THROW_ERROR_MSG(TypeError, "invalid cast from " << b.typestr() << " to StructBuilder");
    return *p;
}


StructBuilder& StructBuilder::operator=(const StructBuilder& b)
{
    type_ = b.type_;
    the_hash_value_ = b.the_hash_value_;
    is_hash_value_ok_ = b.is_hash_value_ok_;
    members_.clear();
    Members::const_iterator it = b.members_.begin();
    while(it!=b.members_.end())
    {
        members_[it->first] = Builder::safe_clone(it->second);
        ++it;
    }
    return *this;
}


StructBuilder& StructBuilder::operator=(const StructValue& v)
{
    type_ = v.struct_type();
    is_hash_value_ok_ = false;
    members_.clear();
    StructValue::Pair p = v.all_members();
    while(p.first!=p.second)
    {
        members_[(p.first)->first] = Value::new_builder((p.first)->second);
        ++(p.first);
    }
    return *this;
}


SmartType StructBuilder::type() const
{
    return type_;
}


bool StructBuilder::can_assign_builder(const Builder& b) const
{
    return dynamic_cast<const StructBuilder*>(&b)!=0;
}

bool StructBuilder::can_assign_value(const Value& v) const
{
    return dynamic_cast<const StructValue*>(&v)!=0;
}


void StructBuilder::assign_builder(const Builder& that)
{
    operator=(narrow(that));
}

void StructBuilder::assign_value(const Value& that)
{
    operator=(StructValue::narrow(that));
}


SmartPtr<Builder> StructBuilder::clone() const
{
    return SmartBuilder(new StructBuilder(*this));
}


SmartValue StructBuilder::new_value() const
{
    StructValue::Members vm;
    Members::const_iterator it = members_.begin();
    while(it!=members_.end())
    {
        if(it->second)
        {
            vm[it->first] = it->second->new_value();
        }
        ++it;
    }
    return SmartValue(new StructValue(type_, vm));
}


std::size_t StructBuilder::hash_value() const
{
    if(!is_hash_value_ok_)
    {
        SmartValue sv = new_value();
        the_hash_value_ = sv->hash_value();
        is_hash_value_ok_ = true;
    }
    return the_hash_value_;
}


int StructBuilder::compare_builder(const Builder& that) const
{
    const StructBuilder& other = narrow(that);

    std::size_t this_size = members_.size();
    std::size_t that_size = other.members_.size();
    if(this_size<that_size)
        return -1;
    if(this_size>that_size)
        return 1;

    SmartValue sv_this = new_value();
    SmartValue sv_that = other.new_value();
    return sv_this->compare(*(sv_that.cptr()));
}


int StructBuilder::compare_value(const Value& that) const
{
    SmartValue sv_this = new_value();
    return sv_this->compare(that);
}


bool StructBuilder::has_member(const std::string& name) const
{
    return members_.find(name)!=members_.end();
}


SmartBuilder StructBuilder::get(const std::string& name) const
{
    Members::const_iterator pos = members_.find(name);
    if(pos!=members_.end())
        return pos->second;

    if(type_)
    {
        SmartValue sv = Value::default_value(type_->get_member(name));
        if(sv)
        {
            SmartBuilder result = sv->create_builder();
            const_cast<StructBuilder*>(this)->members_[name] = result;
            return result;
        }
    }

    return SmartBuilder();
}


void StructBuilder::do_set(const std::string& name, SmartBuilder b)
{
    if(type_ && !type_->has_member(name))
        THROW_ERROR_MSG(TypeError, "unknown member \"" << name << '"');

    members_[name] = b;
    is_hash_value_ok_ = false;
}

void StructBuilder::set(const std::string& name, SmartBuilder b)
{
    do_set(name, b);
}

void StructBuilder::set(const std::string& name, SmartValue v)
{
    do_set(name, Value::new_builder(v));
}

void StructBuilder::set(const std::string& name, const Builder& b)
{
    do_set(name, b.clone());
}

void StructBuilder::set(const std::string& name, const Value& v)
{
    do_set(name, v.create_builder());
}


void StructBuilder::remove(const std::string& name)
{
    if(type_ && !type_->has_member(name))
        THROW_ERROR_MSG(TypeError, "unknown member \"" << name << '"');

    members_.erase(name);
    is_hash_value_ok_ = false;
}


} // /namespace
} // /namespace
} // /namespace
