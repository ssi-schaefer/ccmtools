#ifndef WX__Utils__FloatBuilder_H
#define WX__Utils__FloatBuilder_H

#include "SimpleBuilder.h"
#include "FloatValue.h"

namespace wamas {
namespace platform {
namespace utils {


/**
@brief builder for float values
*/
class FloatBuilder : public SimpleBuilder
{
public:
    WX_UTILS_BUILDER_DEFAULT_IMPLEMENTATION

    FloatBuilder() {}
    FloatBuilder(const FloatBuilder& src) : value_(src.value_) {}
    explicit FloatBuilder(float value) : value_(value) {}
    explicit FloatBuilder(const FloatValue& value) : value_(value) {}

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
    float get_value() const
    { return value_.value_; }

    /// sets the value
    void set_value(float v)
    { value_.value_ = v; }

    /// reads the value
    float value() const
    { return value_.value_; }

    /**
    @internal reference downcast
    @throw TypeError if b is not a FloatBuilder
    */
    static const FloatBuilder& narrow(const Builder& b);

    FloatBuilder& operator=(const FloatBuilder& b)
    { value_ = b.value_; return *this; }

    FloatBuilder& operator=(const FloatValue& v)
    { value_ = v; return *this; }

    FloatBuilder& operator=(float v)
    { value_.value_ = v; return *this; }

protected:
    FloatValue value_;
};


typedef TypedSmartBuilder<FloatBuilder> SmartFloatBuilder;


} // /namespace
} // /namespace
} // /namespace

#endif
