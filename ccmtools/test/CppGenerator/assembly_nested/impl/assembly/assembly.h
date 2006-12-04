
#ifndef ASSEMBLY_H
#define ASSEMBLY_H

#include <wamas/platform/utils/smartptr.h>

#include <Components/ccm/local/CCM.h>
#include <ccm/local/HomeFinder.h>

#include <ccm/local/BasicTest_gen.h>
#include <ccm/local/BasicTestHome_gen.h>

#include <ccm/local/UserTest_gen.h>
#include <ccm/local/UserTestHome_gen.h>

#include <ccm/local/SuperTest_gen.h>
#include <ccm/local/SuperTestHome_gen.h>


namespace ccm {
namespace local {

using namespace std;
using namespace ccm::local;

class Assembly
: public Components::ccm::local::Assembly
{
private:
	Components::ccm::local::AssemblyState state_;

	// Super Component: SuperTest
	wamas::platform::utils::SmartPtr<SuperTest> superTest;
	wamas::platform::utils::SmartPtr<BasicTypeInterface> innerBasicType;
	wamas::platform::utils::SmartPtr<UserTypeInterface> innerUserType;

	// Inner Component: BasicTest
	wamas::platform::utils::SmartPtr<BasicTest> basicTest;
	wamas::platform::utils::SmartPtr<BasicTypeInterface> basicType;

	// Inner Component: UserTest
	wamas::platform::utils::SmartPtr<UserTest> userTest;
	wamas::platform::utils::SmartPtr<UserTypeInterface> userType;

public:
	Assembly();
	virtual ~Assembly();

	/*
	 * Creates required component servers, creates required containers, installs
	 * required component homes, instantiates components, configures and
	 * interconnects them according to the assembly descriptor.
	 */
	virtual void build()
	    throw (Components::ccm::local::CreateFailure);

	/*
	 * Build a component assembly based on a given facade component.
	 *
	 * Note: This is an CCM extension to support nested components.
	 */
	virtual void build(
	wamas::platform::utils::SmartPtr<Components::ccm::local::CCMObject> facadeComponent)
	    throw (Components::ccm::local::CreateFailure);

	/*
	 * Call configuration_complete on every component instance in the 
	 * assembly.
	 *
	 * Note: This is an CCM extension to support nested components.
	 */
	virtual void configuration_complete();

	/*
	 * Removes all connections between components and destroys all 
	 * components, homes, containers, and component servers that were 
	 * created by the build operation.
	 */
	virtual void tear_down()
	    throw (Components::ccm::local::RemoveFailure);

	/*
	 * Returns whether the assembly is active or inactive.
	 */
	virtual Components::ccm::local::AssemblyState get_state();
};

} // /namespace local
} // /namespace ccm

#endif /* ASSEMBLY_H */
