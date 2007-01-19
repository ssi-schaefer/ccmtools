#ifndef WX__Utils__BooleanValue_H
#define WX__Utils__BooleanValue_H

#include "Value.h"

namespace wamas {
namespace platform {
namespace utils {


/**
@brief boolean value
*/
class BooleanValue : public Value
{
public:
    friend class BooleanBuilder;

    /// default ctor; sets the value to false
    BooleanValue() : value_(false) {}

    /// copy ctor
    BooleanValue(const BooleanValue& src) : value_(src.value_) {}

    /**
    @brief copy ctor for the base class
    @throw TypeError if v is not a BooleanValue
    */
    explicit BooleanValue(const Value& v) : value_(narrow(v).value_) {}

    /// ctor for constant values
    explicit BooleanValue(bool value) : value_(value) {}

    /**@name Value implementation */
    //@{
    virtual SmartType type() const;
    virtual std::size_t hash_value() const;
    virtual int compare(const Value& that) const;
    virtual SmartPtr<Value> clone() const;
    virtual SmartBuilder create_builder() const;
    //@}

    /// the value
    bool value() const
    { return value_; }

    /**
    @internal reference downcast
    @throw TypeError if v is not a BooleanValue
    */
    static const BooleanValue& narrow(const Value& v);

    /**
    @internal compares two boolean values
    */
    static int compare(const BooleanValue& a, const BooleanValue& b);

    /**
    @internal value type string
    */
    static std::string const& value_typestr();

protected:
    bool value_;

private:
    // internal
    void operator=(const BooleanValue& src)
    { value_ = src.value_; }
};


typedef TypedSmartValue<BooleanValue> SmartBooleanValue;


} // /namespace
} // /namespace
} // /namespace

#endif
