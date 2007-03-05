#include "ccmtools.h"

namespace Components {

void ComponentDelegator::disconnect(const FeatureName& receptacle)
{
    static Cookie dummy;
    this->disconnect(receptacle, dummy);
}

} // /namespace Components
