#ifndef WX__Utils__LongBuilder_H
#define WX__Utils__LongBuilder_H

#include "SimpleBuilder.h"
#include "LongValue.h"

namespace wamas {
namespace platform {
namespace utils {


/**
@brief builder for short integer values
*/
class LongBuilder : public SimpleBuilder
{
public:
    WX_UTILS_BUILDER_DEFAULT_IMPLEMENTATION

    LongBuilder() {}
    LongBuilder(const LongBuilder& src) : value_(src.value_) {}
    explicit LongBuilder(long value) : value_(value) {}
    explicit LongBuilder(const LongValue& value) : value_(value) {}

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
    long get_value() const
    { return value_.value_; }

    /// sets the value
    void set_value(long v)
    { value_.value_ = v; }

    /// reads the value
    long value() const
    { return value_.value_; }

    /**
    @internal reference downcast
    @throw TypeError if b is not a LongBuilder
    */
    static const LongBuilder& narrow(const Builder& b);

    LongBuilder& operator=(const LongBuilder& b)
    { value_ = b.value_; return *this; }

    LongBuilder& operator=(const LongValue& v)
    { value_ = v; return *this; }

    LongBuilder& operator=(long v)
    { value_.value_ = v; return *this; }

protected:
    LongValue value_;
};


typedef TypedSmartBuilder<LongBuilder> SmartLongBuilder;


} // /namespace
} // /namespace
} // /namespace

#endif
