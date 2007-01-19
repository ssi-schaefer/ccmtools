#ifndef WX__Utils__BooleanType_H
#define WX__Utils__BooleanType_H

#include "Type.h"

namespace wamas {
namespace platform {
namespace utils {


/**
@brief boolean type
*/
class BooleanType : public Type
{
public:
    /**@name Type implementation */
    //@{
    virtual const std::string& typestr() const;
    virtual SmartValue default_value() const;
    virtual SmartBuilder create() const;
    virtual SmartValue fit(const SmartValue& value) const;
    //@}

    /**
    @internal value type string
    */
    static std::string const& value_typestr();
};


} // /namespace
} // /namespace
} // /namespace

#endif
