
#ifndef ASSEMBLY_H
#define ASSEMBLY_H

#include <wamas/platform/utils/smartptr.h>

#include <Components/ccm/local/CCM.h>
#include <ccm/local/HomeFinder.h>

#include <BasicTestHome_gen.h>
#include <UserTestHome_gen.h>
#include <SuperTestHome_gen.h>

using namespace std;
namespace wx = wamas::platform::utils;

class Assembly
: public Components::ccm::local::Assembly
{
private:
	Components::ccm::local::AssemblyState state_;

	// Super Component: SuperTest
	wx::SmartPtr<SuperTest> superTest;
	wx::SmartPtr<BasicTypeInterface> innerBasicType;
	wx::SmartPtr<UserTypeInterface> innerUserType;

	// Inner Component: BasicTest
	wx::SmartPtr<BasicTest> basicTest;
	wx::SmartPtr<BasicTypeInterface> basicType;

	// Inner Component: UserTest
	wx::SmartPtr<UserTest> userTest;
	wx::SmartPtr<UserTypeInterface> userType;

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
	virtual void build(wx::SmartPtr<Components::ccm::local::CCMObject> facadeComponent)
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

#endif /* ASSEMBLY_H */
