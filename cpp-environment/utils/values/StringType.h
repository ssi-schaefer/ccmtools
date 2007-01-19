#ifndef WX__Utils__StringType_H
#define WX__Utils__StringType_H

#include "Type.h"

namespace wamas {
namespace platform {
namespace utils {


/**
@brief string type
*/
class StringType : public Type
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
