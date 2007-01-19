#ifndef WX__Utils__ShortBuilder_H
#define WX__Utils__ShortBuilder_H

#include "SimpleBuilder.h"
#include "ShortValue.h"

namespace wamas {
namespace platform {
namespace utils {


/**
@brief builder for short integer values
*/
class ShortBuilder : public SimpleBuilder
{
public:
    WX_UTILS_BUILDER_DEFAULT_IMPLEMENTATION

    ShortBuilder() {}
    ShortBuilder(const ShortBuilder& src) : value_(src.value_) {}
    explicit ShortBuilder(short value) : value_(value) {}
    explicit ShortBuilder(const ShortValue& value) : value_(value) {}

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
    short get_value() const
    { return value_.value_; }

    /// sets the value
    void set_value(short v)
    { value_.value_ = v; }

    /// reads the value
    short value() const
    { return value_.value_; }

    /**
    @internal reference downcast
    @throw TypeError if b is not a ShortBuilder
    */
    static const ShortBuilder& narrow(const Builder& b);

    ShortBuilder& operator=(const ShortBuilder& b)
    { value_ = b.value_; return *this; }

    ShortBuilder& operator=(const ShortValue& v)
    { value_ = v; return *this; }

    ShortBuilder& operator=(short v)
    { value_.value_ = v; return *this; }

protected:
    ShortValue value_;
};


typedef TypedSmartBuilder<ShortBuilder> SmartShortBuilder;


} // /namespace
} // /namespace
} // /namespace

#endif
