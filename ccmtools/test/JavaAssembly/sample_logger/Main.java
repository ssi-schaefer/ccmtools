package wamas;

import Components.CCMHome;
import wamas.helpers.*;

public class Main
{
    public static void main( String[] args )
    {
        try
        {
            CCMHome home = StdErrLoggerHomeDeployment.create();
            StdErrLogger comp = ((StdErrLoggerHomeAdapter) home).create();
            wamas.io.LoggerItf logger = comp.provide_logger();
            comp.configuration_complete();
            
            logger.print("Hello World!");
            
            comp.remove();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            System.exit(1);
        }
    }
}