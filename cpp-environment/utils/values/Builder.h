#ifndef WX__Utils__Builder_H
#define WX__Utils__Builder_H

#include "Value.h"

namespace wamas {
namespace platform {
namespace utils {


/**
@brief abstract base for all value builders
*/
class Builder : virtual public wamas::platform::utils::RefCounted
{
public:
    virtual ~Builder() {}

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
    @brief assignment; only valid if can_assign(b)==true
    @note Every sub-class has also to implement an assignment-operator with it's own type!
    @throw TypeError if the assignment failed
    */
    virtual Builder& operator=(const Builder& b) = 0;

    /**
    @brief assignment; only valid if can_assign(v)==true
    @note Every sub-class has also to implement an assignment-operator with it's own type!
    @throw TypeError if the assignment failed
    */
    virtual Builder& operator=(const Value& v) = 0;

    /**
    @brief checks if we can call 'assign' without any problems
    */
    bool can_assign(const Builder& b) const
    { return this->can_assign_builder(b); }

    /**
    @brief checks if we can call 'assign' without any problems
    */
    bool can_assign(const Value& v) const
    { return this->can_assign_value(v); }

    /**
    @brief assignment to this from that
    @throw TypeError if the assignment failed
    */
    void assign(const Builder& that)
    { this->assign_builder(that); }

    /**
    @brief assignment to this from that
    @throw TypeError if the assignment failed
    */
    void assign(const Value& that)
    { this->assign_value(that); }

    /**
    @brief calculates a hash-value
    */
    virtual std::size_t hash_value() const = 0;

    /**
    @brief compares two compatible builders
    @return <0 if this is less than that, >0 if greater, and ==0 if equal
    @throw TypeError if the two builders are not compatible
    */
    int compare(const Builder& that) const
    { return this->compare_builder(that); }

    /**
    @brief compares a builder and a value
    @return <0 if this is less than that, >0 if greater, and ==0 if equal
    @throw TypeError if the builder and the value are not compatible
    */
    int compare(const Value& that) const
    { return this->compare_value(that); }

    /**
    @brief returns a new value of this type
    */
    virtual SmartValue new_value() const = 0;

    /**
    @brief creates a deep copy of this builder
    */
    virtual SmartPtr<Builder> clone() const = 0;

protected:
    static SmartPtr<Builder> safe_clone(SmartPtr<Builder> b);

    virtual void assign_builder(const Builder& that) = 0;
    virtual void assign_value(const Value& that) = 0;
    virtual bool can_assign_builder(const Builder& b) const = 0;
    virtual bool can_assign_value(const Value& v) const = 0;
    virtual int compare_builder(const Builder& that) const = 0;
    virtual int compare_value(const Value& that) const = 0;
};


///@internal for common operators
#define WX_UTILS_BUILDER_DEFAULT_IMPLEMENTATION \
    Builder& operator=(const Builder& b)        \
    { assign_builder(b); return *this; }        \
    Builder& operator=(const Value& v)          \
    { assign_value(v); return *this; }


template<typename T>
inline bool operator==(const Builder& l, const T& r) {
   return l.compare(r) == 0;
}

template<typename T>
inline bool operator!=(const Builder& l, const T& r) {
   return l.compare(r) != 0;
}

template<typename T>
inline bool operator<(const Builder& l, const T& r) {
   return l.compare(r) < 0;
}

template<typename T>
inline bool operator<=(const Builder& l, const T& r) {
   return l.compare(r) <= 0;
}

template<typename T>
inline bool operator>(const Builder& l, const T& r) {
   return l.compare(r) > 0;
}

template<typename T>
inline bool operator>=(const Builder& l, const T& r) {
   return l.compare(r) >= 0;
}


/**
Calculates the hash-value of an instance of Builder.
Use this struct together with wamas::platform::utils::HashMap.
*/
struct BuilderHash
{
    std::size_t operator()(Builder const& v) const
    {
        return v.hash_value();
    }
};


/**
@internal typed smart pointer for builders
*/
template<class BUILDER>
class TypedSmartBuilder : public SmartPtr<BUILDER>
{
public:
    TypedSmartBuilder()
    {}

    TypedSmartBuilder(BUILDER* pv)
    : SmartPtr<BUILDER>(pv)
    {}

    TypedSmartBuilder(Builder* pv)
    : SmartPtr<BUILDER>(narrow(pv))
    {}

    TypedSmartBuilder(const SmartBuilder& sv)
    : SmartPtr<BUILDER>(narrow(sv.ptr()))
    {}

private:
    static BUILDER* narrow(Builder* pv)
    {
        if(!pv)
            return 0;
        const BUILDER& ref = BUILDER::narrow(*pv);
        return const_cast<BUILDER*>(&ref);
    }
};


} // /namespace
} // /namespace
} // /namespace

#endif
