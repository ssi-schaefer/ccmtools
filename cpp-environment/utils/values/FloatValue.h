#ifndef WX__Utils__FloatValue_H
#define WX__Utils__FloatValue_H

#include "Value.h"

namespace wamas {
namespace platform {
namespace utils {


/**
@brief float value
*/
class FloatValue : public Value
{
public:
    friend class FloatBuilder;

    /// default ctor; sets the value to zero
    FloatValue() : value_(0) {}

    /// copy ctor
    FloatValue(const FloatValue& src) : value_(src.value_) {}

    /**
    @brief copy ctor for the base class
    @throw TypeError if v is not a FloatValue
    */
    explicit FloatValue(const Value& v) : value_(narrow(v).value_) {}

    /// ctor for constant values
    explicit FloatValue(float value) : value_(value) {}

    /**@name Value implementation */
    //@{
    virtual SmartType type() const;
    virtual std::size_t hash_value() const;
    virtual int compare(const Value& that) const;
    virtual SmartPtr<Value> clone() const;
    virtual SmartBuilder create_builder() const;
    //@}

    /// the value
    float value() const
    { return value_; }

    /**
    @internal reference downcast
    @throw TypeError if v is not a FloatValue
    */
    static const FloatValue& narrow(const Value& v);

    /**
    @internal compares two float values
    */
    static int compare(const FloatValue& a, const FloatValue& b);

    /**
    @internal value type string
    */
    static std::string const& value_typestr();

protected:
    float value_;

private:
    // internal
    void operator=(const FloatValue& src)
    { value_ = src.value_; }
};


typedef TypedSmartValue<FloatValue> SmartFloatValue;


} // /namespace
} // /namespace
} // /namespace

#endif
