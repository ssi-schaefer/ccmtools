package ccmtools.utils;

public class Timer
{
    private long clockStart;
    private long clockStop;
    private long millisSum;
    
    public Timer()
    {
        resetClock();
    }

    public void startClock()
    {
        clockStart = System.currentTimeMillis();
    }

    public void stopClock()
    {
        clockStop = System.currentTimeMillis();
        millisSum += (clockStop - clockStart);
    }

    public void resetClock()
    {
        millisSum = 0;
        clockStart = 0;
        clockStop = 0;
    }
    
    public long getTimeMillis()
    {
        return millisSum;
    }
    
    public double getTimeSec()
    {
        return millisSum/1000.0;
    }
    
    public String toString()
    {
        return Long.toString(getTimeMillis());
    }
}
