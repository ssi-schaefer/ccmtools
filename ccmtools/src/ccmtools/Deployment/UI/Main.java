package ccmtools.Deployment.UI;

import ccmtools.Deployment.CDDGenerator;

public class Main
{
    /**
     * Using the main method, we can start the component deployment
     * descriptor generator applictaion.
     * 
     * @param args
     */
    public static void main(String[] args)
    {
        CDDGenerator deployment = new CDDGenerator();
        deployment.generate(args);
    }
}
