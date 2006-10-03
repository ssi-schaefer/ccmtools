#ifndef ASSEMBLY_FACTORY_H
#define ASSEMBLY_FACTORY_H

#include <Components/ccm/local/CCM.h>

namespace ccm {
namespace local {

class AssemblyFactory
: virtual public Components::ccm::local::AssemblyFactory
{
public:
  AssemblyFactory();
  virtual ~AssemblyFactory();

  /*
   * Returns a new instance of StocktakeAssembly which is used by
   * the Main component's home to establish a nested component.
   */
  virtual wx::utils::SmartPtr<Components::ccm::local::Assembly> create()
    throw (Components::ccm::local::CreateFailure);
};

} // /namespace ccm
} // /namespace local

#endif  /* _STOCKTAKE_ASSEMBLY_FACTORY_H */

