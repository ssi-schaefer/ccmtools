#include <WX/Utils/debug.h>
#include <WX/Utils/smartptr.h>

#include <LocalComponents/CCM.h>
#include <CCM_Local/HomeFinder.h>

#include <CCM_Local/world/CCM_Session_Server/Server_gen.h>
#include <CCM_Local/world/CCM_Session_Server/ServerHome_gen.h>

using namespace std;
using namespace WX::Utils;
using namespace CCM_Local;
using namespace world;
using namespace CCM_Session_Server;

int main(int argc, char *argv[])
{
    SmartPtr<Server> server;
    SmartPtr<Hello> hello;
    LocalComponents::HomeFinder* homeFinder;
    int error;

    try {
      homeFinder = HomeFinder::Instance();
      error  = deploy_CCM_Local_world_ServerHome("ServerHome");
      if(error) {
        cerr << "ERROR: Can't deploy component homes!" << endl;
        return(error);
      }

      SmartPtr<ServerHome> 
	home(dynamic_cast<ServerHome*>
	     (homeFinder->find_home_by_name("ServerHome").ptr()));

      server = home->create();   
      hello = server->provide_hello();
      server->configuration_complete();
    
      string s = hello->sayHello();
      cout << "sayHello(): " << s << endl;

      server->remove();
      error = undeploy_CCM_Local_world_ServerHome("ServerHome");
      if(error) {
        cerr << "ERROR: Can't undeploy component homes!" << endl;
        return(error);
      }
    } 
    catch (LocalComponents::Exception& e ) {
      cout << "ERROR: " << e.what() << endl;
      error = -1;
    } 
}

