#ifndef WX__Utils__StructBuilder_H
#define WX__Utils__StructBuilder_H

#include "Builder.h"
#include "StructValue.h"

namespace wamas {
namespace platform {
namespace utils {


/**
@brief builder for structure values

@note A StructBuilder is much slower than a StructValue,
      so don't use it as key or value for maps, sets etc.
*/
class StructBuilder : public Builder
{
public:
    WX_UTILS_BUILDER_DEFAULT_IMPLEMENTATION

    /**
    @brief assignment to this from that
    @throw TypeError if the assignment failed
    */
    StructBuilder(const Builder& that)
    { assign(that); }

    /**
    @brief assignment to this from that
    @throw TypeError if the assignment failed
    */
    StructBuilder(const Value& that)
    { assign(that); }

    /**
    @brief copy ctor from builder; makes a deep copy
    */
    StructBuilder(const StructBuilder& src)
    { operator=(src); }

    /**
    @brief copy ctor from value; makes a deep copy
    */
    explicit StructBuilder(const StructValue& v)
    { operator=(v); }

    /**
    @brief creates a typed structure; every member has it's default value
    @param t the type
    */
    explicit StructBuilder(const SmartStructType& t);

    /**
    @brief creates a structure without type and members
    */
    StructBuilder()
    : is_hash_value_ok_(false)
    {}

    /** @name Builder implementation */
    //@{
    virtual SmartType type() const;
    virtual bool can_assign_builder(const Builder& b) const;
    virtual bool can_assign_value(const Value& v) const;
    virtual void assign_builder(const Builder& that);
    virtual void assign_value(const Value& that);
    virtual std::size_t hash_value() const;
    virtual SmartValue new_value() const;
    virtual SmartPtr<Builder> clone() const;
    virtual int compare_builder(const Builder& that) const;
    virtual int compare_value(const Value& that) const;
    //@}

    /**
    @internal reference downcast
    @throw TypeError if b is not a StructBuilder
    */
    static const StructBuilder& narrow(const Builder& b);

    /// assignment (deep copy)
    StructBuilder& operator=(const StructBuilder& b);

    /// assignment (deep copy)
    StructBuilder& operator=(const StructValue& v);

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
    SmartBuilder get(const std::string& name) const;

    /**
    @brief sets a member
    @throw TypeError if a member with that name is not known
    */
    void set(const std::string& name, SmartBuilder b);

    /**
    @brief sets a member
    @throw TypeError if a member with that name is not known
    */
    void set(const std::string& name, SmartValue v);

    /**
    @brief sets a member
    @throw TypeError if a member with that name is not known
    */
    void set(const std::string& name, const Builder& b);

    /**
    @brief sets a member
    @throw TypeError if a member with that name is not known
    */
    void set(const std::string& name, const Value& v);

    /**
    @brief sets a member
    @throw TypeError if a member with that name is not known
    */
    template<class BUILDER>
    void set(const std::string& name, const TypedSmartBuilder<BUILDER>& b)
    { do_set(name, SmartBuilder(b.ptr())); }

    /**
    @brief removes a member
    @throw TypeError if a member with that name is not known
    */
    void remove(const std::string& name);

    /**
    @brief returns the number of members
    */
    int size() const
    { return members_.size(); }

    /** @name access to all members */
    //@{
    typedef std::map<std::string, SmartBuilder> Members;
    typedef Members::const_iterator Iterator;
    typedef std::pair<Iterator, Iterator> Pair;

    Pair all_members() const
    { return Pair(members_.begin(), members_.end()); }
    //@}

    /**
    @internal ctor for wrappers
    */
    StructBuilder(const SmartStructType& t, const StructBuilder& v)
    : type_(t), members_(v.members_), is_hash_value_ok_(false)
    {}

protected:
    SmartStructType type_;
    Members members_;
    mutable std::size_t the_hash_value_;
    mutable bool is_hash_value_ok_;

private:
    void do_set(const std::string& name, SmartBuilder b);
};


typedef TypedSmartBuilder<StructBuilder> SmartStructBuilder;


} // /namespace
} // /namespace
} // /namespace

#endif
