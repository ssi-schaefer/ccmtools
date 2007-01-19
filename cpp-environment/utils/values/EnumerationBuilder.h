#ifndef WX__Utils__EnumerationBuilder_H
#define WX__Utils__EnumerationBuilder_H

#include "SimpleBuilder.h"
#include "EnumerationValue.h"

namespace wamas {
namespace platform {
namespace utils {


/**
@brief builder for enumeration values
*/
class EnumerationBuilder : public SimpleBuilder
{
public:
    WX_UTILS_BUILDER_DEFAULT_IMPLEMENTATION

    /**
    @brief copy ctor from builder
    */
    EnumerationBuilder(const EnumerationBuilder& src)
    : value_(src.value_)
    {}

    /**
    @brief copy ctor from value
    */
    explicit EnumerationBuilder(const EnumerationValue& v)
    : value_(v)
    {}

    /**
    @brief creates a typed enumeration
    @param t the type
    @param i index
    @throw EnumerationType::RangeError if the index is out of range
    */
    EnumerationBuilder(const SmartEnumerationType& t, int i=0)
    : value_(t, i)
    {}

    /**
    @brief creates a typed enumeration
    @param t the type
    @param literal literal name
    @throw EnumerationType::LiteralError if the literal is unknown
    */
    EnumerationBuilder(const SmartEnumerationType& t, const std::string& literal)
    : value_(t, literal)
    {}

    /**
    @brief creates an enumeration without type
    @param index the iteger value
    */
    explicit EnumerationBuilder(int index)
    : value_(index)
    {}

    /** @name Builder implementation */
    //@{
    virtual SmartType type() const;
    virtual bool can_assign_value(const Value& v) const;
    virtual void assign_value(const Value& that);
    virtual std::size_t hash_value() const;
    virtual SmartValue new_value() const;
    virtual SmartPtr<Builder> clone() const;
    virtual const Value& the_value() const;
    //@}

    /**
    @internal reference downcast
    @throw TypeError if b is not an EnumerationBuilder
    */
    static const EnumerationBuilder& narrow(const Builder& b);

    /// assignment
    EnumerationBuilder& operator=(const EnumerationBuilder& b)
    { value_ = b.value_; return *this; }

    /// assignment
    EnumerationBuilder& operator=(const EnumerationValue& v)
    { value_ = v; return *this; }

    /**
    @brief returns the integer value
    */
    int get_as_int() const
    { return value_.get_as_int(); }

    /**
    @brief returns the literal name
    @throw TypeError if this enumeration is untyped
    */
    const std::string& get_as_string() const
    { return value_.get_as_string(); }

    /**
    @brief sets the literal by it's index
    @throw EnumerationType::RangeError if the index is out of range
    */
    void set_as_int(int index);

    /**
    @brief sets the literal by it's name
    @param literal literal name
    @throw EnumerationType::LiteralError if the literal is unknown
    */
    void set_as_string(const std::string& literal);

protected:
    EnumerationValue value_;
};


typedef TypedSmartBuilder<EnumerationBuilder> SmartEnumerationBuilder;


} // /namespace
} // /namespace
} // /namespace

#endif
