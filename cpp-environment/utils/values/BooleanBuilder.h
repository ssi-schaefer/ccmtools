#ifndef WX__Utils__BooleanBuilder_H
#define WX__Utils__BooleanBuilder_H

#include "SimpleBuilder.h"
#include "BooleanValue.h"

namespace wamas {
namespace platform {
namespace utils {


/**
@brief builder for boolean values
*/
class BooleanBuilder : public SimpleBuilder
{
public:
    WX_UTILS_BUILDER_DEFAULT_IMPLEMENTATION

    BooleanBuilder() {}
    BooleanBuilder(const BooleanBuilder& src) : value_(src.value_) {}
    explicit BooleanBuilder(bool value) : value_(value) {}
    explicit BooleanBuilder(const BooleanValue& value) : value_(value) {}

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
    bool get_value() const
    { return value_.value_; }

    /// sets the value
    void set_value(bool v)
    { value_.value_ = v; }

    /// reads the value
    bool value() const
    { return value_.value_; }

    /**
    @internal reference downcast
    @throw TypeError if b is not a BooleanBuilder
    */
    static const BooleanBuilder& narrow(const Builder& b);

    BooleanBuilder& operator=(const BooleanBuilder& b)
    { value_ = b.value_; return *this; }

    BooleanBuilder& operator=(const BooleanValue& v)
    { value_ = v; return *this; }

    BooleanBuilder& operator=(bool v)
    { value_.value_ = v; return *this; }

protected:
    BooleanValue value_;
};


typedef TypedSmartBuilder<BooleanBuilder> SmartBooleanBuilder;


} // /namespace
} // /namespace
} // /namespace

#endif
