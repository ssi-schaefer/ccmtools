#ifndef WX__Utils__LongValue_H
#define WX__Utils__LongValue_H

#include "Value.h"

namespace wamas {
namespace platform {
namespace utils {


/**
@brief long integer value
*/
class LongValue : public Value
{
public:
    friend class LongBuilder;

    /// default ctor; sets the value to zero
    LongValue() : value_(0) {}

    /// copy ctor
    LongValue(const LongValue& src) : value_(src.value_) {}

    /**
    @brief copy ctor for the base class
    @throw TypeError if v is not a LongValue (or compatible)
    */
    explicit LongValue(const Value& v)
    { assign(v); }

    /// ctor for constant values
    explicit LongValue(long value) : value_(value) {}

    /**@name Value implementation */
    //@{
    virtual SmartType type() const;
    virtual std::size_t hash_value() const;
    virtual int compare(const Value& that) const;
    virtual SmartPtr<Value> clone() const;
    virtual SmartBuilder create_builder() const;
    //@}

    /// the value
    long value() const
    { return value_; }

    /**
    @internal reference downcast
    @throw TypeError if v is not a LongValue
    */
    static const LongValue& narrow(const Value& v);

    /**
    @internal compares two long integer values
    */
    static int compare(const LongValue& a, const LongValue& b);

    /**
    @internal value type string
    */
    static std::string const& value_typestr();

protected:
    long value_;

private:
    // internal
    void operator=(const LongValue& src)
    { value_ = src.value_; }

    // internal
    void assign(const Value& v);
    bool can_assign(const Value& v) const;
};


typedef TypedSmartValue<LongValue> SmartLongValue;


} // /namespace
} // /namespace
} // /namespace

#endif
