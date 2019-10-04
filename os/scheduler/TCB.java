package os.scheduler;

public class TCB {
    private Thread thread = null;
    private int tid = 0;
    private int pid = 0;
    private boolean terminated = false;

    public TCB( Thread newThread, int myTid, int parentTid ) {
	thread = newThread;
	tid = myTid;
	pid = parentTid;
	terminated = false;

	System.err.println( "threadOS: a new thread (thread=" + thread + 
			    " tid=" + tid + 
			    " pid=" + pid + ")");
    }

    public synchronized Thread getThread( ) {
	return thread;
    }

    public synchronized int getTid( ) {
	return tid;
    }

    public synchronized int getPid( ) {
	return pid;
    }

    public synchronized boolean setTerminated( ) {
	terminated = true;
	return terminated;
    }

    public synchronized boolean getTerminated( ) {
	return terminated;
    }

}