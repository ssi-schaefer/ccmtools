#ifndef WX__Utils__StructValue_H
#define WX__Utils__StructValue_H

#include "Value.h"
#include "StructType.h"

namespace wamas {
namespace platform {
namespace utils {


/**
@brief structure value
*/
class StructValue : public Value
{
public:
    /**
    @brief copy ctor (shallow copy!)
    */
    StructValue(const StructValue& src)
    : type_(src.type_)
    , members_(src.members_)
    , the_hash_value_(src.the_hash_value_)
    , is_hash_value_ok_(src.is_hash_value_ok_)
    {}

    /**
    @brief copy ctor for the base class (shallow copy!)
    @throw TypeError if v is not a StructValue
    */
    explicit StructValue(const Value& v)
    { operator=(narrow(v)); }

    /**
    @brief copy ctor for the base class (shallow copy!)
    @throw TypeError if v is not a StructValue
    */
    explicit StructValue(const SmartValue& v)
    { operator=(narrow(v)); }

    /**
    @brief creates a typed structure; every member has it's default value
    @param t the type
    */
    explicit StructValue(const SmartStructType& t);

    /**
    @internal creates a structure without type and members
    */
    StructValue()
    : is_hash_value_ok_(false)
    {}

    /** @name Value implementation */
    //@{
    virtual SmartType type() const;
    virtual std::size_t hash_value() const;
    virtual int compare(const Value& that) const;
    virtual SmartPtr<Value> clone() const;
    virtual SmartBuilder create_builder() const;
    //@}

    /**
    @internal reference downcast
    @throw TypeError if v is not a StructValue
    */
    static const StructValue& narrow(const Value& v);

    /**
    @internal reference downcast
    @throw TypeError if v is not a StructValue
    */
    static const StructValue& narrow(const SmartValue& v)
    { return narrow(*(v.cptr())); }

    /**
    @brief checks if this structure has a member with that name
    */
    bool has_member(const std::string& name) const;

    /**
    @brief returns that member
    @note If this is a typed structure, the return value is the default
          value of that member, if the member hasn't been set.
          If the structure has no type and the value has not been set,
          the return value is NULL.
    @throw TypeError if a member with that name doesn't exist
    */
    SmartValue get(const std::string& name) const;

    /**
    @brief returns the structure type object (may be NULL)
    */
    SmartStructType struct_type() const
    { return type_; }

    /** @name access to all members */
    //@{
    typedef std::map<std::string, SmartValue> Members;
    typedef Members::const_iterator Iterator;
    typedef std::pair<Iterator, Iterator> Pair;

    Pair all_members() const
    { return Pair(members_.begin(), members_.end()); }
    //@}

    /**
    @internal ctor for builders
    */
    StructValue(const SmartStructType& t, const Members& m)
    : type_(t), members_(m), is_hash_value_ok_(false)
    {}

    /**
    @internal ctor for wrappers
    */
    StructValue(const SmartStructType& t, const StructValue& v);

    /// compares two structure values
    int compare_struct(const StructValue& that) const;

protected:
    SmartStructType type_;
    Members members_;
    mutable std::size_t the_hash_value_;
    mutable bool is_hash_value_ok_;

    // internal
    void operator=(const StructValue& src);
private:
    static int compare_typed(const SmartStructType& t, const StructValue* a, const StructValue& b);
};


typedef TypedSmartValue<StructValue> SmartStructValue;


} // /namespace
} // /namespace
} // /namespace

#endif
