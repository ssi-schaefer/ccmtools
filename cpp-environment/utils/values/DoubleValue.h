#ifndef WX__Utils__DoubleValue_H
#define WX__Utils__DoubleValue_H

#include "Value.h"

namespace wamas {
namespace platform {
namespace utils {


/**
@brief double value
*/
class DoubleValue : public Value
{
public:
    friend class DoubleBuilder;

    /// default ctor; sets the value to zero
    DoubleValue() : value_(0) {}

    /// copy ctor
    DoubleValue(const DoubleValue& src) : value_(src.value_) {}

    /**
    @brief copy ctor for the base class
    @throw TypeError if v is not a DoubleValue (or compatible)
    */
    explicit DoubleValue(const Value& v)
    { assign(v); }

    /// ctor for constant values
    explicit DoubleValue(double value) : value_(value) {}

    /**@name Value implementation */
    //@{
    virtual SmartType type() const;
    virtual std::size_t hash_value() const;
    virtual int compare(const Value& that) const;
    virtual SmartPtr<Value> clone() const;
    virtual SmartBuilder create_builder() const;
    //@}

    /// the value
    double value() const
    { return value_; }

    /**
    @internal reference downcast
    @throw TypeError if v is not a DoubleValue
    */
    static const DoubleValue& narrow(const Value& v);

    /**
    @internal compares two double values
    */
    static int compare(const DoubleValue& a, const DoubleValue& b);

    /**
    @internal value type string
    */
    static std::string const& value_typestr();

protected:
    double value_;

private:
    // internal
    void operator=(const DoubleValue& src)
    { value_ = src.value_; }

    // internal
    void assign(const Value& v);
    bool can_assign(const Value& v) const;
};


typedef TypedSmartValue<DoubleValue> SmartDoubleValue;


} // /namespace
} // /namespace
} // /namespace

#endif
