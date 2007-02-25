package wamas;

import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import ccmtools.local.ServiceLocator;

class Main
{
    public static void main(String[] args)
    {
        try
        {
            /*
             * Configure Logger
             */

            Logger logger = Logger.getLogger("test");
            logger.setLevel(Level.FINE);
            Handler handler = new ConsoleHandler();
            handler.setLevel(Level.ALL);
            handler.setFormatter(new ccmtools.utils.SimpleFormatter());
            logger.addHandler(handler);
            ServiceLocator.instance().setLogger(logger);        	
        	
            
            /*
             * First test case
             */        	
            
            H3 home = (H3)H3Deployment.create();
            C3 component = home.create();
            component.configuration_complete();

            // Here we use provide after configuration_complete !!!
            wamas.Test.I1 c3i1 = component.provide_i1();
            System.out.println("C3.b = "+component.b());
            System.out.println("C3->I1.value() = "+c3i1.value());

            
            /*
             * Second test case
             */
            
            wamas.Test.H1 h1 = (wamas.Test.H1)wamas.Test.H1Deployment.create();
            wamas.Test.C1 c1 = h1.create();
            c1.a1("second Test");
            wamas.Test.I1 c1i1 = c1.provide_i1();
            c1.configuration_complete();
            System.out.println("C1->I1.value() = "+c1i1.value());
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
