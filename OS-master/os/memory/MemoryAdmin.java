
package os.memory;

import os.memory.pages.PageTable;

public class MemoryAdmin {
	int Pages=0;
	@SuppressWarnings("unused")
	public MemoryAdmin(int Pages) 
    { 
		this.Pages=Pages;
		PageTable PhysicalMem = new PageTable(Pages/2);
		PageTable VirtualMem = new PageTable(Pages);
		
	}
}
