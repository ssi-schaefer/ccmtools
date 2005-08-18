#ifndef ASSEMBLY_FACTORY_H
#define ASSEMBLY_FACTORY_H

#include <ccm/local/Components/CCM.h>

namespace ccm {
namespace local {

class AssemblyFactory
: virtual public Components::AssemblyFactory
{
public:
  AssemblyFactory();
  virtual ~AssemblyFactory();

  /*
   * Returns a new instance of StocktakeAssembly which is used by
   * the Main component's home to establish a nested component.
   */
  virtual WX::Utils::SmartPtr<Components::Assembly> create()
    throw (Components::CreateFailure);
};

} // /namespace ccm
} // /namespace local

#endif  /* _STOCKTAKE_ASSEMBLY_FACTORY_H */

