#ifndef WX__Utils__StringValue_H
#define WX__Utils__StringValue_H

#include "Value.h"

namespace wamas {
namespace platform {
namespace utils {


/**
@brief string value
*/
class StringValue : public Value
{
public:
    friend class StringBuilder;

    /// ctor for empty strings
    StringValue()
    : is_hash_value_ok_(false)
    {}

    /// copy ctor
    StringValue(const StringValue& src)
    : value_(src.value_)
    , the_hash_value_(src.the_hash_value_)
    , is_hash_value_ok_(src.is_hash_value_ok_)
    {}

    /**
    @brief copy ctor for the base class
    @throw TypeError if v is not a StringValue
    */
    explicit StringValue(const Value& v)
    { operator=(narrow(v)); }

    /// ctor for constant values
    explicit StringValue(const std::string& value)
    : value_(value)
    , is_hash_value_ok_(false)
    {}

    /**@name Value implementation */
    //@{
    virtual SmartType type() const;
    virtual std::size_t hash_value() const;
    virtual int compare(const Value& that) const;
    virtual SmartPtr<Value> clone() const;
    virtual SmartBuilder create_builder() const;
    //@}

    /// the value
    const std::string& value() const
    { return value_; }

    /**
    @internal reference downcast
    @throw TypeError if v is not a StringValue
    */
    static const StringValue& narrow(const Value& v);

    /**
    @internal compares two string values
    */
    static int compare(const StringValue& a, const StringValue& b);

    /**
    @internal value type string
    */
    static std::string const& value_typestr();

protected:
    std::string value_;
    mutable std::size_t the_hash_value_;
    mutable bool is_hash_value_ok_;

private:
    // internal
    void operator=(const StringValue& src)
    {
        value_ = src.value_;
        the_hash_value_ = src.the_hash_value_;
        is_hash_value_ok_ = src.is_hash_value_ok_;
    }
    void operator=(const std::string& v)
    {
        value_ = v;
        is_hash_value_ok_ = false;
    }
};


typedef TypedSmartValue<StringValue> SmartStringValue;


} // /namespace
} // /namespace
} // /namespace

#endif
