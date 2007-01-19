#include "SimpleBuilder.h"

namespace wamas {
namespace platform {
namespace utils {


bool SimpleBuilder::can_assign_builder(const Builder& b) const
{
    const SimpleBuilder* pb = dynamic_cast<const SimpleBuilder*>(&b);
    if(!pb)
        return false;
    return this->can_assign_value(pb->the_value());
}

void SimpleBuilder::assign_builder(const Builder& that)
{
    const SimpleBuilder* pb = dynamic_cast<const SimpleBuilder*>(&that);
    if(!pb)
        THROW_ERROR_MSG(TypeError, "invalid assignment from " << that.typestr() << " to " << typestr());
    this->assign_value(pb->the_value());
}

int SimpleBuilder::compare_builder(const Builder& that) const
{
    const SimpleBuilder* pb = dynamic_cast<const SimpleBuilder*>(&that);
    if(!pb)
        THROW_ERROR_MSG(TypeError, "invalid comparison between " << that.typestr() << " and " << typestr());
    return the_value().compare(pb->the_value());
}

int SimpleBuilder::compare_value(const Value& that) const
{
    return the_value().compare(that);
}


} // /namespace
} // /namespace
} // /namespace
