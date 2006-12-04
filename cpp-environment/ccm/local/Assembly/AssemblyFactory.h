#ifndef __CCM_LOCAL_ASSEMBLY_FACTORY_H__
#define __CCM_LOCAL_ASSEMBLY_FACTORY_H__

#include <wamas/platform/utils/smartptr.h>

#include <Components/ccm/local/CCM.h>

namespace ccm {
namespace local {

template<class T>
class AssemblyFactory
: virtual public Components::ccm::local::AssemblyFactory
{
public:
	AssemblyFactory()
  	{
  	}
  
  	virtual ~AssemblyFactory()
  	{
 	}

  	virtual wamas::platform::utils::SmartPtr<Components::ccm::local::Assembly> create()
    		throw (Components::ccm::local::CreateFailure)
    {
  		wamas::platform::utils::SmartPtr<Components::ccm::local::Assembly> assembly(new T());
  		return assembly;
    }
};

} // /namespace ccm
} // /namespace local

#endif  /* __CCM_LOCAL__ASSEMBLY_FACTORY_H__ */

