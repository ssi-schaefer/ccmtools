#ifndef WX__Utils__DoubleBuilder_H
#define WX__Utils__DoubleBuilder_H

#include "SimpleBuilder.h"
#include "DoubleValue.h"

namespace wamas {
namespace platform {
namespace utils {


/**
@brief builder for float values
*/
class DoubleBuilder : public SimpleBuilder
{
public:
    WX_UTILS_BUILDER_DEFAULT_IMPLEMENTATION

    DoubleBuilder() {}
    DoubleBuilder(const DoubleBuilder& src) : value_(src.value_) {}
    explicit DoubleBuilder(double value) : value_(value) {}
    explicit DoubleBuilder(const DoubleValue& value) : value_(value) {}

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
    double get_value() const
    { return value_.value_; }

    /// sets the value
    void set_value(double v)
    { value_.value_ = v; }

    /// reads the value
    double value() const
    { return value_.value_; }

    /**
    @internal reference downcast
    @throw TypeError if b is not a DoubleBuilder
    */
    static const DoubleBuilder& narrow(const Builder& b);

    DoubleBuilder& operator=(const DoubleBuilder& b)
    { value_ = b.value_; return *this; }

    DoubleBuilder& operator=(const DoubleValue& v)
    { value_ = v; return *this; }

    DoubleBuilder& operator=(double v)
    { value_.value_ = v; return *this; }

protected:
    DoubleValue value_;
};


typedef TypedSmartBuilder<DoubleBuilder> SmartDoubleBuilder;


} // /namespace
} // /namespace
} // /namespace

#endif
