#ifndef __CCM_LOCAL_ASSEMBLY_FACTORY_H__
#define __CCM_LOCAL_ASSEMBLY_FACTORY_H__

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
  		LDEBUGNL(CCM_CONTAINER,"+AssemblyFactory::AssemblyFactory()" );
  	}
  
  	virtual ~AssemblyFactory()
  	{
  		LDEBUGNL(CCM_CONTAINER,"-AssemblyFactory::AssemblyFactory()" );
 	}

  	virtual wx::utils::SmartPtr<Components::ccm::local::Assembly> create()
    		throw (Components::ccm::local::CreateFailure)
    {
    	  	LDEBUGNL(CCM_CONTAINER," AssemblyFactory::create()" );
  		wx::utils::SmartPtr<Components::ccm::local::Assembly> assembly(new T());
  		return assembly;
    }
};

} // /namespace ccm
} // /namespace local

#endif  /* __CCM_LOCAL__ASSEMBLY_FACTORY_H__ */

