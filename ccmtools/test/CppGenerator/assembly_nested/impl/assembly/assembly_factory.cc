#include "assembly.h"
#include "assembly_factory.h"

#include <WX/Utils/debug.h>

using namespace std;
using namespace WX::Utils;

namespace CCM_Local {

AssemblyFactory::AssemblyFactory()
{
  DEBUGNL("+AssemblyFactory::AssemblyFactory()");
}
AssemblyFactory::~AssemblyFactory()
{
  DEBUGNL("-AssemblyFactory::~AssemblyFactory()");
}

WX::Utils::SmartPtr<LocalComponents::Assembly>
AssemblyFactory::create()
  throw(LocalComponents::CreateFailure)
{
  DEBUGNL(" AssemblyFactory::create()");
  WX::Utils::SmartPtr<LocalComponents::Assembly>
    assembly(new Assembly());
  return assembly;
}

} // /namespace CCM_Local

