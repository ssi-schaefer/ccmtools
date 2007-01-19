#ifndef WX__Utils__StringBuilder_H
#define WX__Utils__StringBuilder_H

#include "SimpleBuilder.h"
#include "StringValue.h"

namespace wamas {
namespace platform {
namespace utils {


/**
@brief builder for string values
*/
class StringBuilder : public SimpleBuilder
{
public:
    WX_UTILS_BUILDER_DEFAULT_IMPLEMENTATION

    StringBuilder() {}
    StringBuilder(const StringBuilder& src) : value_(src.value_) {}
    explicit StringBuilder(const std::string& value) : value_(value) {}
    explicit StringBuilder(const StringValue& value) : value_(value) {}

    /**@name Builder implementation */
    //@{
    virtual SmartType type() const;
    virtual bool can_assign_value(const Value& v) const;
    virtual void assign_value(const Value& that);
    virtual std::size_t hash_value() const;
    virtual SmartValue new_value() const;
    virtual SmartPtr<Builder> clone() const;
    virtual const Value& the_value() const;
    //@}

    /// reads the value
    const std::string& get_value() const
    { return value_.value(); }

    /// sets the value
    void set_value(const std::string& v)
    { value_ = v; }

    /// reads the value
    const std::string& value() const
    { return value_.value(); }

    /**
    @internal reference downcast
    @throw TypeError if b is not a StringBuilder
    */
    static const StringBuilder& narrow(const Builder& b);

    StringBuilder& operator=(const StringBuilder& b)
    { value_ = b.value_; return *this; }

    StringBuilder& operator=(const StringValue& v)
    { value_ = v; return *this; }

    StringBuilder& operator=(const std::string& v)
    { value_ = v; return *this; }

protected:
    StringValue value_;
};


typedef TypedSmartBuilder<StringBuilder> SmartStringBuilder;


} // /namespace
} // /namespace
} // /namespace

#endif
