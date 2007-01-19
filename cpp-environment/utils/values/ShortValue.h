#ifndef WX__Utils__ShortValue_H
#define WX__Utils__ShortValue_H

#include "Value.h"

namespace wamas {
namespace platform {
namespace utils {


/**
@brief short integer value
*/
class ShortValue : public Value
{
public:
    friend class ShortBuilder;

    /// default ctor; sets the value to zero
    ShortValue() : value_(0) {}

    /// copy ctor
    ShortValue(const ShortValue& src) : value_(src.value_) {}

    /**
    @brief copy ctor for the base class
    @throw TypeError if v is not a ShortValue
    */
    explicit ShortValue(const Value& v) : value_(narrow(v).value_) {}

    /// ctor for constant values
    explicit ShortValue(short value) : value_(value) {}

    /**@name Value implementation */
    //@{
    virtual SmartType type() const;
    virtual std::size_t hash_value() const;
    virtual int compare(const Value& that) const;
    virtual SmartPtr<Value> clone() const;
    virtual SmartBuilder create_builder() const;
    //@}

    /// the value
    short value() const
    { return value_; }

    /**
    @internal reference downcast
    @throw TypeError if v is not a ShortValue
    */
    static const ShortValue& narrow(const Value& v);

    /**
    @internal compares two short integer values
    */
    static int compare(const ShortValue& a, const ShortValue& b);

    /**
    @internal value type string
    */
    static std::string const& value_typestr();

protected:
    short value_;

private:
    // internal
    void operator=(const ShortValue& src)
    { value_ = src.value_; }
};


typedef TypedSmartValue<ShortValue> SmartShortValue;


} // /namespace
} // /namespace
} // /namespace

#endif
