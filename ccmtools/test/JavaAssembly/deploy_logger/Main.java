package wamas;

import Components.CCMException;
import Components.CCMHome;
import Components.HomeFinder;
import wamas.helpers.*;

public class Main
{
    public static void main( String[] args )
    {
        try
        {
            deploy();
            business_logic();
            undeploy();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            System.exit(1);
        }
    }

    private static void deploy() throws CCMException
    {
        wamas.io.OutputStreamLoggerHomeDeployment.deploy("OutputStreamLogger");
        wamas.system.ConsoleHomeDeployment.deploy("Console");
        wamas.helpers.StdErrLoggerHomeDeployment.deploy("StdErrLogger");
    }

    private static void undeploy()
    {
        wamas.io.OutputStreamLoggerHomeDeployment.undeploy("OutputStreamLogger");
        wamas.system.ConsoleHomeDeployment.undeploy("Console");
        wamas.helpers.StdErrLoggerHomeDeployment.undeploy("StdErrLogger");
    }

    private static void business_logic() throws Exception
    {
        CCMHome home = HomeFinder.instance().find_home_by_name("StdErrLogger");
        StdErrLogger comp = ((StdErrLoggerHomeAdapter) home).create();
        wamas.io.LoggerItf logger = comp.provide_logger();
        comp.configuration_complete();
        logger.print("Hello World!");
        comp.remove();
    }
}
