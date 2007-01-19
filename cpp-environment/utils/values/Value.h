#ifndef WX__Utils__Value_H
#define WX__Utils__Value_H

#include "Type.h"

namespace wamas {
namespace platform {
namespace utils {


/**
@brief abstract base for all values
@note A \a Value is read-only. Use a \a Builder to change values.
*/
class Value : virtual public wamas::platform::utils::RefCounted
{
public:
    virtual ~Value() {}
    Value() {}

    /**
    @brief returns the type object of this value (may be NULL)
    */
    virtual SmartType type() const = 0;

    /**
    @brief returns the type name
    @note It is safe to call this method for untyped values.
    */
    const std::string& typestr() const;

    /**
    @brief calculates a hash-value
    */
    virtual std::size_t hash_value() const = 0;

    /**
    @brief compares two compatible values
    @return <0 if this is less than that, >0 if greater, and ==0 if equal
    @throw TypeError if the two values are not compatible
    */
    virtual int compare(const Value& that) const = 0;

    /**
    @brief creates a deep copy of this value
    */
    virtual SmartPtr<Value> clone() const = 0;

    /**
    @internal compares two values
    */
    static int compare(SmartPtr<Value> a, SmartPtr<Value> b);

    /**
    @internal creates a Builder from a Value
    */
    static SmartBuilder new_builder(SmartPtr<Value> v);

    /**
    @internal creates a Builder from this Value
    */
    virtual SmartBuilder create_builder() const = 0;

    /**
    @internal gets the default value from a type
    */
    static SmartPtr<Value> default_value(SmartType t);

    /**
    @internal gets a new value from a builder
    */
    static SmartPtr<Value> get_from_builder(const SmartBuilder& sb);

protected:
    /// permitted only for sub-classes
    Value(const Value&) {}

private:
    /// not permitted
    Value& operator=(const Value&);
};


inline bool operator==(const Value& l, const Value& r) {
   return l.compare(r) == 0;
}

inline bool operator!=(const Value& l, const Value& r) {
   return l.compare(r) != 0;
}

inline bool operator<(const Value& l, const Value& r) {
   return l.compare(r) < 0;
}

inline bool operator<=(const Value& l, const Value& r) {
   return l.compare(r) <= 0;
}

inline bool operator>(const Value& l, const Value& r) {
   return l.compare(r) > 0;
}

inline bool operator>=(const Value& l, const Value& r) {
   return l.compare(r) >= 0;
}


/**
Calculates the hash-value of an instance of Value.
Use this struct together with wamas::platform::utils::HashMap.
*/
struct ValueHash
{
    std::size_t operator()(Value const& v) const
    {
        return v.hash_value();
    }
};


/**
@internal typed smart pointer for values
*/
template<class VALUE>
class TypedSmartValue : public SmartPtr<VALUE>
{
public:
    TypedSmartValue()
    {}

    TypedSmartValue(VALUE* pv)
    : SmartPtr<VALUE>(pv)
    {}

    TypedSmartValue(Value* pv)
    : SmartPtr<VALUE>(narrow(pv))
    {}

    TypedSmartValue(const SmartValue& sv)
    : SmartPtr<VALUE>(narrow(sv.ptr()))
    {}

    TypedSmartValue(const SmartBuilder& sb)
    {
        SmartValue sv = Value::get_from_builder(sb);
        if(sv)
            this->eat(narrow(sv.ptr()));
    }

private:
    static VALUE* narrow(Value* pv)
    {
        if(!pv)
            return 0;
        const VALUE& ref = VALUE::narrow(*pv);
        return const_cast<VALUE*>(&ref);
    }
};


} // /namespace
} // /namespace
} // /namespace

#endif
