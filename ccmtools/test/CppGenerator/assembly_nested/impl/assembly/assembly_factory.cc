#include "assembly.h"
#include "assembly_factory.h"

#include <wx/utils/debug.h>

using namespace std;
using namespace wx::utils;

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

wx::utils::SmartPtr<Components::ccm::local::Assembly>
AssemblyFactory::create()
  throw(Components::ccm::local::CreateFailure)
{
  DEBUGNL(" AssemblyFactory::create()");
  wx::utils::SmartPtr<Components::ccm::local::Assembly>
    assembly(new Assembly());
  return assembly;
}

} // /namespace ccm
} // /namespace local

