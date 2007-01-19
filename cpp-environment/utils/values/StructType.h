#ifndef WX__Utils__StructType_H
#define WX__Utils__StructType_H

#include "Type.h"
#include "Value.h"

#include <map>
#include <vector>

namespace wamas {
namespace platform {
namespace utils {


/**
@brief structure type
*/
class StructType : public Type
{
public:
    typedef std::vector<std::string> Names;
    typedef std::map<std::string, SmartType> Members;

    /**
    @brief ctor for builders
    @param name structure name
    @param m all members
    */
    StructType(const std::string& name, const Members& m);

    /**
    @brief ctor (no members)
    @param name structure name
    */
    StructType(const std::string& name);

    /**
    @brief returns the structure name
    */
    const std::string& name() const
    { return name_; }

    /** @name Type implementation */
    //@{
    virtual const std::string& typestr() const;
    virtual SmartValue default_value() const;
    virtual SmartBuilder create() const;
    virtual SmartValue fit(const SmartValue& value) const;
    //@}

    /**
    @internal prefix for 'typestr'
    */
    static const char* typestr_prefix();

    /**
    @brief adds the next member of the structure
    @param name name of the member
    @param type type of the member
    @throw TypeError if a member with that name already exists
    */
    void add_member(const std::string& name, SmartType type);

    /**
    @brief checks if this structure has a member with that name
    */
    bool has_member(const std::string& name) const;

    /**
    @brief returns that member
    @throw TypeError if a member with that name doesn't exist
    */
    SmartType get_member(const std::string& name) const;

    /**
    @brief access to a sequence of all member names
    */
    const Names& all_member_names() const
    { return names_; }

private:
    std::string name_, typestr_;
    Names names_;
    mutable SmartValue default_value_;
    Members members_;
};


typedef TypedSmartType<StructType> SmartStructType;


} // /namespace
} // /namespace
} // /namespace

#endif
