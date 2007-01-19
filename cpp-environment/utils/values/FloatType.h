#ifndef WX__Utils__FloatType_H
#define WX__Utils__FloatType_H

#include "Type.h"

namespace wamas {
namespace platform {
namespace utils {


/**
@brief float type
*/
class FloatType : public Type
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
