public class Timer
{
    public Timer() 
    {
	clockStart_ = 0;
	clockStop_ = 0;
    }

    public void startClock()
    {
	clockStart_ = System.currentTimeMillis(); 
    }
  
    public void stopClock()
    {
	clockStop_ = System.currentTimeMillis(); 
    }
    
    void reportResult(long loops, long size)
    {
	System.out.print(" loops(" + loops + ") size(" + size + ") ");;
  	long cpu_time_used = clockStop_ - clockStart_;
 	System.out.println("time(" + cpu_time_used + ")ms");
    }

    private long clockStart_;
    private long clockStop_;
}
