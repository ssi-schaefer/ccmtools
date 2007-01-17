import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.omg.CORBA.ORB;

import ccmtools.local.ServiceLocator;

public class Server
{
    public static final String COMPONENT_HOME_NAME = "ServerHome";

    public static void main(String[] args)
    {
        System.out.println("Login Server");
        for(int i = 0; i < args.length; i++)
        {
            System.out.println(args[i]);
        }
        
        // Configure Logger
        Logger logger = Logger.getLogger("test");
        logger.setLevel(Level.FINER);
        Handler handler = new ConsoleHandler();
        handler.setLevel(Level.ALL);
        handler.setFormatter(new ccmtools.utils.SimpleFormatter());
        logger.addHandler(handler);
        ServiceLocator.instance().setLogger(logger);

        
        try
        {
            // Set up the ServiceLocator singleton
            ORB orb = ORB.init(args, null);
            ServiceLocator.instance().setCorbaOrb(orb);

            ccmtools.remote.application.ServerHomeDeployment.deploy(COMPONENT_HOME_NAME);
            System.out.println("> Server is running...");
            orb.run();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
