import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.omg.CORBA.ORB;

import application.Group;
import application.Login;
import application.PersonData;
import application.InvalidPersonData;
import application.Server;
import application.ServerHome;
import application.ServerHomeClientLibDeployment;
import Components.HomeFinder;
import ccmtools.local.ServiceLocator;

public class Client
{
    public static final String COMPONENT_HOME_NAME = "ServerHome";
    
    public static void main(String[] args)
    {
        System.out.println("Login Client");

        // Configure Logger
        Logger logger = Logger.getLogger("test");
        logger.setLevel(Level.FINER);
        Handler handler = new ConsoleHandler();
        handler.setLevel(Level.ALL);
        handler.setFormatter(new ccmtools.utils.SimpleFormatter());
        logger.addHandler(handler);
        ServiceLocator.instance().setLogger(logger);

        /**
         * Setup code (Part 1)
         */
        try
        {
            // Set up the ServiceLocator singleton
            ORB orb = ORB.init(args, null);
            ServiceLocator.instance().setCorbaOrb(orb);
            ServerHomeClientLibDeployment.deploy(COMPONENT_HOME_NAME);
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
            HomeFinder homeFinder = HomeFinder.instance();
            ServerHome home = (ServerHome) homeFinder.find_home_by_name(COMPONENT_HOME_NAME);
            Server server = home.create();
            server.configuration_complete();
            Login login = server.provide_login();

            try
            {
                PersonData person = new PersonData(0, "", "", Group.USER);
                login.isValidUser(person);
                assert(false);
            }
            catch (InvalidPersonData e)
            {
                System.err.println("Caught InvalidPersonData exception!");
            }

            
            try
            {
                PersonData person = new PersonData(277, "eteinik", "eteinik", Group.USER);
                boolean result = login.isValidUser(person);

                if (result)
                {
                    System.out.println("Welcome " + person.getName());
                }
                else
                {
                    System.out.println("We don't know you...");
                }
            }
            catch (InvalidPersonData e)
            {
                System.err.println("Error: InvalidPersonData!");
            }
            
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
            ServerHomeClientLibDeployment.undeploy(COMPONENT_HOME_NAME);
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
