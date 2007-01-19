#ifndef WX__Utils__EnumerationType_H
#define WX__Utils__EnumerationType_H

#include "Type.h"
#include "Value.h"

#include <vector>

namespace wamas {
namespace platform {
namespace utils {


/**
@brief enumeration type

@note The index range goes from 0 to max_index().
*/
class EnumerationType : public Type
{
public:
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

    /// sequence of literals
    typedef std::vector<std::string> Members;

    /**
    @brief ctor
    @param name enumeration name
    @param members sequence of literals
    */
    EnumerationType(const std::string& name, const Members& members);

    /// unknown literal
    class LiteralError : public TypeError {};

    /// index is out of range
    class RangeError : public TypeError {};

    /**
    @brief returns the enumeration name
    */
    const std::string& name() const
    { return name_; }

    /**
    @brief converts index to literal
    @param index the index
    @return literal name
    @throw RangeError if the index is out of range
    */
    const std::string& get_as_string(int index) const;

    /**
    @brief converts literal to index
    @param literal literal name
    @return index
    @throw LiteralError if the literal is unknown
    */
    int member_index(const std::string& literal) const;

    /**
    @brief checks the range of an index
    @param index the index
    @return the index if it is in range
    @throw RangeError if the index is out of range
    */
    int member_index(int index) const;

    /**
    @brief returns the highest valid index
    */
    int max_index() const
    { return int(members_.size())-1; }

private:
    std::string name_, typestr_;
    Members members_;
    mutable SmartValue default_value_;
};


typedef TypedSmartType<EnumerationType> SmartEnumerationType;


} // /namespace
} // /namespace
} // /namespace

#endif
