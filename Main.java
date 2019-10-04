import java.util.Random;

import os.memory.Memory;
import os.process.ProcessList;

public class Main {

	public static void main(String[] args) {

		os.memory.memoryAdmin MemoryAdministrator = new os.memory.memoryAdmin();
		os.scheduler.Scheduler scheduler = new os.scheduler.Scheduler();

		scheduler.start();
		for (int i = 0; i < 100000; i++) {
			os.process.Process proc = new os.process.Process(1, 2, 3);
			os.scheduler.TCB newTcb = scheduler.addThread(proc);
		}
	}
}
