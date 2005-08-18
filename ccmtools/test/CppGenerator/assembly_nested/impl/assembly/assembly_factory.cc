#include "assembly.h"
#include "assembly_factory.h"

#include <WX/Utils/debug.h>

using namespace std;
using namespace WX::Utils;

namespace ccm {
namespace local {

AssemblyFactory::AssemblyFactory()
{
  DEBUGNL("+AssemblyFactory::AssemblyFactory()");
}
AssemblyFactory::~AssemblyFactory()
{
  DEBUGNL("-AssemblyFactory::~AssemblyFactory()");
}

WX::Utils::SmartPtr<Components::Assembly>
AssemblyFactory::create()
  throw(Components::CreateFailure)
{
  DEBUGNL(" AssemblyFactory::create()");
  WX::Utils::SmartPtr<Components::Assembly>
    assembly(new Assembly());
  return assembly;
}

} // /namespace ccm
} // /namespace local

