#ifndef WX__Utils__Struct_H
#define WX__Utils__Struct_H

#include "StructValue.h"

namespace wamas {
namespace platform {
namespace utils {


/**
@internal map from std::string to SmartValue
@note We derive this class from StructValue only for convenience.
*/
class Struct : public StructValue
{
public:
    /// default ctor
    Struct()
    {}

    /// copy ctor
    Struct(const Struct& src)
    : StructValue(src)
    {}

    /// assignment operator
    Struct& operator=(const Struct& src);

    /// @internal copy ctor (shallow copy!)
    Struct(const StructValue& src)
    : StructValue(src)
    {}

    /// @internal ctor for builders
    explicit Struct(const SmartStructType& t)
    {
        type_ = t;
    }

    /// @internal copy ctor for the base class (shallow copy!)
    explicit Struct(const Value& v)
    : StructValue(v)
    {}

    /// @internal copy ctor for the base class (shallow copy!)
    explicit Struct(const SmartValue& v)
    : StructValue(v)
    {}

    /// returns a member or NULL, if that member doesn't exist
    SmartValue getP(const std::string& name) const;

    /// returns false if the member doesn't exist
    bool getP(const std::string& name, SmartValue& result) const;

    /// sets the member
    void setP(const std::string& name, const SmartValue& value);

    /// sets the member
    void setP(const std::string& name, const SmartBuilder& b)
    { setP(name, Value::get_from_builder(b)); }

    /// returns the number of members
    int size() const
    { return members_.size(); }

    /// clears the content
    void clear();
};


} // /namespace
} // /namespace
} // /namespace

#endif
