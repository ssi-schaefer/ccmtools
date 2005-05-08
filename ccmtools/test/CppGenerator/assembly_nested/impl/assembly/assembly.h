
#ifndef ASSEMBLY_H
#define ASSEMBLY_H


#include <WX/Utils/debug.h>
#include <WX/Utils/smartptr.h>

#include <LocalComponents/CCM.h>
#include <CCM_Local/HomeFinder.h>

#include <CCM_Local/CCM_Session_BasicTest/BasicTest_gen.h>
#include <CCM_Local/CCM_Session_BasicTest/BasicTestHome_gen.h>

#include <CCM_Local/CCM_Session_UserTest/UserTest_gen.h>
#include <CCM_Local/CCM_Session_UserTest/UserTestHome_gen.h>

#include <CCM_Local/CCM_Session_SuperTest/SuperTest_gen.h>
#include <CCM_Local/CCM_Session_SuperTest/SuperTestHome_gen.h>


using namespace std;
using namespace CCM_Local;

using namespace CCM_Session_BasicTest;
using namespace CCM_Session_UserTest;
using namespace CCM_Session_SuperTest;

namespace CCM_Local {

class Assembly
: public LocalComponents::Assembly
{
private:
	LocalComponents::AssemblyState state_;

	// Super Component: SuperTest
	WX::Utils::SmartPtr<SuperTest> superTest;
	//	WX::Utils::SmartPtr<BasicTypeInterface> outerBasicType;
	//	WX::Utils::SmartPtr<UserTypeInterface> outerUserType;
	WX::Utils::SmartPtr<BasicTypeInterface> innerBasicType;
	WX::Utils::SmartPtr<UserTypeInterface> innerUserType;

	// Inner Component: BasicTest
	WX::Utils::SmartPtr<BasicTest> basicTest;
	WX::Utils::SmartPtr<BasicTypeInterface> basicType;

	// Inner Component: UserTest
	WX::Utils::SmartPtr<UserTest> userTest;
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
		throw (LocalComponents::CreateFailure);

	/*
	 * Build a component assembly based on a given facade component.
	 *
	 * Note: This is an CCM extension to support nested components.
	 */
	virtual void build(
	WX::Utils::SmartPtr<LocalComponents::CCMObject> facadeComponent)
		throw (LocalComponents::CreateFailure);

	/*
	 * Call configuration_complete on every component instance in the assembly.
	 *
	 * Note: This is an CCM extension to support nested components.
	 */
	virtual void configuration_complete();

	/*
	 * Removes all connections between components and destroys all components,
	 * homes, containers, and component servers that were created by the build
	 * operation.
	 */
	virtual void tear_down()
		throw (LocalComponents::RemoveFailure);

	/*
	 * Returns whether the assembly is active or inactive.
	 */
	virtual LocalComponents::AssemblyState get_state();
};

} // /namespace CCM_Local

#endif /* ASSEMBLY_H */
