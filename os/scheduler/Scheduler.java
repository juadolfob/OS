package os.scheduler;
import java.util.*;

public class Scheduler extends Thread {
    private Vector queue0;
    private Vector queue1;
    private Vector queue2;
    private int timeSlice;
    private static final int DEFAULT_TIME_SLICE = 1000;
    private static final int DEFAULT_MAX_THREADS = 10000;
    private boolean[] tids;
    private int nextId = 0;

    public TCB getMyTcb() {
        Thread myThread = Thread.currentThread();

        synchronized (queue0) {
            for (int i = 0; i < queue0.size(); i++) {
                TCB tcb = (TCB) queue0.elementAt(i);
                Thread thread = tcb.getThread();
                if (thread == myThread)
                    return tcb;
            }
        }
        synchronized (queue1) {
            for (int i = 0; i < queue1.size(); i++) {
                TCB tcb = (TCB) queue1.elementAt(i);
                Thread thread = tcb.getThread();
                if (thread == myThread)
                    return tcb;
            }
        }
        synchronized (queue2) {
            for (int i = 0; i < queue2.size(); i++) {
                TCB tcb = (TCB) queue2.elementAt(i);
                Thread thread = tcb.getThread();
                if (thread == myThread)
                    return tcb;
            }
        }
        return null;
    }

    private void initTid(int maxThreads) {
        tids = new boolean[maxThreads];
        for (int i = 0; i < maxThreads; i++) {
            tids[i] = false;
        }
    }

    private int getNewTid() {
        for (int i = 0; i < tids.length; i++) {
            int tentative = (nextId + i) % tids.length;
            if (tids[tentative] == false) {
                tids[tentative] = true;
                nextId = (tentative + 1) % tids.length;
                return tentative;
            }
        }
        return -1;
    }

    private boolean returnTid(int tid) {
        if (tid >= 0 && tid < tids.length && tids[tid] == true) {
            tids[tid] = false;
            return true;
        }
        return false;
    }

    public int getMaxThreads() {
        return tids.length;
    }

    public Scheduler() {
        timeSlice = DEFAULT_TIME_SLICE;
        queue0 = new Vector();
        queue1 = new Vector();
        queue2 = new Vector();
        initTid(DEFAULT_MAX_THREADS);
    }

    public Scheduler(int quantum) {
        timeSlice = quantum;
        queue0 = new Vector();
        queue1 = new Vector();
        queue2 = new Vector();
        initTid(DEFAULT_MAX_THREADS);
    }

    public Scheduler(int quantum, int maxThreads) {
        timeSlice = quantum;
        queue0 = new Vector();
        queue1 = new Vector();
        queue2 = new Vector();
        initTid(maxThreads);
    }

    private void schedulerSleep() {
        try {
            Thread.sleep(timeSlice);
        } catch (InterruptedException e) {
        }
    }

    public TCB addThread(Thread t) {
        TCB parentTcb = getMyTcb();
        int pid = (parentTcb != null) ? parentTcb.getTid() : -1;
        int tid = getNewTid();

        if (tid == -1)
            return null;

        TCB tcb = new TCB(t, tid, pid);
        queue0.add(tcb);
        return tcb;
    }

    public boolean deleteThread() {
        TCB tcb = getMyTcb();
        if (tcb != null) {
            return tcb.setTerminated();
        } else {
            return false;
        }
    }

    public void sleepThread(int milliseconds) {
        try {
            sleep(milliseconds);
        } catch (InterruptedException e) {
        }
    }

    public void run() {
        Thread current = null;
        while (true) {
            try {
                if (allQueuesAreEmpty())
                    continue;

                if (queue0_hasContent()) {
                    if (processQueue0(current))
                        continue;
                    continue;
                }
                if (queue0_isEmpty() && queue1_hasContent()) {
                    if (processQueue1(current))
                        continue;
                    continue;
                }
                if (queue0_isEmpty() && queue1_isEmpty() && queue2_hasContent()) {
                    if (processQueue2(current))
                        continue;
                    continue;
                }
            } catch (NullPointerException e3) {
            }
            ;
        }
    }

    private boolean processQueue0(Thread current) {
        TCB currentTCB = (TCB) queue0.firstElement();
        if (threadIsDead(currentTCB, queue0))
            return true;

        current = currentTCB.getThread();
        getThreadGoing(current);
        sleepThread(timeSlice / 2);

        finishProcessingQueue(queue0, queue1, current, currentTCB);
        return false;
    }

    private boolean processQueue1(Thread current) {
        TCB currentTCB = (TCB) queue1.firstElement(); // grab queue's first TCB
        if (threadIsDead(currentTCB, queue1))
            return true;

        current = currentTCB.getThread(); // grab thread object
        getThreadGoing(current); // start/resume thread

        sleepThread(timeSlice / 2); // first timeSlice/2
        if (queue0_hasContent())
            processNewTcb(current);
        sleepThread(timeSlice / 2); // second timeSlice/2

        // Move TCBs from queue1 to queue2
        finishProcessingQueue(queue1, queue2, current, currentTCB);
        return false;
    }

    private boolean processQueue2(Thread current) {
        TCB currentTCB = (TCB) queue2.firstElement();
        if (threadIsDead(currentTCB, queue2))
            return true;

        current = currentTCB.getThread();
        getThreadGoing(current);

        sleepThread(timeSlice / 2);
        if (queue0_hasContent() || queue1_hasContent())
            processNewTcb(current);
        sleepThread(timeSlice / 2);
        sleepThread(timeSlice);

        finishProcessingQueue(queue2, queue2, current, currentTCB);
        return false;
    }

    private void processNewTcb(Thread current) {
        if (current != null && current.isAlive()) {
            current.suspend();
            Thread newProcess = null;
            processQueue0(newProcess);
            current.resume();
        }
    }

    private void finishProcessingQueue(Vector myQueue, Vector nextQueue, Thread current, TCB currentTCB) {
        synchronized (myQueue) {
            if (current != null && current.isAlive()) {
                current.suspend();
                myQueue.remove(currentTCB);
                nextQueue.add(currentTCB);
            }
        }
    }

    private boolean threadIsDead(TCB currentTCB, Vector queue) {
        if (currentTCB.getTerminated() == true) {
            queue.remove(currentTCB);
            returnTid(currentTCB.getTid());
            return true;
        }
        return false;
    }

    private void getThreadGoing(Thread current) {
        if (current != null) {
            if (current.isAlive()) {
                current.resume();
            } else {
                current.start();
            }
        }
    }

    private boolean queue0_hasContent() {
        return (queue0.size() > 0);
    }

    private boolean queue1_hasContent() {
        return (queue1.size() > 0);
    }

    private boolean queue2_hasContent() {
        return (queue2.size() > 0);
    }

    private boolean queue0_isEmpty() {
        return (queue0.size() == 0);
    }

    private boolean queue1_isEmpty() {
        return (queue1.size() == 0);
    }

    private boolean queue2_isEmpty() {
        return (queue2.size() == 0);
    }

    private boolean allQueuesAreEmpty() {
        return (queue0_isEmpty() && queue1_isEmpty() && queue2_isEmpty());
    }
}