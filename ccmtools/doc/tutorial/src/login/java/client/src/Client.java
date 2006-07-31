import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.omg.CORBA.ORB;

import application.ccm.local.Group;
import application.ccm.local.Login;
import application.ccm.local.PersonData;
import application.ccm.local.Server;
import application.ccm.local.ServerHome;
import Components.ccm.local.HomeFinder;
import ccm.local.ServiceLocator;

public class Client
{
    public static final String COMPONENT_HOME_NAME = "LoginHome";
    
    public static void main(String[] args)
    {
        System.out.println("Login Client");

        // Configure Logger
        Logger logger = Logger.getLogger("test");
        logger.setLevel(Level.FINER);
        Handler handler = new ConsoleHandler();
        handler.setLevel(Level.ALL);
        handler.setFormatter(new ccm.local.MinimalFormatter());
        logger.addHandler(handler);
        ccm.local.ServiceLocator.instance().setLogger(logger);

        /**
         * Setup code (Part 1)
         */
        try
        {
            // Set up the ServiceLocator singleton
            ORB orb = ORB.init(args, null);
            ServiceLocator.instance().setCorbaOrb(orb);
            application.ccm.local.ServerHomeClientLibDeployment.deploy(COMPONENT_HOME_NAME);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        
        /**
         * Business code
         */
        try
        {            
            HomeFinder homeFinder = ccm.local.HomeFinder.instance();
            ServerHome home = (ServerHome) homeFinder.find_home_by_name(COMPONENT_HOME_NAME);
            Server server = home.create();
            server.configuration_complete();

            Login login = server.provide_login();
            
            PersonData person = new PersonData(277, "eteinik", "eteinik", Group.USER);
            boolean result = login.isValidUser(person);
            
            if(result)
            {
                System.out.println("Welcome " + person.getName());
            }
            else
            {
                System.out.println("We don't know you...");
            }

            assert(result);
            
            server.remove();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        
        /**
         * Setup code (Part 2)
         */
        try
        {
            application.ccm.local.ServerHomeClientLibDeployment.undeploy(COMPONENT_HOME_NAME);
            System.out.println("OK!");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            // Tear down the ServiceLocator singleton
            ServiceLocator.instance().destroy();
        }
    }
}
