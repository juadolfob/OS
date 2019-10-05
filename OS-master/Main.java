
import os.memory.MemoryAdmin;

public class Main {

	public static void main(String[] args) {

		MemoryAdmin MemoryAdministrator = new MemoryAdmin(64);
		while(true) {
			RandomReferenceCaller(MemoryAdministrator);
		}
	}

	private static void RandomReferenceCaller(MemoryAdmin memoryAdministrator) {
		try {
			Thread.sleep(2000);
			System.out.println("random mem - reference");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
