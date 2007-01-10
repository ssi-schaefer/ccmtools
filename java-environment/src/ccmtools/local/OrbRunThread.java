package ccmtools.local;

import org.omg.CORBA.ORB;

/**
 * Because the org.run() methods blocks, we have to use a separate
 * thread for that purpose.
 * Note that all CORBA request of an application process are handled
 * by the same ORB, thus, we only need one OrbRunThread instance.  
 */
public class OrbRunThread
	extends Thread
{
	public static final String THREAD_NAME = "ORB_RUN_THREAD";
	
	private ORB orb;
	
	public OrbRunThread(ORB orb)
    {
		super(THREAD_NAME);
        this.orb = orb;
    }

	/**
	 * Call the ORB's run() method to start CORBA request processing.
	 */
	public void run()
	{
		orb.run();
	}
}
