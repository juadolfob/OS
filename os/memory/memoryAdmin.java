package os.memory;

import os.memory.pages.PageTable;
import os.process.ProcessList;

public class memoryAdmin {
	public memoryAdmin() 
    { 
		
		Memory virtualMem	=new Memory(16);// 512 MB
		Memory primaryMem	=new Memory(15);// 256 MB
		System.out.println("Primary Memory "+primaryMem.bytesize+" bytes");
		System.out.println("Virtual Memory "+virtualMem.bytesize+" bytes");
	
		PageTable pagetable = new PageTable(4); // 1048576
	}
}
