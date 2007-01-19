#ifndef WX__Utils__SimpleBuilder_H
#define WX__Utils__SimpleBuilder_H

#include "Builder.h"

namespace wamas {
namespace platform {
namespace utils {


/**
@brief abstract base for value builders, which are implemented by an instance of Value
*/
class SimpleBuilder : public Builder
{
public:
    virtual ~SimpleBuilder() {}

    /**@name Builder interface */
    //@{
    virtual bool can_assign_builder(const Builder& b) const;
    virtual void assign_builder(const Builder& that);
    virtual int compare_builder(const Builder& that) const;
    virtual int compare_value(const Value& that) const;
    //@}

protected:
    /**
    @internal returns the value object
    */
    virtual const Value& the_value() const = 0;
};


} // /namespace
} // /namespace
} // /namespace

#endif
