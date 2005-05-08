#ifndef ASSEMBLY_FACTORY_H
#define ASSEMBLY_FACTORY_H

#include <LocalComponents/CCM.h>

namespace CCM_Local {

class AssemblyFactory
: virtual public LocalComponents::AssemblyFactory
{
public:
  AssemblyFactory();
  virtual ~AssemblyFactory();

  /*
   * Returns a new instance of StocktakeAssembly which is used by
   * the Main component's home to establish a nested component.
   */
  virtual WX::Utils::SmartPtr<LocalComponents::Assembly> create()
    throw (LocalComponents::CreateFailure);
};

} // /namespace CCM_Local

#endif  /* _STOCKTAKE_ASSEMBLY_FACTORY_H */

