#include <cstdlib> 
#include <iostream>
#include <string>
#include <WX/Utils/debug.h>
#include <CCM/CCMContainer.h>

#include <CORBA.h>
#include <coss/CosNaming.h>

#include <BigBusiness/ccm/remote/component/CarRental/CarRentalHome_remote.h>
#include <BigBusiness_CarRental.h>

using namespace std;
using namespace WX::Utils;

int main (int argc, char *argv[])
{
    // Initialize ORB 
    CORBA::ORB_var orb = CORBA::ORB_init(argc, argv);

    // Register all value type factories with the ORB  
    CCM::register_all_factories (orb);

    // Deploy local and remote component homes	
    int error = 0;
    error += deploy_BigBusiness_ccm_local_component_CarRental_CarRentalHome("CarRentalHome");
    error += deploy_BigBusiness_ccm_remote_component_CarRental_CarRentalHome(orb, "CarRentalHome:1.0");
    if(!error) {
        cout << "CarRentalHome server is running..." << endl;
    }
    else {
        cerr << "ERROR: Can't deploy components!" << endl;
        return -1;
    }

    // Start ORB
    orb->run();
}

