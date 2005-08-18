
#ifndef ASSEMBLY_H
#define ASSEMBLY_H


#include <WX/Utils/debug.h>
#include <WX/Utils/smartptr.h>

#include <ccm/local/Components/CCM.h>
#include <ccm/local/HomeFinder.h>

#include <ccm/local/component/BasicTest/BasicTest_gen.h>
#include <ccm/local/component/BasicTest/BasicTestHome_gen.h>

#include <ccm/local/component/UserTest/UserTest_gen.h>
#include <ccm/local/component/UserTest/UserTestHome_gen.h>

#include <ccm/local/component/SuperTest/SuperTest_gen.h>
#include <ccm/local/component/SuperTest/SuperTestHome_gen.h>


namespace ccm {
namespace local {

using namespace std;
using namespace ccm::local;

class Assembly
: public Components::Assembly
{
private:
	Components::AssemblyState state_;

	// Super Component: SuperTest
	WX::Utils::SmartPtr<component::SuperTest::SuperTest> superTest;
	WX::Utils::SmartPtr<BasicTypeInterface> innerBasicType;
	WX::Utils::SmartPtr<UserTypeInterface> innerUserType;

	// Inner Component: BasicTest
	WX::Utils::SmartPtr<component::BasicTest::BasicTest> basicTest;
	WX::Utils::SmartPtr<BasicTypeInterface> basicType;

	// Inner Component: UserTest
	WX::Utils::SmartPtr<component::UserTest::UserTest> userTest;
	WX::Utils::SmartPtr<UserTypeInterface> userType;

public:
	Assembly();
	virtual ~Assembly();

	/*
	 * Creates required component servers, creates required containers, installs
	 * required component homes, instantiates components, configures and
	 * interconnects them according to the assembly descriptor.
	 */
	virtual void build()
	    throw (Components::CreateFailure);

	/*
	 * Build a component assembly based on a given facade component.
	 *
	 * Note: This is an CCM extension to support nested components.
	 */
	virtual void build(
	WX::Utils::SmartPtr<Components::CCMObject> facadeComponent)
	    throw (Components::CreateFailure);

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
	    throw (Components::RemoveFailure);

	/*
	 * Returns whether the assembly is active or inactive.
	 */
	virtual Components::AssemblyState get_state();
};

} // /namespace local
} // /namespace ccm

#endif /* ASSEMBLY_H */
