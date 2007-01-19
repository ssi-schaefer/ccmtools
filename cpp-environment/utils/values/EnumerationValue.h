#ifndef WX__Utils__EnumerationValue_H
#define WX__Utils__EnumerationValue_H

#include "Value.h"
#include "EnumerationType.h"

namespace wamas {
namespace platform {
namespace utils {


/**
@brief enumeration value
*/
class EnumerationValue : public Value
{
public:
    friend class EnumerationBuilder;

    /**
    @brief copy ctor
    */
    EnumerationValue(const EnumerationValue& src)
    : type_(src.type_)
    , index_(src.index_)
    {}

    /**
    @brief copy ctor for the base class
    @throw TypeError if v is not an EnumerationValue (or compatible)
    */
    explicit EnumerationValue(const Value& v)
    { assign(v); }

    /**
    @brief creates a typed enumeration
    @param t the type
    @param i index
    @throw EnumerationType::RangeError if the index is out of range
    */
    EnumerationValue(const SmartEnumerationType& t, int i=0);

    /**
    @brief creates a typed enumeration
    @param t the type
    @param literal literal name
    @throw EnumerationType::LiteralError if the literal is unknown
    */
    EnumerationValue(const SmartEnumerationType& t, const std::string& literal);

    /**
    @internal changes the type of an value
    @throw TypeError if v is not an EnumerationValue (or compatible)
    */
    EnumerationValue(const SmartEnumerationType& t, const Value& v)
    : type_(t)
    {
        assign(v);
        type_ = t;  // we need that, if 'v' is an EnumerationValue
    }

    /**
    @brief creates an enumeration without type
    @param index the iteger value
    */
    explicit EnumerationValue(int index)
    : index_(index)
    {}

    /**@name Value implementation */
    //@{
    virtual SmartType type() const;
    virtual std::size_t hash_value() const;
    virtual int compare(const Value& that) const;
    virtual SmartPtr<Value> clone() const;
    virtual SmartBuilder create_builder() const;
    //@}

    /**
    @internal reference downcast
    @throw TypeError if v is not an EnumerationValue
    */
    static const EnumerationValue& narrow(const Value& v);

    /**
    @brief returns the integer value
    */
    int get_as_int() const
    { return index_; }

    /**
    @brief returns the literal name
    @throw TypeError if this enumeration is untyped
    */
    const std::string& get_as_string() const;

    /**
    @internal compares two enumeration values
    */
    static int compare(const EnumerationValue& a, const EnumerationValue& b);

protected:
    SmartEnumerationType type_;
    int index_;

private:
    // internal
    void operator=(const EnumerationValue& src)
    { type_ = src.type_; index_ = src.index_; }

    // internal
    void assign(const Value& v);
    bool can_assign(const Value& v) const;
};


typedef TypedSmartValue<EnumerationValue> SmartEnumerationValue;


} // /namespace
} // /namespace
} // /namespace

#endif
