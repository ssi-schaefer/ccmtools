
#ifndef ASSEMBLY_H
#define ASSEMBLY_H

#include <Components/ccmtools.h>

#include <BasicTestHome_gen.h>
#include <UserTestHome_gen.h>
#include <SuperTestHome_gen.h>

using namespace std;

class Assembly
  : public Components::Assembly
{
private:
	Components::AssemblyState state_;

	// Super Component: SuperTest
	SuperTest::SmartPtr superTest;
	BasicTypeInterface::SmartPtr innerBasicType;
	UserTypeInterface::SmartPtr innerUserType;

	// Inner Component: BasicTest
	BasicTest::SmartPtr basicTest;
	BasicTypeInterface::SmartPtr basicType;

	// Inner Component: UserTest
	UserTest::SmartPtr userTest;
	UserTypeInterface::SmartPtr userType;

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
	virtual void build(Components::CCMObject::SmartPtr facadeComponent)
	    throw(Components::CreateFailure);

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
	    throw(Components::RemoveFailure);

	/*
	 * Returns whether the assembly is active or inactive.
	 */
	virtual Components::AssemblyState get_state();
};

#endif /* ASSEMBLY_H */
