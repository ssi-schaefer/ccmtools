#include <WX/Utils/debug.h>
#include <WX/Utils/smartptr.h>

#include <ccm/local/Components/CCM.h>
#include <ccm/local/HomeFinder.h>

#include <world/ccm/local/component/Server/Server_gen.h>
#include <world/ccm/local/component/Server/ServerHome_gen.h>

using namespace std;
using namespace WX::Utils;
using namespace world::ccm::local;

int main(int argc, char *argv[])
{
    SmartPtr<component::Server::Server> server;
    SmartPtr<Hello> hello;
    ccm::local::Components::HomeFinder* homeFinder =
      ccm::local::HomeFinder::Instance();
    int error;

    try {
      error = deploy_world_ccm_local_component_Server_ServerHome("ServerHome");
      if(error) {
        cerr << "BOOTSTRAP ERROR: Can't deploy component homes!" << endl;
        return(error);
      }

      SmartPtr<component::Server::ServerHome> 
	home(dynamic_cast<component::Server::ServerHome*>
	     (homeFinder->find_home_by_name("ServerHome").ptr()));

      server = home->create();   
      hello = server->provide_hello();
      server->configuration_complete();
    
      string s = hello->sayHello();
      cout << "sayHello(): " << s << endl;

      server->remove();
      error += 
	undeploy_world_ccm_local_component_Server_ServerHome("ServerHome");
      if(error) {
        cerr << "ERROR: Can't undeploy component homes!" << endl;
        return(error);
      }
    } 
    catch ( ccm::local::Components::HomeNotFound ) {
        cout << "ERROR: can't find a home!" << endl;
        error = -1;
    } 
    catch ( ccm::local::Components::NotImplemented& e ) {
        cout << "ERROR: function not implemented: " 
	     << e.what (  ) << endl;
        error = -1;
    }  
    catch ( ccm::local::Components::InvalidName& e ) {
        cout << "ERROR: invalid name during connection: " 
             << e.what (  ) << endl;
        error = -1;
    }
    // Clean up HomeFinder singleton
    ccm::local::HomeFinder::destroy();
}

