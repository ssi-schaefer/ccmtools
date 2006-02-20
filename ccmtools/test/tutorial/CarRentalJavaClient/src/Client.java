import org.omg.CORBA.ORB;
import org.omg.CORBA.StringHolder;

import BigBusiness.ccm.local.*;
import ccm.local.ServiceLocator;


public class Client
{
    private static boolean isTest;

    public static void main(String[] args)
    {
        try 
	{
	    // Set up the ServiceLocator singleton
	    ORB orb = ORB.init(args, null);
	    ServiceLocator.instance().setCorbaOrb(orb);
	    
	    CarRentalHomeClientLibDeployment.deploy("CarRentalHome");
        }
        catch(Exception e) {
            e.printStackTrace();
        }

	
	try
	{
	    ccm.local.Components.HomeFinder homeFinder = 
		ccm.local.HomeFinder.instance();
	    CarRentalHome home = 
		(CarRentalHome) homeFinder.find_home_by_name("CarRentalHome");

	    CarRental component = home.create();	    
	    CustomerMaintenance maintenance = component.provide_maintenance();
	    CustomerBusiness business = component.provide_business();
	    component.configuration_complete();


	    business.dollars_per_mile(5.5);
	    
	    {
		int id = 1;
		BigBusiness.Customer person = new BigBusiness.Customer();
		person.id = id;
		person.first_name = "Franz";
		person.last_name = "Kafka";
		maintenance.createCustomer(person);
		
		business.resetCustomerMiles(id);
		business.addCustomerMiles(id, 120.0);
	    }
	    
	    {
		int id = 1;
		BigBusiness.Customer person = maintenance.retrieveCustomer(id);
		double dollars = business.getCustomerDollars(id);
		
		System.out.println(" Customer: " + person.first_name + " " + person.last_name);
		System.out.println(" Miles : " + person.mileage);
		System.out.println(" To pay: " + dollars + " Dollars");
	    }

	    component.remove();
	}
	catch (Exception e)
	{
	    e.printStackTrace();
	}
	finally
	{
	    CarRentalHomeClientLibDeployment.undeploy("CarRentalHome");
	    
	    // Tear down the ServiceLocator singleton
	    ServiceLocator.instance().destroy();
	}
    }
}
